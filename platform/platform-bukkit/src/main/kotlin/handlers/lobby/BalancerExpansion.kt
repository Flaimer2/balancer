package ru.snapix.balancer.handlers.lobby

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import ru.snapix.balancer.Balancer
import ru.snapix.balancer.BalancerServer
import ru.snapix.balancer.Mode
import ru.snapix.balancer.balancerBukkit
import ru.snapix.balancer.extensions.canJoin
import ru.snapix.balancer.extensions.getBestServer
import ru.snapix.library.ServerType

class BalancerExpansion : PlaceholderExpansion() {
    override fun getIdentifier() = "balancer"
    override fun getAuthor() = "Flaimer"
    override fun getVersion() = "1.0.4"
    override fun persist() = true

    override fun onRequest(player: OfflinePlayer?, params: String): String? {
        if (params.startsWith("count:", ignoreCase = true)) {
            val arg = params.removePrefix("count:").lowercase().split(":")
            val serverType = ServerType(arg[0])

            if (arg.size == 1) {
                val servers = Balancer.servers(serverType)
                return servers.sumOf { it.players.size }.toString()
            }
            if (arg.size == 2) {
                val mode = Mode.entries.find { it.name == arg[1].uppercase() }
                return if (mode != null) {
                    Balancer.servers(serverType).filter { it.mode == mode }.sumOf { it.players.size }.toString()
                } else {
                    Balancer.servers(serverType).filter { it.map == arg[1] }.sumOf { it.players.size }.toString()
                }
            }
        }
        return if (player != null && player.isOnline) this.onPlaceholderRequest(player.player, params) else null
    }

    override fun onPlaceholderRequest(player: Player, params: String): String? {
        if (params.startsWith("best:", ignoreCase = true)) {
            val arg = params.removePrefix("best:").lowercase().split('_')
            val filter = arg[0].split(':')
            val value = arg[1]
            val serverType = ServerType(filter[0])

            when (filter.size) {
                1 -> {
                    val server = Balancer.getBestServer(player, serverType).firstOrNull()
                    return getValue(player, server, value)
                }

                2 -> {
                    val mode = Mode.entries.find { it.name == filter[1].uppercase() }
                    if (mode != null) {
                        val server = Balancer.getBestServer(player, serverType).firstOrNull { it.mode == mode }
                        return getValue(player, server, value)
                    } else {
                        val server = Balancer.getBestServer(player, serverType).firstOrNull { it.map == filter[1] }
                        return getValue(player, server, value)
                    }
                }
            }
        }

        if (params.startsWith("server:", ignoreCase = true)) {
            val arg = params.removePrefix("server:").lowercase().split('_')
            return getValue(player, Balancer.server(arg[0]), arg[1])
        }

        if (params.startsWith("current:", ignoreCase = true)) {
            val arg = params.removePrefix("current:").lowercase().split('_')
            return getValue(player, balancerBukkit.currentServer(), arg[1])
        }

        return null
    }

    private fun getValue(player: Player, server: BalancerServer?, key: String): String? = when (key.lowercase()) {
        "name" -> server?.name
        "map" -> server?.map
        "port" -> server?.port?.toString()
        "players" -> server?.players?.toString()
        "online" -> server?.players?.size?.toString()
        "maxplayers" -> server?.maxPlayers?.toString()
        "canjoin" -> server?.canJoin(player)?.toString()
        "servertype" -> server?.serverType?.toString()
        "state" -> server?.state?.toString()
        "mode" -> server?.mode?.toString()
        else -> null
    }
}