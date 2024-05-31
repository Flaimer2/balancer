package ru.snapix.balancer

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.plugin.java.JavaPlugin
import ru.snapix.balancer.handlers.Handler
import ru.snapix.balancer.handlers.lobby.LobbyHandler
import ru.snapix.balancer.handlers.skywars.SkyWarsHandler
import ru.snapix.balancer.settings.Settings
import ru.snapix.library.ServerType

class BalancerBukkit : JavaPlugin() {
    private lateinit var balancerServer: BalancerServer
    lateinit var handler: Handler
    lateinit var serverType: ServerType

    override fun onLoad() {
        instance = this
        serverType = Settings.config.gameType()
    }

    override fun onEnable() {
        when (serverType) {
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
        Balancer.pool.resource.apply {
            val hash = mutableMapOf<String, String>()
            hash[balancerServer.name] = Json.encodeToString(balancerServer)
            hset(balancerServer.serverType.redisKeyServer, hash)
        }
    }


    companion object {
        lateinit var instance: BalancerBukkit
    }
}

val balancerBukkit = BalancerBukkit.instance