package ru.snapix.balancer.settings

import ru.snapix.balancer.balancerBukkit
import ru.snapix.library.Configuration
import ru.snapix.library.create

object Settings {
    private val mainConfig = Configuration.create("config.yml", MainConfig::class.java, balancerBukkit)
    val config = mainConfig.data()
    private val messageConfig = Configuration.create("message.yml", MessageConfig::class.java, balancerBukkit)
    val message = messageConfig.data()

    fun reload() {
        mainConfig.reloadConfig()
        messageConfig.reloadConfig()
    }
}
