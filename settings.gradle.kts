pluginManagement {
    repositories {
        maven {
            name = "ParchmentMC"
            url = uri("https://maven.parchmentmc.org/")
        }
        gradlePluginPortal()
    }
}

rootProject.name = "EventBused"

include("core", "generic", "typed") //TODO: , "modlauncher-6")