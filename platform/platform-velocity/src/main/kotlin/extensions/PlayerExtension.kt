package ru.snapix.balancer.extensions

import com.velocitypowered.api.proxy.Player
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.snapix.balancer.BalancerServer
import ru.snapix.balancer.PlayerConnect
import ru.snapix.balancer.redisKeyConnect
import ru.snapix.library.ServerType
import ru.snapix.library.redis.async
import ru.snapix.library.redis.redisClient

fun Player.connect(server: BalancerServer) {
    if (server.serverType == ServerType.UNKNOWN) return
    redisClient.async {
        publish(
            redisKeyConnect, Json.encodeToString(PlayerConnect(username, server))
        )
    }
}