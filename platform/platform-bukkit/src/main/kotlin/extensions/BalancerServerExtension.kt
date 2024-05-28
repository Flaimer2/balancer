package ru.snapix.balancer.extensions

import org.bukkit.entity.Player
import ru.snapix.balancer.BalancerServer
import ru.snapix.balancer.State

// TODO: Make party work!!!
fun BalancerServer.canJoin(player: Player): Boolean {
    return players.size < maxPlayers && (state == State.WAITING || state == State.STARTING)
}