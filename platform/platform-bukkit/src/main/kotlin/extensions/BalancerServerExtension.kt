package ru.snapix.balancer.extensions

import org.bukkit.entity.Player
import ru.snapix.balancer.BalancerServer
import ru.snapix.balancer.State
import ru.snapix.snapicooperation.api.Party

fun BalancerServer.canJoin(player: Player): Boolean {
    val party = Party[player.name]
    if (party != null) {
        return (players.size + party.players.size) < maxPlayers && (state == State.WAITING || state == State.STARTING)
    }
    return players.size < maxPlayers && (state == State.WAITING || state == State.STARTING)
}