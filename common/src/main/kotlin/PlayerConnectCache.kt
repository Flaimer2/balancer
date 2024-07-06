package ru.snapix.balancer

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.snapix.library.cache.RedisCache

object PlayerConnectCache : RedisCache<PlayerConnect>() {
    override val KEY_REDIS: String = "player-connected-server"
    override fun key(value: PlayerConnect) = value.name
    override fun decode(value: String) = Json.decodeFromString<PlayerConnect>(value)
    override fun encode(value: PlayerConnect) = Json.encodeToString(value)
}