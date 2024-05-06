plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

dependencies {
    compileSnapiLibraryBukkit()
    compileSpigotApi()
}

bukkit {
    main = "ru.snapix.example.ExampleBukkit"
    author = "Flaimer"
    website = "https://mcsnapix.ru"
}