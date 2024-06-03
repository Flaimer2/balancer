package ru.snapix.balancer.extensions

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import ru.snapix.balancer.Balancer
import ru.snapix.balancer.BalancerServer
import ru.snapix.balancer.PlayerConnect
import ru.snapix.balancer.redisKeyConnect
import ru.snapix.library.ServerType
import ru.snapix.library.useAsync

fun Player.connect(server: BalancerServer) {
    if (server.serverType == ServerType.UNKNOWN) return
    Balancer.jedis.useAsync { publish(redisKeyConnect, Json.encodeToString(PlayerConnect(name, server))) }
}

fun Player.message(message: String) {
    sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%player_name%", name))
}

fun Player.message(message: String, vararg pair: Pair<String, String>) {
    var result = message
    pair.forEach { result = result.replace("%${it.first}%", it.second) }
    message(result)
}

fun Player.message(messages: Collection<String>) {
    messages.forEach { message(it) }
}

fun Player.message(messages: Array<String>) {
    messages.forEach { message(it) }
}