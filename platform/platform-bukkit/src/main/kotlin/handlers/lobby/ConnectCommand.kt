package ru.snapix.balancer.handlers.lobby

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import ru.snapix.balancer.Balancer
import ru.snapix.balancer.BalancerServer
import ru.snapix.balancer.Mode
import ru.snapix.balancer.extensions.canJoin
import ru.snapix.balancer.extensions.connect
import ru.snapix.library.ServerType

@CommandAlias("connect")
@CommandPermission("")
class ConnectCommand : BaseCommand() {
    // Example:
    // /connect <SW-1>
    // /connect server <SW-1>
    // /connect type <SkyWars>
    // /connect mode <SkyWars> <Solo>
    @Default
    @Subcommand("server")
    fun onServer(player: Player, args: String) {
        val server = Balancer.getServer(args) ?: return
        if (!server.canJoin(player)) {
            return
        }
        player.connect(server)
    }

    @Subcommand("type")
    fun onType(player: Player, args: String) {
        val type = ServerType(args)
        val best = getBestServer(player, type).firstOrNull() ?: return
        player.connect(best)
    }

    @Subcommand("mode")
    fun onMode(player: Player, args: Array<String>) {
        if (args.size != 2) {
            return
        }
        val type = ServerType(args[0])
        val mode = Mode.valueOf(args[1])
        val best = getBestServer(player, type).firstOrNull { it.mode == mode } ?: return
        player.connect(best)
    }

    private fun getBestServer(player: Player, type: ServerType): List<BalancerServer> = Balancer.getServers(type).filter { it.value.canJoin(player) }.values.sortedByDescending { it.maxPlayers }
}