package ru.snapix.balancer.handlers.bedwars

import com.axeelheaven.hbedwars.BedWars
import com.axeelheaven.hbedwars.api.arena.Arena
import com.axeelheaven.hbedwars.api.arena.GameState
import org.bukkit.Bukkit
import ru.snapix.balancer.Mode
import ru.snapix.balancer.State
import ru.snapix.balancer.balancerBukkit
import ru.snapix.balancer.balancerServer
import ru.snapix.balancer.handlers.Handler
import ru.snapix.library.network.ServerType

object BedWarsHandler : Handler {
    private val plugin = balancerBukkit
    private val server = plugin.server
    private val bedwars = Bukkit.getPluginManager().getPlugin("HBedWars") as BedWars

    override fun enable() {
        server.pluginManager.registerEvents(BedWarsListener(), plugin)
        updateServer()
        plugin.handler = this
    }

    override fun disable() {
        updateServer(true)
    }

    fun updateServer(arena: Arena) {
        val balancerServer = balancerServer {
            name = server.serverName
            map = arena.name
            port = server.port
            serverType = ServerType.BEDWARS
            players = arena.players.map { it.name }
            maxPlayers = arena.arenaTeams.size * arena.group.teamSize
            state = when (arena.gameState) {
                GameState.LOADING -> State.WAITING
                GameState.WAITING -> State.WAITING
                GameState.STARTING -> State.STARTING
                GameState.FULL -> State.STARTING
                GameState.GAME -> State.GAME
                GameState.END -> State.FINISH
                else -> State.RESTARTING
            }
            mode = when (arena.group.name.uppercase()) {
                "SOLO" -> Mode.SOLO
                "DOUBLES" -> Mode.DOUBLES
                "QUARTETS" -> Mode.TEAM
                else -> Mode.UNKNOWN
            }
        }
        plugin.updateServer(balancerServer)
    }

    private fun updateServer(stop: Boolean = false) {
        val arena = bedwars.arenaManager.games.values.firstOrNull()
        if (arena == null) {
            balancerBukkit.logger.severe("No arena is configured. Cannot register server.")
            server.pluginManager.disablePlugin(plugin)
            return
        }
        if (stop) {
            val balancerServer = balancerServer {
                name = server.serverName
                map = arena.name
                port = server.port
                serverType = ServerType.BEDWARS
                players = emptyList()
                maxPlayers = arena.arenaTeams.size * arena.group.teamSize
                state = State.RESTARTING
                mode = when (arena.group.name.uppercase()) {
                    "SOLO" -> Mode.SOLO
                    "DOUBLES" -> Mode.DOUBLES
                    "QUARTETS" -> Mode.TEAM
                    else -> Mode.UNKNOWN
                }
            }
            plugin.updateServer(balancerServer)
            return
        }
        updateServer(arena)
    }
}