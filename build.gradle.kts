plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.20"
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
    tasks.test {
        useJUnitPlatform()
    }
    repositories {
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
        gradlePluginPortal()
    }

    dependencies {
        //koin implementation
        implementation("org.openapitools:openapi-generator-gradle-plugin:6.0.0-beta")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")

    }
    configurations.forEach { config ->
        config.resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-")) {
                useVersion(Versions.kotlin)
            }
        }
    }

}