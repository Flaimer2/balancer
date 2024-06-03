dependencies {
    compileVelocityApi()
    compileSnapiLibraryVelocity()
    implementateCommon()
}

tasks {
    shadowJar {
        dependsOn(":common:shadowJar")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask> {
    dependsOn(":common:shadowJar")
}
