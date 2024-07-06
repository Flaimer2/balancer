package ru.snapix.balancer.commands

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ru.snapix.balancer.Balancer
import ru.snapix.balancer.extensions.canJoin
import ru.snapix.balancer.extensions.connect
import ru.snapix.balancer.extensions.getBestServer
import ru.snapix.balancer.settings.Settings
import ru.snapix.library.libs.commands.BaseCommand
import ru.snapix.library.libs.commands.annotation.*
import ru.snapix.library.network.ServerType
import ru.snapix.library.utils.message
import ru.snapix.library.utils.translateAlternateColorCodes
import kotlin.math.ceil
import kotlin.random.Random

@CommandAlias("connect")
@CommandPermission("balancer.command.connect")
class ConnectCommand : BaseCommand() {
    private val config = Settings.message

    @CatchUnknown
    @Subcommand("help")
    fun onHelp(sender: CommandSender) {
        sender.sendMessage(config.connectCommand().help().map { translateAlternateColorCodes(it) }.toTypedArray())
    }

    @Default
    @Subcommand("server")
    fun onServer(player: Player, args: Array<String>) {
        if (args.isEmpty()) {
            player.message(config.connectCommand().useCommandServer())
            return
        }
        val server = Balancer.server(args[0])
        if (server == null) {
            player.message(config.connectCommand().serverNotFound(), "server_name" to args[0])
            return
        }
        if (!server.canJoin(player)) {
            player.message(config.connectCommand().cantJoin(), "server_name" to server.name)
            return
        }
        player.connect(server)
    }

    @Subcommand("type")
    fun onType(player: Player, args: Array<String>) {
        if (args.isEmpty()) {
            player.message(config.connectCommand().useCommandType())
            return
        }
        val type = ServerType[args[0]]
        if (type == ServerType.UNKNOWN) {
            player.message(config.connectCommand().unknownServerMode(), "server_mode" to args[0].lowercase())
            return
        }
        val server = Balancer.getBestServer(player, type).firstOrNull()
        if (server == null) {
            player.message(config.connectCommand().modeServerNotFound(), "server_mode" to type.name.lowercase())
            return
        }
        player.connect(server)
    }

    @Subcommand("mode")
    fun onMode(player: Player, args: Array<String>) {
        if (args.size < 2) {
            player.message(config.connectCommand().useCommandMode())
            return
        }
        val type = ServerType[args[0]]
        if (type == ServerType.UNKNOWN) {
            player.message(config.connectCommand().unknownServerMode(), "server_mode" to args[0].lowercase())
            return
        }
        val server = Balancer.getBestServer(player, type).firstOrNull { it.mode.name.lowercase() == args[1].lowercase() }
        if (server == null) {
            player.message(config.connectCommand().typeModeServerNotFound(), "server_mode" to type.name.lowercase(), "server_type" to args[1].lowercase())
            return
        }
        player.connect(server)
    }

    @CommandAlias("randomgame")
    fun onRandomGame(player: Player) {
        val bigListServers = Balancer.servers().filter { it.serverType.isGame() && it.canJoin(player) }.sortedByDescending { it.maxPlayers }
        val servers = bigListServers.dropLast(ceil(bigListServers.size.toDouble() / 4).toInt())
        val server = servers.randomOrNull() ?: bigListServers.randomOrNull()

        if (server == null) {
            player.message(config.connectCommand().randomGameNotFound())
            return
        }

        player.connect(server)
    }
}