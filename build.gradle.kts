val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val prometeus_version: String by project
val exposedVersion: String by project
val koinVersion: String by project


plugins {
    kotlin("jvm") version "1.6.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.20"

}

    repositories {
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
        gradlePluginPortal()
    }


group = "com.killua"
version = "0.0.1"

subprojects {

    group = rootProject.group
    version = rootProject.version

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
    repositories {
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
        gradlePluginPortal()
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        //deploy ant helper
        //koin implementation
        implementation("io.insert-koin:koin-ktor:$koinVersion")
        implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    }
    configurations.forEach { config ->
        config.resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-")) {
                useVersion(Versions.kotlin)
            }
        }
    }

}