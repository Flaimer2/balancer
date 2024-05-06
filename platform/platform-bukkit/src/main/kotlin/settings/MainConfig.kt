package ru.snapix.balancer.settings

import ru.snapix.library.ServerType
import space.arim.dazzleconf.annote.ConfDefault.DefaultString

interface MainConfig {
    @DefaultString("UNKNOWN")
    fun gameType(): ServerType
}