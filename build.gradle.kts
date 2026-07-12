plugins {
    `java-library`
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
group = "com.sayi.demo_plugin2"
version = "1.0-SNAPSHOT"

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public/") }
    mavenCentral()
}

dependencies {
    implementation("com.mikuac:shiro:2.5.4")

    implementation("org.springframework.ai:spring-ai-starter-model-deepseek:1.1.4")

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.retrofit2:converter-jackson:3.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("cn.hutool:hutool-all:5.8.47")

    compileOnly("org.slf4j:slf4j-api")
    compileOnly("ch.qos.logback:logback-classic")

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
            .joinToString(", ")

        attributes(mapOf("Dependencies" to dependenciesString))
    }
}
