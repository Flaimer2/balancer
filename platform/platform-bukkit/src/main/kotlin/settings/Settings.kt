package ru.snapix.balancer.settings

import ru.snapix.balancer.balancerBukkit
import ru.snapix.library.config.Configuration
import ru.snapix.library.config.create

object Settings {
    private val messageConfig = Configuration.create("message.yml", MessageConfig::class.java, balancerBukkit)
    val message
        get() = messageConfig.data()

    fun reload() {
        messageConfig.reloadConfig()
    }
}
