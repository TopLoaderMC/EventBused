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

include("eventbus-core", "eventbus-typed") //TODO: , "eventbus-modlauncher-6")