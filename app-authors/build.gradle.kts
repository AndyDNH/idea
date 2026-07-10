plugins {
    id("java")
    id("io.quarkus") version "3.35.2"
    id ("io.freefair.lombok") version "9.2.0"
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}


val quarkusVersion =  "3.35.2"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

dependencies {
//    esta dependencia es la que coloca las versiones, sin esta dependencia si deberia especificar la version
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:${quarkusVersion}"))
//    CDI
    implementation("io.quarkus:quarkus-rest")
//    REST
    implementation("io.quarkus:quarkus-arc")

    implementation("io.quarkus:quarkus-rest-jsonb")
    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-flyway")
    implementation("io.quarkus:quarkus-jackson")

    implementation("org.flywaydb:flyway-database-postgresql:12.5.0")

    implementation("io.quarkus:quarkus-rest-client-jsonb")
    implementation("io.quarkus:quarkus-rest-client")

//    Service Discovery
    implementation("io.quarkus:quarkus-smallrye-stork")
    implementation("io.smallrye.stork:stork-service-discovery-static-list:2.6.3")

    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")

//    Telemetria
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
}

tasks.test {
    useJUnitPlatform()
}