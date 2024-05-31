package ru.snapix.balancer.extensions

import org.bukkit.entity.Player
import ru.snapix.balancer.Balancer
import ru.snapix.balancer.BalancerServer
import ru.snapix.library.ServerType

fun Balancer.getBestServer(player: Player, type: ServerType): List<BalancerServer> = getServers(type).filter { it.value.canJoin(player) }.values.sortedByDescending { it.maxPlayers }