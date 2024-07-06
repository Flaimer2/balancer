dependencies {
    kapt(libs.velocityapi)

    implementate("common")

    compileOnly(libs.velocityapi)
    compileOnly(libs.snapilibrary.velocity)
    compileOnly(libs.cooperation)
}

tasks {
    shadowJar {
        dependsOn(":common:shadowJar")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask> {
    dependsOn(":common:shadowJar")
}
