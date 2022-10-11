plugins {
    id("java")
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("com.diffplug.spotless") version "6.3.0"
    // id("org.flywaydb.flyway") version "8.5.13"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}


//spotless {
//    java {
//        // Use the default importOrder configuration
//        importOrder()
//        removeUnusedImports()
//
//        googleJavaFormat()          // has its own section below
//    }
//}

//val spotlessApply = tasks.getByName("spotlessApply")
// val build = tasks.getByName("build")

//build.dependsOn(spotlessApply)

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
    maven("https://repo.spring.io/snapshot")
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.22")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("io.vavr:vavr:0.10.4")
    implementation("mysql:mysql-connector-java:8.0.30")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:2.2.2")
    testImplementation("org.flywaydb:flyway-core:8.5.13")
    testImplementation("org.flywaydb:flyway-mysql:8.5.13")
    testImplementation("org.testcontainers:junit-jupiter:1.17.3")
    testImplementation("org.testcontainers:mysql:1.17.3")
    testImplementation("org.testcontainers:testcontainers:1.17.3")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

//flyway {
//    url = "jdbc:h2:tcp://localhost/~/test"
//    user = "sa"
//    password = ""
//    locations = arrayOf("classpath:rdb-migration")
//}
