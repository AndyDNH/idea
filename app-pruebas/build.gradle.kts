plugins {
    java
    id("io.quarkus") version "3.35.2"
    id("io.freefair.lombok") version "9.2.0"
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-rest-client-jsonb")
    implementation("io.quarkus:quarkus-flyway")
    implementation("io.quarkus:quarkus-rest-jsonb")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("io.quarkus:quarkus-smallrye-stork")
    implementation("io.smallrye.stork:stork-service-discovery-static-list:2.7.10")

    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")

    testImplementation("io.quarkus:quarkus-junit")
    testImplementation("io.rest-assured:rest-assured")

}

group = "org.acme"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}