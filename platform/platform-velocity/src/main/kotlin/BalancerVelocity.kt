package ru.snapix.balancer

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Dependency
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import ru.snapix.balancer.events.PlayerConnectEvent
import ru.snapix.balancer.listeners.ConnectionListener
import ru.snapix.library.libs.kreds.connection.AbstractKredsSubscriber
import ru.snapix.library.players
import ru.snapix.library.redis.async
import ru.snapix.library.redis.redisClient
import ru.snapix.library.redis.subscribe
import ru.snapix.library.velocity.VelocityPlugin
import java.nio.file.Path
import kotlin.jvm.optionals.getOrNull

@Plugin(
    id = "snapibalancer",
    name = "SnapiBalancer",
    version = "1.3",
    authors = ["Flaimer"],
    dependencies = [Dependency(id = "snapilibrary")]
)
class BalancerVelocity @Inject constructor(server: ProxyServer, logger: Logger, @DataDirectory dataDirectory: Path) : VelocityPlugin() {
    init {
        init(server, logger, dataDirectory)
    }

    @Subscribe
    fun onEnable(event: ProxyInitializeEvent) {
        instance = this
        subscribe(object : AbstractKredsSubscriber() {
            override fun onException(ex: Throwable) {
                logger.error("Exception while handling subscription to redis: ${ex.stackTrace}")
            }

            override fun onMessage(channel: String, message: String) {
                val playerConnect = Json.decodeFromString<PlayerConnect>(message)
                val balancerServer = playerConnect.server
                val player = server.getPlayer(playerConnect.name).getOrNull()
                    ?: error("Player ${playerConnect.name} not found in balancer connect")
                val connectServer = server.getServer(balancerServer.name).getOrNull()
                    ?: error("Server ${balancerServer.name} not found in balancer connect")

                logger.debug(
                    "Try create connection request for {} to server {} with state {}",
                    player.username,
                    balancerServer.name,
                    balancerServer.state
                )
                player.createConnectionRequest(connectServer)
                server.eventManager.fire(PlayerConnectEvent(player, connectServer, balancerServer))
            }

            override fun onSubscribe(channel: String, subscribedChannels: Long) {
                logger.info("Success subscribed to channel: $channel")
            }

            override fun onUnsubscribe(channel: String, subscribedChannels: Long) {
                logger.info("Success unsubscribed to channel: $channel")
            }
        }, redisKeyConnect)
        server.eventManager.register(this, ConnectionListener())
    }

    @Subscribe
    fun onDisable(event: ProxyShutdownEvent) {
        players().forEach { removePlayer(it) }
    }

    fun addPlayer(name: String) {
        redisClient.async {
            sadd(KEY_REDIS_PLAYER, name)
        }
    }

    fun removePlayer(name: String) {
        redisClient.async {
            srem(KEY_REDIS_PLAYER, name)
        }
    }

    companion object {
        lateinit var instance: BalancerVelocity
    }
}

const val KEY_REDIS_PLAYER = "proxy-player"
val balancerVelocity = BalancerVelocity.instance