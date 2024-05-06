package ru.snapix.balancer

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import redis.clients.jedis.JedisPubSub
import ru.snapix.library.velocity.VelocityPlugin
import java.nio.file.Path


@Plugin(id = "snapibalancer", name = "SnapiBalancer", version = "1.0.3", authors = ["Flaimer"])
class BalancerVelocity @Inject constructor(server: ProxyServer, logger: Logger, @DataDirectory dataDirectory: Path) : VelocityPlugin() {
    init {
        init(server, logger, dataDirectory)
    }

    @Subscribe
    fun onInitialize(event: ProxyInitializeEvent) {
        Balancer.pool.resource.apply {
            subscribe(object : JedisPubSub() {
                override fun onMessage(channel: String, message: String) {
                    Balancer.pool.resource.apply {
                        hdel(channel, )
                    }
                }
            }, redisKeyConnect)
        }
//        Jedis jSubscriber = new Jedis();
//        jSubscriber.subscribe(new JedisPubSub() {
//            @Override
//            public void onMessage(String channel, String message) {
//                // handle message
//            }
//        }, "channel");
    }
}