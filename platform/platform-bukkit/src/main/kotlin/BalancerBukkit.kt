package ru.snapix.balancer

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.plugin.java.JavaPlugin
import ru.snapix.balancer.handlers.Handler
import ru.snapix.balancer.handlers.lobby.LobbyHandler
import ru.snapix.balancer.handlers.skywars.SkyWarsHandler
import ru.snapix.library.ServerType
import ru.snapix.library.redis.async
import ru.snapix.library.redis.redisClient
import ru.snapix.library.snapiLibrary

class BalancerBukkit : JavaPlugin() {
    private lateinit var balancerServer: BalancerServer
    lateinit var handler: Handler

    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        when (snapiLibrary.serverType) {
            ServerType.AUTH -> TODO()
            ServerType.LOBBY -> LobbyHandler.enable()
            ServerType.CLASSIC -> TODO()
            ServerType.SKYBLOCK -> TODO()
            ServerType.SKYPVP -> TODO()
            ServerType.ANARCHY -> TODO()
            ServerType.SKYWARS -> SkyWarsHandler.enable()
            ServerType.BEDWARS -> TODO()
            ServerType.MURDERMYSTERY -> TODO()
            ServerType.THEBRIDGE -> TODO()
            ServerType.MINERWARE -> TODO()
            ServerType.BUILDBATTLE -> TODO()
            ServerType.UNKNOWN -> error("Not found server type")
        }
    }

    override fun onDisable() {
        handler.disable()
    }

    fun updateServer(balancerServer: BalancerServer) {
        this.balancerServer = balancerServer
        redisClient.async {
            hset(balancerServer.serverType.redisKeyServer, balancerServer.name to Json.encodeToString(balancerServer))
        }
    }

    fun currentServer(): BalancerServer {
        return balancerServer
    }


    companion object {
        lateinit var instance: BalancerBukkit
    }
}

val balancerBukkit = BalancerBukkit.instance