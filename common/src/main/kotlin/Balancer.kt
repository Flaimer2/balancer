package ru.snapix.balancer

import redis.clients.jedis.JedisPool
import ru.snapix.library.ServerType

object Balancer {
    val pool = JedisPool("localhost", 6379)
}

val ServerType.redisKeyServer
    get() = "balancer-server:${this.name.lowercase()}"