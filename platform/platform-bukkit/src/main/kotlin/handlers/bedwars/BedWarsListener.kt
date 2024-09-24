package ru.snapix.balancer.handlers.bedwars

import com.axeelheaven.hbedwars.api.events.game.arena.BedWarsGameStateChangeEvent
import com.axeelheaven.hbedwars.api.events.game.player.BedWarsPlayerJoinEvent
import com.axeelheaven.hbedwars.api.events.game.player.BedWarsPlayerLeaveEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class BedWarsListener : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun onGameJoin(event: BedWarsPlayerJoinEvent) {
        BedWarsHandler.updateServer(event.arena)
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onGameQuit(event: BedWarsPlayerLeaveEvent) {
        BedWarsHandler.updateServer(event.arena)
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onGameStart(event: BedWarsGameStateChangeEvent) {
        BedWarsHandler.updateServer(event.arena)
    }
}