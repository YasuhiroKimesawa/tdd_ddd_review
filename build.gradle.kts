plugins {
    id("java")
    id("com.diffplug.spotless") version "6.3.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

spotless {
    java {
        // Use the default importOrder configuration
        importOrder()
        removeUnusedImports()

        googleJavaFormat()          // has its own section below
    }
}

val spotlessApply = tasks.getByName("spotlessApply")
val build = tasks.getByName("build")

build.dependsOn(spotlessApply)

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("io.vavr:vavr:0.10.4")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}