import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.shadow)
    alias(libs.plugins.serialization)
    `maven-publish`
}

allprojects {
    group = rootGroup
    version = rootVersion

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.aikar.co/content/groups/aikar/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    dependencies {
        compileOnly(kotlin("stdlib"))
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-XDenableSunApiLintControl"))
    }

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        if (project.name.startsWith("universal")) {
            archiveFileName.set("$rootName.jar")
        } else {
            archiveFileName.set("$rootName-${project.name.removePrefix("platform-").uppercaseFirstChar()}.jar")
        }
        archiveAppendix.set("")
        archiveClassifier.set("")
    }
}

subprojects
    .filter { it.name.startsWith("platform-") }
    .forEach { proj ->
        proj.publishing { applyToSub(proj) }
    }

fun PublishingExtension.applyToSub(subProject: Project) {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "${project.name.lowercase()}-${subProject.name.removePrefix("platform-")}"
            groupId = rootGroup
            version = rootVersion
            artifact(subProject.tasks["shadowJar"])
        }
    }
}