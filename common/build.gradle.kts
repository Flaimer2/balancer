dependencies {
    compileSnapiLibraryVelocity()
    compileSnapiLibraryBukkit()
    compileSpigotApi()
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
}