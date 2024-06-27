package ru.snapix.balancer

import kotlinx.serialization.json.Json
import ru.snapix.library.ServerType
import ru.snapix.library.redis.async
import ru.snapix.library.redis.redisClient

object Balancer {
    fun servers(type: ServerType): List<BalancerServer> {
        if (type == ServerType.UNKNOWN) return listOf()
        val result = redisClient.async {
            hvals(type.redisKeyServer).map { Json.decodeFromString<BalancerServer>(it) }
        }
        return result
    }

    fun server(type: ServerType, server: String): BalancerServer? {
        if (type == ServerType.UNKNOWN || server.isEmpty()) return null
        val result = redisClient.async {
            val value = hget(type.redisKeyServer, server.lowercase()) ?: return@async null
            Json.decodeFromString<BalancerServer>(value)
        }
        return result
    }

    fun server(server: String): BalancerServer? {
        return server(ServerType(server), server)
    }
}

val ServerType.redisKeyServer
    get() = "balancer-server:${this.name.lowercase()}"
const val redisKeyConnect = "balancer-connect"