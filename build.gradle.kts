plugins {
    java
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("com.rabbitmq:amqp-client:5.12.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
//    implementation("org.springframework.boot:spring-boot-starter-messaging")
    implementation("com.alibaba:fastjson:1.2.75")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}