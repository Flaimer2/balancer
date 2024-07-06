package ru.snapix.balancer.settings

import ru.snapix.balancer.balancerBukkit
import ru.snapix.library.bukkit.settings.create
import ru.snapix.library.settings.Configuration

object Settings {
    private val messageConfig = Configuration.create("message.yml", MessageConfig::class.java, balancerBukkit)
    val message
        get() = messageConfig.data()

    fun reload() {
        messageConfig.reloadConfig()
    }
}
