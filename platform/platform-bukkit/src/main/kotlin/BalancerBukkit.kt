package ru.snapix.balancer

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.plugin.java.JavaPlugin
import ru.snapix.balancer.commands.ConnectCommand
import ru.snapix.balancer.handlers.Handler
import ru.snapix.balancer.handlers.bedwars.BedWarsHandler
import ru.snapix.balancer.handlers.lobby.LobbyHandler
import ru.snapix.balancer.handlers.murdermystery.MurderMysteryHandler
import ru.snapix.balancer.handlers.skywars.SkyWarsHandler
import ru.snapix.balancer.handlers.survival.AnarchyHandler
import ru.snapix.balancer.handlers.survival.ClassicHandler
import ru.snapix.balancer.handlers.survival.SkyBlockHandler
import ru.snapix.balancer.handlers.survival.SkyPvPHandler
import ru.snapix.balancer.handlers.thebridge.TheBridgeHandler
import ru.snapix.library.bukkit.SnapiLibraryBukkit
import ru.snapix.library.libs.commands.PaperCommandManager
import ru.snapix.library.network.ServerType
import ru.snapix.library.utils.async
import ru.snapix.library.utils.redisClient

class BalancerBukkit : JavaPlugin() {
    private lateinit var balancerServer: BalancerServer
    lateinit var handler: Handler

    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        handler = when (SnapiLibraryBukkit.instance.serverType) {
            ServerType.AUTH -> error("Cant use this plugin at Auth")
            ServerType.LOBBY -> LobbyHandler
            ServerType.CLASSIC -> ClassicHandler
            ServerType.SKYBLOCK -> SkyBlockHandler
            ServerType.SKYPVP -> SkyPvPHandler
            ServerType.ANARCHY -> AnarchyHandler
            ServerType.SKYWARS -> SkyWarsHandler
            ServerType.BEDWARS -> BedWarsHandler
            ServerType.MURDERMYSTERY -> MurderMysteryHandler
            ServerType.THEBRIDGE -> TheBridgeHandler
            ServerType.UNKNOWN -> error("Not found server type")
        }
        handler.enable()
        val commandManager = PaperCommandManager(this)
        commandManager.registerCommand(ConnectCommand())
        BalancerExpansion().register()
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