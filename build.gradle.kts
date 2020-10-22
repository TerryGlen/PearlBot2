import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.72"
    application
}
group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

application{
    mainClass.set("PearlBot")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit", "junit", "4.12")
    implementation("com.jessecorbett:diskord-jvm:1.7.3")
    implementation("org.jetbrains.exposed:exposed-core:0.25.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.25.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.25.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.25.1")
    implementation("mysql:mysql-connector-java:5.1.34")
}

tasks.withType<KotlinCompile>{
    kotlinOptions.jvmTarget = "1.8"
}