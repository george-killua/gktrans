import Dependencies.Database
import Dependencies.Ktor
import Dependencies.Logging
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
    project.setProperty("mainClassName", mainClass.get())
}
val sshAntTask = configurations.create("sshAntTask")

dependencies {
    implementation(Ktor.serverNetty)
    implementation(Ktor.serverCore)
    implementation(Ktor.serverHostCommon)

    implementation(Ktor.auth)
    implementation(Ktor.authJwt)

    implementation(Ktor.locations)
    implementation(Ktor.websockets)
    implementation(Ktor.sessions)
    implementation(Ktor.kotlinxSerialization)

    implementation(Ktor.htmlBuilder)
    testImplementation(Ktor.cssJvm)
    implementation(Ktor.micrometer)
    implementation(Ktor.prometheus)

    testImplementation(Ktor.serverTestHost)
    implementation(Ktor.serverTests)

    implementation(Logging.logback)
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")

    //database implementation
    implementation(Database.exposedCore)
    implementation(Database.exposedDao)
    implementation(Database.exposedJdbc)
    implementation(Database.exposedJodatime)
    implementation(Database.hikariCP)
    implementation(Database.postgresql)
    //hasher
    implementation("de.nycode:bcrypt:2.3.0")
    sshAntTask("org.apache.ant:ant-jsch:1.10.12")

}

tasks {

    withType<KotlinCompile>().all {
        kotlinOptions.freeCompilerArgs += "-Xopt-in=io.ktor.util.KtorExperimentalAPI"
    }
}
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get()
        )
    }
}
ant.withGroovyBuilder {
    "taskdef"(
        "name" to "scp",
        "classname" to "org.apache.tools.ant.taskdefs.optional.ssh.Scp",
        "classpath" to configurations.get("sshAntTask").asPath
    )
    "taskdef"(
        "name" to "ssh",
        "classname" to "org.apache.tools.ant.taskdefs.optional.ssh.SSHExec",
        "classpath" to configurations.get("sshAntTask").asPath
    )
}

task("deploy") {
    outputs.cacheIf { true }
    dependsOn("clean", "shadowJar")
    ant.withGroovyBuilder {
        doLast {
            val knownHosts = File.createTempFile("knownhosts", "txt")
            val user = "root"
            val host = "145.14.157.4"
            val pk = file("keys/apissh")
            val jarFileName = "transportion-$version-all.jar"
            try {
                "scp"(
                    "file" to file("build/libs/$jarFileName"),
                    "todir" to "$user@$host:/root/api",
                    "keyfile" to pk,
                    "trust" to true,
                    "knownhosts" to knownHosts
                )
                "ssh"(
                    "host" to host,
                    "username" to user,
                    "keyfile" to pk,
                    "trust" to true,
                    "knownhosts" to knownHosts,
                    "command" to "mv /root/api/$jarFileName /root/api/api-server.jar"
                )
                "ssh"(
                    "host" to host,
                    "username" to user,
                    "keyfile" to pk,
                    "trust" to true,
                    "knownhosts" to knownHosts,
                    "command" to "systemctl stop api"
                )
                "ssh"(
                    "host" to host,
                    "username" to user,
                    "keyfile" to pk,
                    "trust" to true,
                    "knownhosts" to knownHosts,
                    "command" to "systemctl start api"
                )
            } finally {
                knownHosts.delete()
            }
        }
    }
}