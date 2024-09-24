package ru.snapix.balancer

import kotlinx.serialization.json.Json
import ru.snapix.library.network.ServerType
import ru.snapix.library.utils.async
import ru.snapix.library.utils.redisClient

object Balancer {
    fun servers(filter: (ServerType) -> Boolean): List<BalancerServer> {
        val list = mutableListOf<BalancerServer>()
        ServerType.entries.filter(filter).forEach {
            list.addAll(servers(it))
        }
        return list
    }

    fun servers(): List<BalancerServer> {
        val list = mutableListOf<BalancerServer>()
        for (type in ServerType.entries) {
            list.addAll(servers(type))
        }
        return list
    }

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
        return server(ServerType[server], server)
    }
}

val ServerType.redisKeyServer
    get() = "balancer-server:${this.name.lowercase()}"
const val redisKeyConnect = "balancer-connect"