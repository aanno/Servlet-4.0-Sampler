import kotlin.String;

// read gradle.properties
val lint = false
val project_version = "1.0.0"
val gradle_version = "6.9.2"

plugins {
    war
    `maven-publish`
    id("com.coditory.manifest") // version "0.1.14"
    idea
    `eclipse-wtp`
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(components["web"])
        }
    }

    repositories {
        maven {
            name = "mavenLocalRepo"
            url = uri("../mvnrepo")
        }
    }
}

repositories {
    mavenCentral()
}

// does not work with Java 8 from IBM?!? (tp)
/*
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
*/
configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

eclipse {
    classpath {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

java {
    // withJavadocJar()
    // withSourcesJar()
}

eclipse {
    classpath {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks {

    // https://github.com/gradle/kotlin-dsl-samples/issues/956
    withType<Wrapper> {
        gradleVersion = gradle_version
        distributionType = Wrapper.DistributionType.ALL
    }

    named<Test>("test") {
        useJUnitPlatform()
    }

    withType<JavaCompile> {
        doFirst {
            options.compilerArgs.addAll(listOf(
                "-encoding", "windows-1252"
            ))
            if (lint) {
                options.compilerArgs.addAll(listOf(
                    "-Xlint:all"
                ))
            }
            println("${project.name}: args for for ${name} are ${options.allCompilerArgs}")
        }
    }

    // needed for publishing war with given manifest
    named("generateMetadataFileFor" + project.name.capitalize() + "Publication") {
        dependsOn("war")
    }

    // needed for building war with given manifest
    named("build") {
        dependsOn("war")
    }

    named("publish") {
        dependsOn("build")
    }

}
