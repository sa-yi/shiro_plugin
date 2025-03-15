plugins {
    `java-library`

}

group = "com.sayi.demo_plugin"
version = "1.0-SNAPSHOT"

repositories {
    maven { url =uri("https://maven.aliyun.com/repository/public/") }

    mavenCentral()

    flatDir{
        dirs("libs")
    }
}

dependencies {

    implementation(files("libs/shiro-2.3.6.jar"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.springframework.boot:spring-boot-starter:3.4.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from(sourceSets.main.get().output)

    manifest {
        attributes(
            "Implementation-Title" to "DemoPlugin",
            "Built-By" to System.getProperty("user.name")
        )
    }

    // 修改include为包含全部编译结果
    includeEmptyDirs = false
}
