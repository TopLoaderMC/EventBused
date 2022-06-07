//plugins {
//    id("me.champeau.jmh") version "0.6.5"
//}

repositories {
    maven("https://maven.minecraftforge.net/") {
        name = "forge"
        mavenContent {
            includeGroup("net.jodah")
        }
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

dependencies {
    api(project(":generic"))
    api("net.jodah:typetools:0.8.3")

    testImplementation(project(":core").dependencyProject.sourceSets.test.get().output)
    testImplementation(project(":generic").dependencyProject.sourceSets.test.get().output)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.+")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.+")
}

tasks.test {
    useJUnitPlatform()
}

//jmh {
//    jvmArgs.set(listOf("-Djmh.separateClasspathJAR=true"))
//    includes.set(listOf("com.github.toploadermc.eventbus.typed.benchmark.Benchmarks"))
//    benchmarkMode.set(listOf("avgt"))
//    profilers.set(listOf("stack"))
//    timeOnIteration.set("5s")
//    warmup.set("5s")
//    warmupIterations.set(3)
//    iterations.set(3)
//    fork.set(3)
//    timeUnit.set("ns")
//}
//
//tasks.jmh.get().outputs.upToDateWhen { false }

publishing {
    publications.create<MavenPublication>("typed") {
        from(project.components.findByName("java"))
        pom {
            name.set("EventBused - Typed")
            description.set("Provides TypeTool related niceties for EventBused")
        }
    }
}