import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    id("org.jetbrains.intellij") version "0.4.12"
    id("io.github.art.project") version "1.0.96"
}

group = "io.github.art"
version = "1.0.0"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    embedded("io.github.sharelison:jsontojava:1.0.1")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

intellij {
    version = "2019.1"
}

tasks.withType(Wrapper::class.java) {
    gradleVersion = "5.6"
}

tasks.withType(KotlinCompile::class.java).all {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}