package ru.snapix.balancer.listeners

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.LoginEvent
import ru.snapix.balancer.balancerVelocity

class ConnectionListener {
    @Subscribe(order = PostOrder.EARLY)
    fun onLogin(event: LoginEvent) {
        balancerVelocity.addPlayer(event.player.username)
    }

    @Subscribe(order = PostOrder.EARLY)
    fun onDisconnect(event: DisconnectEvent) {
        balancerVelocity.removePlayer(event.player.username)
    }
}