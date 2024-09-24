package ru.snapix.balancer.handlers.thebridge

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import plugily.projects.thebridge.minigamesbox.api.events.game.PlugilyGameStateChangeEvent
import plugily.projects.thebridge.minigamesbox.api.events.game.PlugilyGameJoinEvent
import plugily.projects.thebridge.minigamesbox.api.events.game.PlugilyGameLeaveEvent

class TheBridgeListener : Listener {
    @EventHandler
    fun onGameJoin(event: PlugilyGameJoinEvent) {
        TheBridgeHandler.updateServer(event.arena)
    }

    @EventHandler
    fun onGameQuit(event: PlugilyGameLeaveEvent) {
        TheBridgeHandler.updateServer(event.arena)
    }

    @EventHandler
    fun onGameStart(event: PlugilyGameStateChangeEvent) {
        TheBridgeHandler.updateServer(event.arena)
    }
}