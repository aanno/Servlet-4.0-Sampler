plugins {
    `java-test-convention`
}

dependencies {
    compileOnly(project(":ejb1"))

compileOnly("jakarta.platform:jakarta.jakartaee-api:9.1.0")
    compileOnly("log4j:log4j:1.2.17")
    implementation("commons-lang:commons-lang:2.6")
}

tasks {
}
