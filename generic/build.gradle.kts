//plugins {
//    id("me.champeau.jmh") version "0.6.5"
//}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

dependencies {
    api(project(":core"))

    testImplementation(project(":core").dependencyProject.sourceSets.test.get().output)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.+")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.+")
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
    publications.create<MavenPublication>("generic") {
        from(project.components.findByName("java"))
        pom {
            name.set("EventBused - Generic")
            description.set("Provides Generic Events for EventBused")
        }
    }
}