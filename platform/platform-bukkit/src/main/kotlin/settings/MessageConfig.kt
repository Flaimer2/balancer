package ru.snapix.balancer.settings

import ru.snapix.library.libs.dazzleconf.annote.ConfDefault.DefaultString
import ru.snapix.library.libs.dazzleconf.annote.ConfDefault.DefaultStrings
import ru.snapix.library.libs.dazzleconf.annote.SubSection

interface MessageConfig {
    @SubSection
    fun connectCommand(): ConnectCommand

    interface ConnectCommand {
        @DefaultStrings("fs", "gdgf")
        fun help(): List<String>

        @DefaultString("&cСервер %server_name% не найден!")
        fun serverNotFound(): String

        @DefaultString("Рандомная игра не найдена. Вы не можете войти ни в одну игру")
        fun randomGameNotFound(): String

        @DefaultString("&cВы не можете зайти на сервер %server_name%")
        fun cantJoin(): String

        @DefaultString("&cНе найден режим %server_mode%")
        fun unknownServerMode(): String

        @DefaultString("&cНе найден лучший сервер для режима %server_mode%")
        fun modeServerNotFound(): String

        @DefaultString("&cНе найден лучший сервер для режима %server_mode% c типом %server_type%")
        fun typeModeServerNotFound(): String

        @DefaultString("Используйте: /connect server <SkyWars-1>")
        fun useCommandServer(): String

        @DefaultString("Используйте: /connect type <SkyWars>")
        fun useCommandType(): String

        @DefaultString("Используйте: /connect mode <SkyWars> <Solo>")
        fun useCommandMode(): String
    }
}
