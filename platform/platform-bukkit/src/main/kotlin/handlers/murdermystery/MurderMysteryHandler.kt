package ru.snapix.balancer.handlers.murdermystery

import org.bukkit.Bukkit
import plugily.projects.murdermystery.Main
import plugily.projects.murdermystery.minigamesbox.api.arena.IArenaState
import plugily.projects.murdermystery.minigamesbox.api.arena.IPluginArena
import ru.snapix.balancer.Mode
import ru.snapix.balancer.State
import ru.snapix.balancer.balancerBukkit
import ru.snapix.balancer.balancerServer
import ru.snapix.balancer.handlers.Handler
import ru.snapix.library.network.ServerType

object MurderMysteryHandler : Handler {
    private val plugin = balancerBukkit
    private val server = plugin.server
    private val murderMystery = Bukkit.getPluginManager().getPlugin("MurderMystery") as Main

    override fun enable() {
        server.pluginManager.registerEvents(MurderMysteryListener(), plugin)
        updateServer()
        plugin.handler = this
    }

    override fun disable() {
        updateServer(true)
    }

    fun updateServer(arena: IPluginArena) {
        val balancerServer = balancerServer {
            name = server.serverName
            map = arena.mapName
            port = server.port
            serverType = ServerType.MURDERMYSTERY
            players = arena.players.map { it.name }
            maxPlayers = arena.maximumPlayers
            state = when (arena.arenaState) {
                IArenaState.WAITING_FOR_PLAYERS -> State.WAITING
                IArenaState.STARTING -> State.STARTING
                IArenaState.FULL_GAME -> State.STARTING
                IArenaState.IN_GAME -> State.GAME
                IArenaState.ENDING -> State.FINISH
                IArenaState.RESTARTING -> State.RESTARTING
            }
            mode = Mode.SOLO
        }
        plugin.updateServer(balancerServer)
    }

    private fun updateServer(stop: Boolean = false) {
        val arena = murderMystery.arenaRegistry.pluginArenas.firstOrNull()
        if (arena == null) {
            balancerBukkit.logger.severe("No arena is configured. Cannot register server.")
            server.pluginManager.disablePlugin(plugin)
            return
        }
        if (stop) {
            val balancerServer = balancerServer {
                name = server.serverName
                map = arena.mapName
                port = server.port
                serverType = ServerType.MURDERMYSTERY
                players = emptyList()
                maxPlayers = arena.maximumPlayers
                state = State.RESTARTING
                mode = Mode.SOLO
            }
            plugin.updateServer(balancerServer)
            return
        }
        updateServer(arena)
    }
}