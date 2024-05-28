package ru.snapix.balancer.handlers.skywars

import io.github.Leonardo0013YT.UltraSkyWars.api.events.USWGameFinishEvent
import io.github.Leonardo0013YT.UltraSkyWars.api.events.USWGameQuitEvent
import io.github.Leonardo0013YT.UltraSkyWars.api.events.USWGameStartEvent
import io.github.Leonardo0013YT.UltraSkyWars.api.events.data.USWGamePlayerLoadEvent
import org.bukkit.event.Listener

class SkyWarsListener : Listener {
    fun onGamePlayerLoad(event: USWGamePlayerLoadEvent) {
        SkyWarsHandler.updateServer(event.game)
    }

    // TODO: Поменять в плагине UltraSkyWars вызов event'а после удаление игрока
    fun onGameQuit(event: USWGameQuitEvent) {
        SkyWarsHandler.updateServer(event.game)
    }

    // TODO: Поменять в плагине UltraSkyWars вызов event'а после изменения статуса
    fun onGameStart(event: USWGameStartEvent) {
        SkyWarsHandler.updateServer(event.game)
    }

    fun onGameFinish(event: USWGameFinishEvent) {
        SkyWarsHandler.updateServer(event.game)
    }
}