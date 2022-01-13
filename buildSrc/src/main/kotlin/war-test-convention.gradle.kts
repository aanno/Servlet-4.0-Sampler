import kotlin.String;

// read gradle.properties
val lint = false
val project_version = "1.0.0"
val gradle_version = "7.3.3"

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

    // hint (tp): Not working at present!
    jar {
        manifest {
            attributes("Class-Path" to "test1-${project_version}.jar test2-${project_version}.jar",
                "Comment1" to  "manifest by gradle-jar-manifest-attributes")
        }
        into("META-INF/maven/${project.group}/${project.name}") {
            from(layout.buildDirectory.dir("publications/${project.name}"))
            rename {
                it.replace("pom-default.xml", "pom.xml")
            }
        }
        dependsOn(
            "generatePomFileFor" + project.name.capitalize() + "Publication")
    }

    // works only with gradle 7
    named<War>("war") {
        manifest = project.the<JavaPluginExtension>().manifest {
            attributes("Class-Path" to "test1-${project_version}.jar test2-${project_version}.jar",
                "Comment2" to  "manifest by gradle-war-manifest-attributes")
        }
    }

    // needed for publishing war with given manifest
    named("generateMetadataFileFor" + project.name.capitalize() + "Publication") {
        dependsOn("war")
    }

    named("jar") {
        dependsOn("generateMetadataFileFor" + project.name.capitalize() + "Publication")
    }

    // needed for building war with given manifest
    named("build") {
        dependsOn("war")
    }

    named("publish") {
        dependsOn("build")
    }

}
