val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.20"
    id("io.ktor.plugin") version "2.1.3"
}

group = "com.learning"
version = "0.0.1"
application {
    mainClass.set("com.learning.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    val koinVersion = "3.2.0"

    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    //Kotest
    testImplementation(platform("io.kotest:kotest-bom:5.5.3"))
    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-extensions")
    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("io.kotest:kotest-assertions-json")
    testImplementation("io.kotest:kotest-framework-datatest")

    //mockk
    testImplementation("io.mockk:mockk:1.13.2")

    // To run tests on Apple Silicon systems
    testImplementation("net.java.dev.jna:jna:5.12.1")
}