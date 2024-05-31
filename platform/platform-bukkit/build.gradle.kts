plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

dependencies {
    compileSpigotApi()
    compileSnapiLibraryBukkit()
    implementation(project(":common"))
    compileOnly(files("libs/UltraSkyWars.jar"))
    compileOnly("me.clip:placeholderapi:2.11.6")
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