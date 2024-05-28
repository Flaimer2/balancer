dependencies {
    compileSnapiLibraryVelocity()
    compileSnapiLibraryBukkit()
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
}