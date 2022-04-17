object Dependencies {

    object Test {
        const val jupiterApi = "org.junit.jupiter:junit-jupiter-api:${Versions.jUnit}"
        const val jupiterParams = "org.junit.jupiter:junit-jupiter-params:${Versions.jUnit}"
        const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.jUnit}"
        const val kluent = "org.amshove.kluent:kluent:${Versions.kluent}"

    }

    object Kotlinx {
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.kotlinxSerialization}"

    }

    object Ktor {
        //engine
        const val serverNetty = "io.ktor:ktor-server-netty:${Versions.ktor}"
        const val serverCore = "io.ktor:ktor-server-core:${Versions.ktor}"
        const val serverHostCommon = "io.ktor:ktor-server-host-common:${Versions.ktor}"

        //session auth
        const val auth = "io.ktor:ktor-server-auth:${Versions.ktor}"
        const val sessions = "io.ktor:ktor-server-sessions:${Versions.ktor}"
        const val authJwt = "io.ktor:ktor-server-auth-jwt:${Versions.ktor}"

        const val locations = "io.ktor:ktor-server-locations:${Versions.ktor}"
        const val websockets = "io.ktor:ktor-server-websockets:${Versions.ktor}"
        const val kotlinxSerialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
        const val htmlBuilder = "io.ktor:ktor-server-html-builder:${Versions.ktor}"
        const val cssJvm = "org.jetbrains:kotlin-css-jvm:1.0.0-pre.129-kotlin-1.4.20"
        const val micrometer = "io.ktor:ktor-server-metrics-micrometer:${Versions.ktor}"
        const val prometheus = "io.micrometer:micrometer-registry-prometheus:${Versions.prometeusVersion}"

        //testing
        const val serverTestHost = "io.ktor:ktor-server-test-host:${Versions.ktor}"
        const val serverTests = "io.ktor:ktor-server-tests:${Versions.ktor}"
        const val callLogging = "io.ktor:ktor-server-call-logging:${Versions.ktor}"

    }

    object Logging {
        const val logback = "ch.qos.logback:logback-classic:${Versions.logback}"
    }

    object Database {
        const val exposedCore = "org.jetbrains.exposed:exposed-core:${Versions.exposedVersion}"
        const val exposedDao = "org.jetbrains.exposed:exposed-dao:${Versions.exposedVersion}"
        const val exposedJdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposedVersion}"
        const val exposedJodatime = "org.jetbrains.exposed:exposed-jodatime:${Versions.exposedVersion}"
        const val hikariCP = "com.zaxxer:HikariCP:${Versions.hikariCPVersion}"
        const val postgresql = "org.postgresql:postgresql:${Versions.postgresqlVersion}"
    }

    object Koin {
        const val koinKtor = "io.insert-koin:koin-ktor:${Versions.koinVersion}"
        const val KoinLogger = "io.insert-koin:koin-logger-slf4j:${Versions.koinVersion}"
        const val CoinTestingJunit = "io.insert-koin:koin-test-junit5:${Versions.koinVersion}"
        const val KoinTesting = "io.insert-koin:koin-test:${Versions.koinVersion}"
    }

}