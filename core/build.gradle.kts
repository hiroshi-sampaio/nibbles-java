plugins {
    id("java")
    id("io.freefair.lombok") version "8.3"
}

group = "com.sampaio.hiroshi.nibbles.core"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}