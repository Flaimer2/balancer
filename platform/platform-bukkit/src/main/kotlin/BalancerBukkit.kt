package ru.snapix.balancer

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.plugin.java.JavaPlugin
import ru.snapix.balancer.handlers.LobbyHandler
import ru.snapix.balancer.handlers.SkyWarsHandler
import ru.snapix.balancer.settings.Settings
import ru.snapix.library.ServerType

class BalancerBukkit : JavaPlugin() {
    private lateinit var balancerServer: BalancerServer

    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        when (Settings.config.gameType()) {
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


    fun updateServer(balancerServer: BalancerServer) {
        this.balancerServer = balancerServer
        Balancer.pool.resource.apply {
            val hash = mutableMapOf<String, String>()
            hash[balancerServer.name] = Json.encodeToString(balancerServer)
            hset(balancerServer.serverType.redisKeyServer, hash)
        }
    }

    fun getServers(type: ServerType): Map<String, BalancerServer> {
        if (type == ServerType.UNKNOWN) return mapOf()
        Balancer.pool.resource.apply {
            return hgetAll(type.redisKeyServer).mapValues {
                Json.decodeFromString<BalancerServer>(it.value)
            }
        }
    }

    fun getServer(type: ServerType, server: String): BalancerServer? {
        if (type == ServerType.UNKNOWN || server.isEmpty()) return null
        Balancer.pool.resource.apply {
            return Json.decodeFromString<BalancerServer>(hget(type.redisKeyServer, server))
        }
    }

    fun getServer(server: String): BalancerServer? {
        return getServer(ServerType(server), server)
    }

    companion object {
        lateinit var instance: BalancerBukkit
    }
}

val balancerBukkit = BalancerBukkit.instance