package ru.snapix.balancer.handlers.lobby

import co.aikar.commands.BukkitCommandManager
import ru.snapix.balancer.Mode
import ru.snapix.balancer.State
import ru.snapix.balancer.balancerBukkit
import ru.snapix.balancer.balancerServer
import ru.snapix.balancer.handlers.Handler
import ru.snapix.balancer.handlers.skywars.SkyWarsHandler

object LobbyHandler : Handler {
    private val plugin = balancerBukkit
    private val server = plugin.server

    override fun enable() {
        val commandManager = BukkitCommandManager(plugin)
        commandManager.registerCommand(ConnectCommand())
        BalancerExpansion().register()
        updateServer()
        plugin.handler = this
    }

    override fun disable() {
        updateServer(stop = true)
    }

    fun updateServer(stop: Boolean = false) {
        val balancerServer = balancerServer {
            name = server.serverName
            map = server.worlds[0].name
            port = server.port
            serverType = plugin.serverType
            players = server.onlinePlayers.map { it.uniqueId }
            maxPlayers = server.maxPlayers
            state = if (stop) State.RESTARTING else State.WAITING
            mode = Mode.UNKNOWN
        }
        plugin.updateServer(balancerServer)
    }
}