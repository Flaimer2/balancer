package ru.snapix.balancer.handlers.lobby

import ru.snapix.balancer.*
import ru.snapix.balancer.handlers.Handler
import ru.snapix.library.network.ServerType

object LobbyHandler : Handler {
    private val plugin = balancerBukkit
    private val server = plugin.server

    override fun enable() {
        updateServer()
        plugin.handler = this
        plugin.server.pluginManager.registerEvents(LobbyListener(), plugin)
    }

    override fun disable() {
        updateServer(stop = true)
    }

    fun updateServer(stop: Boolean = false) {
        val balancerServer = balancerServer {
            name = server.serverName
            map = server.worlds[0].name
            port = server.port
            serverType = ServerType.LOBBY
            players = if (stop) emptyList() else server.onlinePlayers.map { it.name }
            maxPlayers = server.maxPlayers
            state = if (stop) State.RESTARTING else State.WAITING
            mode = Mode.UNKNOWN
        }
        plugin.updateServer(balancerServer)
    }
}