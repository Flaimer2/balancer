package ru.snapix.balancer.extensions

import com.velocitypowered.api.proxy.Player
import ru.snapix.balancer.*
import ru.snapix.balancer.events.PlayerConnectEvent
import ru.snapix.library.ServerType
import ru.snapix.library.callEvent
import kotlin.jvm.optionals.getOrNull

fun Player.connect(server: BalancerServer) {
    if (server.serverType == ServerType.UNKNOWN) return
    val registeredServer = balancerVelocity.server.getServer(server.name).getOrNull() ?: return

    createConnectionRequest(registeredServer)
    balancerVelocity.callEvent(PlayerConnectEvent(this, registeredServer, server))
}

val Player.currentBalancerServer: BalancerServer?
    get() {
        val server = currentServer.getOrNull() ?: return null
        val servername = server.server.serverInfo.name

        return Balancer.server(servername)
    }