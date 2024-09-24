package ru.snapix.balancer.listeners

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.ServerConnectedEvent
import com.velocitypowered.api.event.player.ServerPreConnectEvent
import ru.snapix.balancer.Balancer
import ru.snapix.balancer.PlayerConnect
import ru.snapix.balancer.PlayerConnectCache
import ru.snapix.balancer.balancerVelocity
import ru.snapix.balancer.extensions.canJoin
import ru.snapix.library.network.ServerType
import kotlin.jvm.optionals.getOrNull

class ConnectionListener {
    @Subscribe(order = PostOrder.FIRST, async = false)
    fun onServerPreConnect(event: ServerPreConnectEvent) {
        val address = event.originalServer.serverInfo.name
        val addressLobby = balancerVelocity.lobbyServer.serverInfo.name
        if (address == addressLobby) {
            val balancerServer = Balancer.servers(ServerType.LOBBY).filter { it.canJoin(event.player) }.randomOrNull()
            val server = balancerVelocity.server.getServer(balancerServer?.name ?: "null").getOrNull()
            if (server == null) {
                event.result = ServerPreConnectEvent.ServerResult.denied()
                return
            }
            event.result = ServerPreConnectEvent.ServerResult.allowed(server)
        }
    }

    @Subscribe
    fun onConnected(event: ServerConnectedEvent) {
        val servername = event.server.serverInfo.name
        val server = Balancer.server(servername) ?: return
        PlayerConnectCache.update(PlayerConnect(event.player.username, server))
    }
}