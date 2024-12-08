plugins {
    kotlin("jvm") version "2.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("com.squareup.moshi:moshi:1.13.0") // Dependencia para Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0") // Dependencia para soporte de Kotlin
    implementation ("org.jetbrains.kotlin:kotlin-reflect:1.8.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(15)
}