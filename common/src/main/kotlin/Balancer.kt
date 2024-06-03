package ru.snapix.balancer

import kotlinx.serialization.json.Json
import redis.clients.jedis.JedisPool
import ru.snapix.library.ServerType
import ru.snapix.library.async

object Balancer {
    val pool = JedisPool("localhost", 6379)
    val jedis = pool.resource

    fun getServers(type: ServerType): Map<String, BalancerServer> {
        if (type == ServerType.UNKNOWN) return mapOf()
        val result = jedis.async {
            hgetAll(type.redisKeyServer).mapValues {
                Json.decodeFromString<BalancerServer>(it.value)
            }
        }
        return result ?: emptyMap()
    }

    fun getServer(type: ServerType, server: String): BalancerServer? {
        if (type == ServerType.UNKNOWN || server.isEmpty()) return null
        val result = jedis.async {
            println("execute")
            val value = hget(type.redisKeyServer, server.lowercase()) ?: return@async null
            println("value $value")
            Json.decodeFromString<BalancerServer>(value)
        }
        println("result $result")
        return result
    }

    fun getServer(server: String): BalancerServer? {
        println(server)
        return getServer(ServerType(server), server)
    }
}

val ServerType.redisKeyServer
    get() = "balancer-server:${this.name.lowercase()}"
const val redisKeyConnect = "balancer-connect"