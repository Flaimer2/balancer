package ru.snapix.balancer.handlers

import io.github.Leonardo0013YT.UltraSkyWars.api.UltraSkyWarsAPI
import io.github.Leonardo0013YT.UltraSkyWars.api.events.USWGameFinishEvent
import io.github.Leonardo0013YT.UltraSkyWars.api.events.USWGameQuitEvent
import io.github.Leonardo0013YT.UltraSkyWars.api.events.USWGameStartEvent
import io.github.Leonardo0013YT.UltraSkyWars.api.events.data.USWGamePlayerLoadEvent
import io.github.Leonardo0013YT.UltraSkyWars.game.GameData
import io.github.Leonardo0013YT.UltraSkyWars.superclass.Game
import io.github.Leonardo0013YT.UltraSkyWars.enums.State.*
import org.bukkit.event.Listener
import ru.snapix.balancer.Mode
import ru.snapix.balancer.State
import ru.snapix.balancer.balancerBukkit
import ru.snapix.balancer.balancerServer
import ru.snapix.library.ServerType

object SkyWarsHandler : Handler {
    private val plugin = balancerBukkit
    private val server = plugin.server

    override fun enable() {
        server.pluginManager.registerEvents(SkyWarsListener(), plugin)
        updateServer()
    }

    override fun disable() {
        updateServer()
    }

    private fun updateServer(gameData: GameData) {
        val balancerServer = balancerServer {
            name = server.name
            map = gameData.map
            port = server.port
            serverType = ServerType.SKYWARS
            maxPlayers = server.maxPlayers
            state = state(gameData.state)
            mode = mode(gameData.type)
        }
        plugin.updateServer(balancerServer)
    }

    fun updateServer(game: Game) {
        val balancerServer = balancerServer {
            name = server.name
            map = game.spectator.world.name
            port = server.port
            serverType = ServerType.SKYWARS
            players = game.players.map { it.uniqueId }
            maxPlayers = server.maxPlayers
            state = state(game.state)
            mode = mode(game.gameType)
        }
        plugin.updateServer(balancerServer)
    }

    fun updateServer() {
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

    private fun state(value: String): State = state(io.github.Leonardo0013YT.UltraSkyWars.enums.State.valueOf(value))
}

class SkyWarsListener : Listener {
    fun onGamePlayerLoad(event: USWGamePlayerLoadEvent) {
        SkyWarsHandler.updateServer(event.game)
    }

    // TODO: Поменять в плагине UltraSkyWars вызов event'а после удаление игрока
    fun onGameQuit(event: USWGameQuitEvent) {
        SkyWarsHandler.updateServer(event.game)
    }

    // TODO: Поменять в плагине UltraSkyWars вызов event'а после изменения статуса
    fun onGameStart(event: USWGameStartEvent) {
        SkyWarsHandler.updateServer(event.game)
    }

    fun onGameFinish(event: USWGameFinishEvent) {
        SkyWarsHandler.updateServer(event.game)
    }
}