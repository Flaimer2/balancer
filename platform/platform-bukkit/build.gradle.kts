plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

dependencies {
    implementate("common")

    compileOnly(libs.bukkit)
    compileOnly(libs.snapilibrary.bukkit)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.cooperation)

    compileOnly(files("libs/UltraSkyWars.jar"))
}

tasks {
    shadowJar {
        dependsOn(":common:shadowJar")
    }
}

bukkit {
    name = rootName
    main = "ru.snapix.balancer.BalancerBukkit"
    author = "Flaimer"
    website = "https://mcsnapix.ru"
    depend = listOf("SnapiLibrary", "PlaceholderAPI")
}