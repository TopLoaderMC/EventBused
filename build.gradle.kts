plugins {
    id("org.parchmentmc.writtenbooks") version "0.5.1" apply false
    id("com.github.hierynomus.license") version "0.16.1"
    `java-library`
    `maven-publish`
}

group = "com.github.toploadermc.eventbused"

subprojects {
    apply(plugin = "com.github.hierynomus.license")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "org.parchmentmc.writtenbooks")

    the<org.parchmentmc.writtenbooks.WrittenBooksExtension>().apply {
        repository         = "https://maven.socketmods.dev/repository/toploader/"
        repositoryUsername - providers.env("TopLoaderUsername")
        repositoryPassword - providers.env("TopLoaderPassword")
        githubRepo         - "TopLoaderMC/EventBused"
    }

    group = rootProject.group

    repositories {
        mavenCentral()
    }

    tasks.jar {
        manifest {
            attributes(
                "Specification-Title" to "eventbus",
                "Specification-Vendor" to "TopLoader",
                "Specification-Version" to "1",
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to "TopLoader",
                "Implementation-Timestamp" to Instant.now().iso8601()
            )
        }
    }

    license {
        header = rootProject.file("LICENSE-Header")
        ext["year"] = "2016-2021"
        mapping("java", "SLASHSTAR_STYLE")

        include("**/*.java")
    }

    tasks.test.configure { dependsOn("license") }

    publishing {
        publications.withType<MavenPublication>().configureEach {
            pom {
                licenses {
                    license {
                        name - "LGPLv2.1"
                        url - "https://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt"
                    }
                }
            }
        }
    }
}

typealias Instant = java.time.Instant
typealias DateTimeFormatter = java.time.format.DateTimeFormatter
fun Instant.iso8601() = DateTimeFormatter.ISO_INSTANT.format(this)

var org.parchmentmc.writtenbooks.WrittenBooksExtension.repository
    get() = this.releaseRepository.get()
    set(value) {
        this.releaseRepository  - value
        this.snapshotRepository - value
        this.bleedingRepository - value
    }

fun ProviderFactory.env(key: String) = this.environmentVariable(key).forUseAtConfigurationTime()
operator fun <T> Property<T>.minus(value: T) = this.set(value)
operator fun <T> Property<T>.minus(value: Provider<T>) = this.set(value)

fun MavenPomDeveloperSpec.developer(author: String) = developer {
    id - author
    name - author
}