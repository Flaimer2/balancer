plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

dependencies {
    compileSpigotApi()
    compileSnapiLibraryBukkit()
    implementateCommon()
    compileOnly(files("libs/UltraSkyWars.jar"))
}

bukkit {
    name = rootName
    main = "ru.snapix.balancer.BalancerBukkit"
    author = "Flaimer"
    website = "https://mcsnapix.ru"
    depend = listOf("SnapiLibrary")
}