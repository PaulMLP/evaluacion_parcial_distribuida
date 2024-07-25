plugins {
    id("java")
    id("io.quarkus") version "3.11.3"
}

group = "com.distribuida"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}


val quarkusVersion = "3.11.3"
dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:${quarkusVersion}"))

    //  Implementacion de CDI de quarkus
    implementation("io.quarkus:quarkus-arc")

    //  Motor de REST JAXRS
    implementation("io.quarkus:quarkus-resteasy-reactive")

    //  JSON
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")

    //  JPA Hibernate | Repository
    implementation("io.quarkus:quarkus-hibernate-orm-panache")

    //  JDBC Postgresql
    implementation("io.quarkus:quarkus-jdbc-postgresql:3.11.3")

    //  Microprofile Health
    implementation("io.quarkus:quarkus-smallrye-health")

    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")
}