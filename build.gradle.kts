plugins {
    `java-library`

}

group = "com.sayi.demo_plugin"
version = "1.0-SNAPSHOT"

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public/") }
    mavenCentral()
}

dependencies {

    //implementation(files("libs/shiro-2.3.6.jar"))
    api("org.springframework.boot:spring-boot-starter-websocket:3.4.0")
    api("org.springframework.boot:spring-boot-starter:3.4.0")
    compileOnly("com.mikuac:shiro:2.3.6")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")


    implementation("cn.bigmodel.openapi:oapi-java-sdk:release-V4-2.3.1") {
        exclude(group = "ch.qos.logback", module = "logback-classic")
        exclude(group = "org.slf4j", module = "slf4j-api")
        exclude(group = "org.apache.logging.log4j")
    }
    compileOnly("ch.qos.logback:logback-classic")
    compileOnly("org.slf4j:slf4j-api")

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
                "Implementation-Title" to "DemoPlugin",
                "Built-By" to System.getProperty("user.name")
            )
        )
        // 生成并添加依赖清单
        val dependenciesString = configurations
            .getByName("runtimeClasspath") // 使用运行时配置获取实际解析的依赖
            .resolvedConfiguration
            .resolvedArtifacts
            .map {
                "${it.moduleVersion.id.group}:${it.moduleVersion.id.name}:${it.moduleVersion.id.version}"
            }
            .distinct()
            .filterNot {//一些库应当由Shiro主程序加载
                it.startsWith("org.springframework") || // 过滤Spring Boot
                        it.startsWith("com.mikuac:shiro") // 过滤shiro核心库
            }
            .joinToString(", ")

        attributes(mapOf("Dependencies" to dependenciesString))
    }
}