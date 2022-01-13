plugins {
    `java-test-convention`
}

dependencies {
    compileOnly(project(":ejb1"))

    // https://github.com/eclipse-ee4j/jakartaee-tutorial-examples/
    compileOnly("jakarta.platform:jakarta.jakartaee-api:9.1.0")
    compileOnly("log4j:log4j:1.2.17")
    implementation("commons-lang:commons-lang:2.6")
}

tasks {
    jar {
        manifest {
            attributes(
                "Class-Path" to "ejb1.jar",
                "Comment" to  "manifest by gradle-jar-manifest-attributes"
            )
        }
    }
}
