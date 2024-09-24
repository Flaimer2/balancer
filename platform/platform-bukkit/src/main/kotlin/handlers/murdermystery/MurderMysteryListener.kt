package ru.snapix.balancer.handlers.murdermystery

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import plugily.projects.murdermystery.minigamesbox.api.events.game.PlugilyGameJoinEvent
import plugily.projects.murdermystery.minigamesbox.api.events.game.PlugilyGameLeaveEvent
import plugily.projects.murdermystery.minigamesbox.api.events.game.PlugilyGameStateChangeEvent

class MurderMysteryListener : Listener {
    @EventHandler
    fun onGameJoin(event: PlugilyGameJoinEvent) {
        MurderMysteryHandler.updateServer(event.arena)
    }

    @EventHandler
    fun onGameQuit(event: PlugilyGameLeaveEvent) {
        MurderMysteryHandler.updateServer(event.arena)
    }

    @EventHandler
    fun onGameStart(event: PlugilyGameStateChangeEvent) {
        MurderMysteryHandler.updateServer(event.arena)
    }
}