package ru.snapix.balancer.settings

import space.arim.dazzleconf.annote.ConfDefault.DefaultStrings
import space.arim.dazzleconf.annote.ConfDefault.DefaultString
import space.arim.dazzleconf.annote.SubSection

interface MessageConfig {
    @SubSection
    fun connectCommand(): ConnectCommand

    interface ConnectCommand {
        @DefaultStrings("fs", "gdgf")
        fun help(): List<String>

        @DefaultString("&cСервер %server_name% не найден!")
        fun serverNotFound(): String

        @DefaultString("&cВы не можете зайти на сервер %server_name%")
        fun cantJoin(): String

        @DefaultString("&cНе найден режим %server_mode%")
        fun unknownServerMode(): String

        @DefaultString("&cНе найден лучший сервер для режима %server_mode%")
        fun modeServerNotFound(): String

        @DefaultString("&cНе найден лучший сервер для режима %server_mode% c типом %server_type%")
        fun typeModeServerNotFound(): String

        @DefaultString("Используйте: /connect mode <SkyWars> <Solo>")
        fun useCommandMode(): String
    }
}
