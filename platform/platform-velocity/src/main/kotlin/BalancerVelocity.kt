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
import com.velocitypowered.api.proxy.server.RegisteredServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import redis.clients.jedis.JedisPubSub
import ru.snapix.balancer.events.PlayerConnectEvent
import ru.snapix.library.velocity.VelocityPlugin
import java.nio.file.Path
import kotlin.jvm.optionals.getOrNull

@Plugin(
    id = "snapibalancer",
    name = "SnapiBalancer",
    version = "1.2",
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
        CoroutineScope(Dispatchers.IO).launch {
            Balancer.pool.resource.subscribe(object : JedisPubSub() {
                override fun onMessage(channel: String, message: String) {
                    val playerConnect: PlayerConnect = Json.decodeFromString<PlayerConnect>(message)
                    val balancerServer: BalancerServer = playerConnect.server
                    val player: Player = server.getPlayer(playerConnect.name).getOrNull()
                        ?: error("Player ${playerConnect.name} not found in balancer connect")
                    val connectServer: RegisteredServer = server.getServer(balancerServer.name).getOrNull()
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

                override fun onSubscribe(channel: String, subscribedChannels: Int) {
                    logger.info("Success subscribed to channel: $channel")
                }

                override fun onUnsubscribe(channel: String, subscribedChannels: Int) {
                    logger.info("Success unsubscribed to channel: $channel")
                }
            }, redisKeyConnect)
        }
    }

    @Subscribe
    fun onDisable(event: ProxyShutdownEvent) {
        Balancer.pool.close()
    }

    companion object {
        lateinit var instance: BalancerVelocity
    }
}