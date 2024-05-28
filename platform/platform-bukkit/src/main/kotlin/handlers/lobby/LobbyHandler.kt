package ru.snapix.balancer.handlers.lobby

import co.aikar.commands.BukkitCommandManager
import ru.snapix.balancer.balancerBukkit
import ru.snapix.balancer.handlers.Handler

object LobbyHandler : Handler {
    private val plugin = balancerBukkit
    private val server = plugin.server

    override fun enable() {
        val commandManager = BukkitCommandManager(plugin)
        commandManager.registerCommand(ConnectCommand())
    }
}