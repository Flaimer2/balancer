package ru.snapix.balancer.settings

import ru.snapix.balancer.balancerBukkit
import ru.snapix.library.Configuration
import ru.snapix.library.create

object Settings {
    private val mainConfig = Configuration.create("config.yml", MainConfig::class.java, balancerBukkit)
    val config = mainConfig.data()

    fun reload() {
        mainConfig.reloadConfig()
    }
}
