package ru.snapix.balancer.handlers.lobby

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import ru.snapix.balancer.Balancer
import ru.snapix.balancer.extensions.canJoin
import ru.snapix.balancer.extensions.connect
import ru.snapix.balancer.extensions.getBestServer
import ru.snapix.balancer.extensions.message
import ru.snapix.balancer.settings.Settings
import ru.snapix.library.ServerType

@CommandAlias("connect")
@CommandPermission("")
class ConnectCommand : BaseCommand() {
    private val config = Settings.message

    @Default
    @Subcommand("server")
    fun onServer(player: Player, args: String) {
        val server = Balancer.getServer(args)
        if (server == null) {
            player.message(config.connectCommand().serverNotFound(), Pair("server_name", args.lowercase()))
            return
        }
        if (!server.canJoin(player)) {
            player.message(config.connectCommand().cantJoin(), Pair("server_name", server.name))
            return
        }
        player.connect(server)
    }

    @Subcommand("type")
    fun onType(player: Player, args: String) {
        val type = ServerType(args)
        if (type == ServerType.UNKNOWN) {
            player.message(config.connectCommand().unknownServerMode(), Pair("server_mode", args.lowercase()))
            return
        }
        val server = Balancer.getBestServer(player, type).firstOrNull()
        if (server == null) {
            player.message(config.connectCommand().modeServerNotFound(), Pair("server_mode", type.name.lowercase()))
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
        val type = ServerType(args[0])
        if (type == ServerType.UNKNOWN) {
            player.message(config.connectCommand().unknownServerMode(), Pair("server_mode", args[0].lowercase()))
            return
        }
        val server = Balancer.getBestServer(player, type).firstOrNull { it.mode.name.lowercase() == args[1].lowercase() }
        if (server == null) {
            player.message(config.connectCommand().typeModeServerNotFound(), Pair("server_mode", type.name.lowercase()), Pair("server_type", args[1].lowercase()))
            return
        }
        player.connect(server)
    }
}