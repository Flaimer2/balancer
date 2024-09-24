package ru.snapix.balancer.handlers.skywars

import io.github.Leonardo0013YT.UltraSkyWars.api.events.USWGameFinishEvent
import io.github.Leonardo0013YT.UltraSkyWars.api.events.USWGameJoinEvent
import io.github.Leonardo0013YT.UltraSkyWars.api.events.USWGameQuitEvent
import io.github.Leonardo0013YT.UltraSkyWars.api.events.USWGameStartEvent
import io.github.Leonardo0013YT.UltraSkyWars.api.events.data.USWGamePlayerLoadEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class SkyWarsListener : Listener {
    @EventHandler
    fun onGameJoin(event: USWGameJoinEvent) {
        SkyWarsHandler.updateServer(event.game)
    }

    @EventHandler
    fun onGameQuit(event: USWGameQuitEvent) {
        SkyWarsHandler.updateServer(event.game)
    }

    @EventHandler
    fun onGameStart(event: USWGameStartEvent) {
        SkyWarsHandler.updateServer(event.game)
    }

    @EventHandler
    fun onGameFinish(event: USWGameFinishEvent) {
        SkyWarsHandler.updateServer(event.game)
    }
}