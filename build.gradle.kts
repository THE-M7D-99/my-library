plugins {
    kotlin("multiplatform") version "1.8.0"
    id("maven-publish")
}

group = "com.example.library"
version = "0.1"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
                implementation("com.google.code.gson:gson:2.8.9") // Replace with the latest version
                implementation("org.json:json:20210307") // Use the latest version available
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["java"])
                groupId = "com.github.THE-M7D-99"
                artifactId = "my-library"
                version = "0.1"
            }
        }
    }
}