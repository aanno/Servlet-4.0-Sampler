import kotlin.String;

// read gradle.properties
val lint = false
val project_version = "1.0.0"
val gradle_version = "7.3.3"

plugins {
    //  Plugin requests from precompiled scripts must not include a version numbskskjskjs
    `java-library`
    `maven-publish`
    id("com.coditory.manifest")
    idea
    `eclipse-wtp`
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
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

// seems not to work because of autodetection
// https://docs.gradle.org/current/userguide/toolchains.html
// https://docs.gradle.org/current/userguide/toolchains.html#sec:auto_detection
/*
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
        vendor.set(JvmVendorSpec.IBM)
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

    jar {
        into("META-INF/maven/${project.group}/${project.name}") {
            from(layout.buildDirectory.dir("publications/${project.name}"))
            rename {
                it.replace("pom-default.xml", "pom.xml")
            }
        }
        dependsOn(
            "generatePomFileFor" + project.name.capitalize() + "Publication")
    }

}
