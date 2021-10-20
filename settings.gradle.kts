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

include("core", "typed") //TODO: , "modlauncher-6")