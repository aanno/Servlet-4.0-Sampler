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
    withSourcesJar()
}

/*
// https://docs.gradle.org/current/userguide/cross_project_publications.html
val fixedWar by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
    // If you want this configuration to share the same dependencies, otherwise omit this line
    // extendsFrom(configurations["implementation"], configurations["runtimeOnly"])
}

artifacts {
    add("fixedWar", file("${buildDir}/libs/${project.name}-${version}.war")) {
        builtBy(tasks.named("build"))
    }
}
 */

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
                "Comment" to  "manifest by gradle-jar-manifest-attributes")
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

    register<War>("manifestWar") {
        manifest = project.the<JavaPluginExtension>().manifest {
            attributes("Class-Path" to "test1-${project_version}.jar test2-${project_version}.jar",
                "Comment" to  "manifest by gradle-manifestWar-manifest-attributes")
        }
    }

    // HACK to fix 'war' plugin not have same MANIFEST support (tp)
    // https://dzone.com/articles/gradle-goodness-replace-files-in-archives
    // Register new task replaceZip of type org.gradle.api.tasks.bundling.Zip.
    //
    // Attention: $version is deprecated and $archiveVersion does not work (tp)
    register<Zip>("replaceZip") {
        dependsOn("war")

        archiveBaseName.set(project.name)
        // destinationDirectory.set(project.layout.buildDirectory.dir("archives"))
        destinationDirectory.set(file("$buildDir/libs"))
        // Include the content of the original archive.
        from(project.zipTree("$buildDir/libs/${project.name}-${project_version}.war")) {
            // But leave out the file we want to replace.
            exclude("META-INF/MANIFEST.MF")
        }
        from("$buildDir/resources/main/META-INF") {
            // rename("MANIFEST.MF", "META-INF/MANIFEST.MF")
            into("META-INF")
        }
        // Add files with same name to replace.
        /*
        from(file("src/new-archive")) {
            include("README")
        }
        */
        // As archives allow duplicate file names we want to fail
        // the build when that happens, because we want to replace
        // an existing file.
        duplicatesStrategy = DuplicatesStrategy.FAIL
        doLast {
            // https://docs.gradle.org/current/userguide/working_with_files.html#sec:moving_files_example
            ant.withGroovyBuilder {
                "move"(
                    "file" to "${buildDir}/libs/${project.name}-${project_version}.zip",
                    "tofile" to "${buildDir}/libs/${project.name}-${project_version}.war")
            }
        }
        // mustRunAfter(named("assemble"))
    }

    /*
    // intend: replace manifest after war task: but is is working? (tp)
    named("assemble") {
        dependsOn("replaceZip")
    }
     */

    // needed for publishing war with given manifest
    named("generateMetadataFileFor" + project.name.capitalize() + "Publication") {
        dependsOn("manifestWar")
    }

    // needed for building war with given manifest
    named("build") {
        dependsOn("manifestWar")
    }

    named("publish") {
        dependsOn("build")
    }

}
