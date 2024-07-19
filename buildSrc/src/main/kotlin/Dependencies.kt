import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.implementate(vararg name: String) {
    name.forEach { add("implementation", project(":$it")) }
}

fun DependencyHandler.compile(vararg name: String) {
    name.forEach { add("compileOnly", project(":$it")) }
}
