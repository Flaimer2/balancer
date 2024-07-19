package ru.snapix.balancer.extensions

import com.velocitypowered.api.proxy.Player
import ru.snapix.balancer.BalancerServer
import ru.snapix.balancer.State

fun BalancerServer.canJoin(player: Player): Boolean {
    return players.size < maxPlayers && (state == State.WAITING || state == State.STARTING)
}