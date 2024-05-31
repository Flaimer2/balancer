package ru.snapix.balancer.handlers.lobby

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class LobbyListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        LobbyHandler.updateServer()
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        LobbyHandler.updateServer()
    }
}
