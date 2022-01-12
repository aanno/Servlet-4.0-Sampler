plugins {
    ear
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            // from("$buildDir/libs/ear.ear")
            artifact("$buildDir/libs/ear.ear")
        }
    }

    repositories {
        maven {
            name = "LocalMavenRepo"
            url = uri("../mvnrepo")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    deploy(project(":war"))

    earlib(group = "log4j", name = "log4j", version = "1.2.17")
    earlib("commons-logging:commons-logging:1.2")
}
