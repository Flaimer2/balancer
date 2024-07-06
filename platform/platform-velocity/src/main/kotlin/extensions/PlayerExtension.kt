package ru.snapix.balancer.extensions

import com.velocitypowered.api.proxy.Player
import ru.snapix.balancer.*
import ru.snapix.balancer.events.PlayerConnectEvent
import ru.snapix.library.network.ServerType
import ru.snapix.library.velocity.utils.callEvent
import kotlin.jvm.optionals.getOrNull

fun Player.connect(server: BalancerServer) {
    if (server.serverType == ServerType.UNKNOWN) return
    val registeredServer = balancerVelocity.server.getServer(server.name).getOrNull() ?: return

    createConnectionRequest(registeredServer)
    callEvent(PlayerConnectEvent(this, registeredServer, server))
}

fun Player.currentBalancerServer(): BalancerServer? {
    val server = currentServer.getOrNull() ?: return null
    val servername = server.server.serverInfo.name

    return Balancer.server(servername)
}