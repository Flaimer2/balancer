package ru.snapix.balancer.handlers.survival

import ru.snapix.balancer.*
import ru.snapix.balancer.handlers.Handler
import ru.snapix.library.network.ServerType

object AnarchyHandler : SurvivalHandler {
    private val plugin = balancerBukkit
    private val server = plugin.server

    override fun enable() {
        updateServer()
        plugin.handler = this
        plugin.server.pluginManager.registerEvents(SurvivalListener(this), plugin)
    }

    override fun disable() {
        updateServer(stop = true)
    }

    override fun updateServer(stop: Boolean) {
        val balancerServer = balancerServer {
            name = server.serverName
            map = server.worlds[0].name
            port = server.port
            serverType = ServerType.ANARCHY
            players = server.onlinePlayers.map { it.name }
            maxPlayers = server.maxPlayers
            state = if (stop) State.RESTARTING else State.WAITING
            mode = Mode.UNKNOWN
        }
        plugin.updateServer(balancerServer)
    }
}