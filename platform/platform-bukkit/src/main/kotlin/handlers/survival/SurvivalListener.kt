package ru.snapix.balancer.handlers.survival

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class SurvivalListener(val handler: SurvivalHandler) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        handler.updateServer()
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        handler.updateServer()
    }
}