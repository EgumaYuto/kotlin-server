import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.2.31"

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlinModule("gradle-plugin", kotlinVersion))
    }
}

plugins {
    java
}

group = "io.github.egumayuto"
version = "1.0-SNAPSHOT"

apply {
    plugin("kotlin")
}

val kotlinVersion: String by extra

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    compile(kotlin("stdlib-jdk8", kotlinVersion))

    // log4j2
    compile("org.apache.logging.log4j", "log4j-core", "2.11.0")
    compile("org.apache.logging.log4j", "log4j-api", "2.11.0")

    // junit
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}