plugins {
    id("me.champeau.jmh") version "0.6.5"
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

dependencies {
    api("org.checkerframework:checker-qual:3.21.4")
    api("org.slf4j:slf4j-api:1.7.36")

    testImplementation("org.slf4j:slf4j-log4j12:1.7.36")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.+")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.+")
}

tasks.test {
    useJUnitPlatform()
}

jmh {
    includes.set(listOf("com.github.toploadermc.eventbus.benchmark.Benchmarks"))
    benchmarkMode.set(listOf("avgt"))
    profilers.set(listOf("stack"))
    timeOnIteration.set("5s")
    warmup.set("5s")
    warmupIterations.set(3)
    iterations.set(3)
    fork.set(3)
    timeUnit.set("ns")
}

tasks.jmh.get().outputs.upToDateWhen { false }

publishing {
    publications.create<MavenPublication>("core") {
        from(project.components.findByName("java"))
        pom {
            name.set("EventBused - Core")
            description.set("An Event Bus library")
        }
    }
}