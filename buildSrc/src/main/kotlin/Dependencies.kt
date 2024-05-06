import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.compileModule(vararg name: String) {
    name.forEach { add("compileOnly", project(":module:$it")) }
}

fun DependencyHandler.implementateModule(vararg name: String) {
    name.forEach { add("implementation", project(":module:$it")) }
}

fun DependencyHandler.compileCommon() {
    add("compileOnly", project(":common"))
}

fun DependencyHandler.implementateCommon() {
    add("implementation", project(":common"))
}

fun DependencyHandler.compileVelocity() {
    add("compileOnly", project(":platform:platform-velocity"))
}

fun DependencyHandler.implementateVelocity() {
    add("implementation", project(":platform:platform-velocity"))
}

fun DependencyHandler.compileBukkit() {
    add("compileOnly", project(":platform:platform-bukkit"))
}

fun DependencyHandler.implementateBukkit() {
    add("implementation", project(":platform:platform-bukkit"))
}

fun DependencyHandler.compileVelocityApi() {
    add("kapt", "com.velocitypowered:velocity-api:$velocityVersion")
    add("compileOnly", "com.velocitypowered:velocity-api:$velocityVersion")
}

fun DependencyHandler.compileSpigotApi() {
    add("compileOnly", "com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT")
}

fun DependencyHandler.compileProtocolize() {
    add("compileOnly", "dev.simplix:protocolize-api:$protocolizeVersion")
}

fun DependencyHandler.implementateSnapiLibraryVelocity() {
    add("implementation", "ru.snapix:snapilibrary:$snapiLibraryVersion-velocity")
}

fun DependencyHandler.compileSnapiLibraryVelocity() {
    add("compileOnly", "ru.snapix:snapilibrary:$snapiLibraryVersion-velocity")
}

fun DependencyHandler.implementateSnapiLibraryBukkit() {
    add("implementation", "ru.snapix:snapilibrary:$snapiLibraryVersion-bukkit")
}

fun DependencyHandler.compileSnapiLibraryBukkit() {
    add("compileOnly", "ru.snapix:snapilibrary:$snapiLibraryVersion-bukkit")
}

fun DependencyHandler.testImplementateSnapiLibrary() {
    add("testImplementation", "ru.snapix:snapilibrary:$snapiLibraryVersion-bukkit")
}
