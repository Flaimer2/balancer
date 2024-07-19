package ru.snapix.balancer.extensions

import com.velocitypowered.api.proxy.Player
import ru.snapix.balancer.Balancer
import ru.snapix.balancer.BalancerServer
import ru.snapix.library.ServerType

fun Balancer.getBestServer(player: Player, type: ServerType): List<BalancerServer> =
    servers(type).filter { it.canJoin(player) }.sortedByDescending { it.maxPlayers }