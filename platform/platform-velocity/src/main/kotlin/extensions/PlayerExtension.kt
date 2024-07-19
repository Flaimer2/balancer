package ru.snapix.balancer.extensions

import com.velocitypowered.api.proxy.Player
import ru.snapix.balancer.*
import ru.snapix.library.ServerType
import kotlin.jvm.optionals.getOrNull

fun Player.connect(server: BalancerServer) {
    if (server.serverType == ServerType.UNKNOWN) return
    val registeredServer = balancerVelocity.server.getServer(server.name).getOrNull() ?: return

    createConnectionRequest(registeredServer)
}

val Player.currentBalancerServer: BalancerServer?
    get() {
        val server = currentServer.getOrNull() ?: return null
        val servername = server.server.serverInfo.name

        return Balancer.server(servername)
    }