package ru.snapix.balancer.handlers.skywars

import io.github.Leonardo0013YT.UltraSkyWars.api.UltraSkyWarsAPI
import io.github.Leonardo0013YT.UltraSkyWars.enums.State.*
import io.github.Leonardo0013YT.UltraSkyWars.game.GameData
import io.github.Leonardo0013YT.UltraSkyWars.superclass.Game
import ru.snapix.balancer.Mode
import ru.snapix.balancer.State
import ru.snapix.balancer.balancerBukkit
import ru.snapix.balancer.balancerServer
import ru.snapix.balancer.handlers.Handler
import ru.snapix.library.network.ServerType

object SkyWarsHandler : Handler {
    private val plugin = balancerBukkit
    private val server = plugin.server

    override fun enable() {
        server.pluginManager.registerEvents(SkyWarsListener(), plugin)
        updateServer()
        plugin.handler = this
    }

    override fun disable() {
        updateServer()
    }

    fun updateServer(game: Game) {
        val balancerServer = balancerServer {
            name = server.serverName
            map = game.spectator.world.name
            port = server.port
            serverType = ServerType.SKYWARS
            players = game.players.map { it.name }
            maxPlayers = server.maxPlayers
            state = state(game.state)
            mode = mode(game.gameType)
        }
        plugin.updateServer(balancerServer)
    }

    private fun updateServer(gameData: GameData) {
        val balancerServer = balancerServer {
            name = server.serverName
            map = gameData.map
            port = server.port
            serverType = ServerType.SKYWARS
            maxPlayers = server.maxPlayers
            state = state(gameData.state)
            mode = mode(gameData.type)
        }
        plugin.updateServer(balancerServer)
    }

    private fun updateServer() {
        val gameData = UltraSkyWarsAPI.getGameData().values.firstOrNull()
        if (gameData == null) {
            balancerBukkit.logger.severe("No arena is configured. Cannot register server.")
            server.pluginManager.disablePlugin(plugin)
            return
        }
        updateServer(gameData)
    }

    private fun mode(value: String) = when (value) {
        "SOLO" -> Mode.SOLO
        "TEAM" -> Mode.DOUBLES
        else -> Mode.UNKNOWN
    }

    private fun state(value: io.github.Leonardo0013YT.UltraSkyWars.enums.State): State = when (value) {
        WAITING -> State.WAITING
        STARTING -> State.STARTING
        PREGAME -> State.GAME
        GAME -> State.GAME
        FINISH -> State.FINISH
        else -> State.RESTARTING
    }

    private fun state(value: String): State = state(valueOf(value))
}