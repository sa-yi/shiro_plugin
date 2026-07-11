plugins {
    `java-library`
}

group = "com.sayi.demo_plugin2"
version = "1.0-SNAPSHOT"

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public/") }
    mavenCentral()
}

dependencies {
    implementation("com.mikuac:shiro:2.5.4")

    implementation("org.springframework.boot:spring-boot-starter-websocket:3.2.0")
    implementation("org.springframework.ai:spring-ai-starter-model-deepseek:1.1.4")

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.retrofit2:converter-jackson:3.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("cn.hutool:hutool-all:5.8.47")

    implementation("cn.bigmodel.openapi:oapi-java-sdk:release-V4-2.3.1") {
        exclude(group = "ch.qos.logback", module = "logback-classic")
        exclude(group = "org.slf4j", module = "slf4j-api")
        exclude(group = "org.apache.logging.log4j")
    }
    compileOnly("ch.qos.logback:logback-classic")
    compileOnly("org.slf4j:slf4j-api")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from(sourceSets.main.get().output)

    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Built-By" to System.getProperty("user.name"),
                "Created-By" to "Gradle ${gradle.gradleVersion}"
            )
        )

        val dependenciesString = configurations
            .getByName("runtimeClasspath")
            .allDependencies
            .filterIsInstance<ExternalDependency>()
            .map {
                "${it.group}:${it.name}:${it.version}"
            }
            .distinct()
            .filterNot {
                it.startsWith("org.springframework") ||
                        it.startsWith("com.mikuac:shiro")
            }
            .joinToString(", ")

        attributes(mapOf("Dependencies" to dependenciesString))
    }
}
