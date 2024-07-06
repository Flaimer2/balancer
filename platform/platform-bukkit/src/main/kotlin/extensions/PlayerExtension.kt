package ru.snapix.balancer.extensions

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.entity.Player
import ru.snapix.balancer.BalancerServer
import ru.snapix.balancer.PlayerConnect
import ru.snapix.balancer.redisKeyConnect
import ru.snapix.library.network.ServerType
import ru.snapix.library.utils.async
import ru.snapix.library.utils.redisClient

fun Player.connect(server: BalancerServer) {
    if (server.serverType == ServerType.UNKNOWN) return
    redisClient.async { publish(redisKeyConnect, Json.encodeToString(PlayerConnect(name, server))) }
}
