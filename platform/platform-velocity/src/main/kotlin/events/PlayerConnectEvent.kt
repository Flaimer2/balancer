package ru.snapix.balancer.events

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.server.RegisteredServer
import ru.snapix.balancer.BalancerServer

data class PlayerConnectEvent(val player: Player, val server: RegisteredServer, val balancerServer: BalancerServer)
