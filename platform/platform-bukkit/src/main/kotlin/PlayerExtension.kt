package ru.snapix.balancer

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.entity.Player
import ru.snapix.library.ServerType

fun Player.connect(server: BalancerServer) {
    if (server.serverType == ServerType.UNKNOWN) return
    Balancer.pool.resource.apply {
        val hash = mutableMapOf<String, String>()
        hash[name] = Json.encodeToString(server)
        hset(redisKeyConnect, hash)
    }
}