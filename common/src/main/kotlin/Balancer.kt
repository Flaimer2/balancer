package ru.snapix.balancer

import kotlinx.serialization.json.Json
import redis.clients.jedis.JedisPool
import ru.snapix.library.ServerType

object Balancer {
    val pool = JedisPool("localhost", 6379)

    fun getServers(type: ServerType): Map<String, BalancerServer> {
        if (type == ServerType.UNKNOWN) return mapOf()
        pool.resource.apply {
            return hgetAll(type.redisKeyServer).mapValues {
                Json.decodeFromString<BalancerServer>(it.value)
            }
        }
    }

    fun getServer(type: ServerType, server: String): BalancerServer? {
        if (type == ServerType.UNKNOWN || server.isEmpty()) return null
        pool.resource.apply {
            val value = hget(type.redisKeyServer, server.lowercase()) ?: return null
            return Json.decodeFromString<BalancerServer>(value)
        }
    }

    fun getServer(server: String): BalancerServer? {
        return getServer(ServerType(server), server)
    }
}

val ServerType.redisKeyServer
    get() = "balancer-server:${this.name.lowercase()}"
const val redisKeyConnect = "balancer-connect"