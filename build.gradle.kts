plugins {
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    id("com.github.johnrengelman.shadow") version shadowJarVersion
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
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "maven-publish")

    dependencies {
        compileOnly(kotlin("stdlib"))
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-XDenableSunApiLintControl"))
    }

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveAppendix.set("")
        archiveClassifier.set("")
    }
}

subprojects
    .filter { it.name.startsWith("platform") }
    .forEach { proj ->
        proj.publishing { applyToSub(proj) }
    }

fun PublishingExtension.applyToSub(subProject: Project) {
    publications {
        create<MavenPublication>("maven") {
            artifactId = project.name.lowercase()
            groupId = rootGroup
            version = rootVersion
            artifact(subProject.tasks["shadowJar"])
        }
    }
}