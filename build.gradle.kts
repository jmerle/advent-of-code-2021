plugins {
    application
    kotlin("jvm") version "1.6.0"
}

group = "com.jaspervanmerle.aoc2021"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.github.classgraph:classgraph:4.8.138")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

application {
    mainClass.set("$group.RunnerKt")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

project.projectDir
    .resolve("src/main/kotlin/com/jaspervanmerle/aoc2021/day")
    .listFiles()
    .map { it.nameWithoutExtension }
    .filter { it != "Day" }
    .map { it.substring(3).toInt() }
    .sorted()
    .forEach {
        task<org.gradle.api.tasks.JavaExec>("day${it.toString().padStart(2, '0')}") {
            group = "days"

            classpath = sourceSets["main"].runtimeClasspath
            mainClass.set(application.mainClass)
            args = listOf(it.toString())
        }
    }
