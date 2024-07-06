plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "SnapiBalancer"

include("common")
include("platform:platform-bukkit")
include("platform:platform-velocity")
include("universal")