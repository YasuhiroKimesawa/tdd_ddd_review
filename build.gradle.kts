plugins {
    id("java")
    id("com.diffplug.spotless") version "6.3.0"
    id("org.flywaydb.flyway") version "8.5.13"
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
    testImplementation("com.h2database:h2:2.1.214")
    implementation("io.vavr:vavr:0.10.4")
    implementation("org.mybatis:mybatis:3.5.10")
    testImplementation("org.flywaydb:flyway-core:8.5.13")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

flyway {
    url = "jdbc:h2:tcp://localhost/~/test"
    user = "sa"
    password = ""
    locations = arrayOf("classpath:rdb-migration")
}
