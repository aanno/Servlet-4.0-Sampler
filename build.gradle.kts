plugins {
    war
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
}


tasks {
    withType<Wrapper> {
        gradleVersion = "7.3.3"
        distributionType = Wrapper.DistributionType.ALL
    }

    // not expected to work
    /*
    jar {
        manifest {
            attributes(
                "Implementation-Title" to "MyWeb Application",
                "Implementation-Version" to "1.0",
                "Implementation-Vendor-Id" to "com.readlearncode.servlet4.mapping",
                "Created-By" to "Gradle"
            )
        }
    }
     */

    /*
    Results in error:
Script compilation errors:

  Line 37:         manifest {
                   ^ Unresolved reference: manifest

  Line 38:             attributes(
                       ^ Unresolved reference. None of the following candidates is applicable because of receiver type mismatch:
                           public fun <T : Configuration> NamedDomainObjectProvider<TypeVariable(T)>.attributes(action: AttributeContainer.() -> Unit): Configuration defined in org.gradle.kotlin.dsl
                           public inline fun Manifest.attributes(vararg attributes: Pair<String, Any?>): Manifest defined in org.gradle.kotlin.dsl
                           public inline fun Manifest.attributes(sectionName: String, vararg attributes: Pair<String, Any?>): Manifest defined in org.gradle.kotlin.dsl
     */
    /*
    war {
        manifest {
            attributes(
                "Implementation-Title" to "MyWeb Application",
                "Implementation-Version" to "1.0",
                "Implementation-Vendor-Id" to "com.readlearncode.servlet4.mapping",
                "Created-By" to "Gradle"
            )
        }
    }
     */

    val sharedManifest = the<JavaPluginExtension>().manifest {
        attributes (
            "Implementation-Title" to "MyWeb Application",
            "Implementation-Version" to "1.0",
            "Implementation-Vendor-Id" to "com.readlearncode.servlet4.mapping",
            "Created-By" to "Gradle"
        )
    }

    register<Jar>("manifestJar") {
        manifest = project.the<JavaPluginExtension>().manifest {
            from(sharedManifest)
        }
    }

    jar {
        manifest = project.the<JavaPluginExtension>().manifest {
            from(sharedManifest)
        }
    }

    /*
     * WORKING!
     */
    register<War>("manifestWar") {
        manifest = project.the<JavaPluginExtension>().manifest {
            from(sharedManifest)
        }
    }

    /*
    Results in error:
Script compilation error:

  Line 87:         setManifest(project.the<JavaPluginExtension>().manifest {
                   ^ Unresolved reference: setManifest
     */
    /*
    war {
        manifest = project.the<JavaPluginExtension>().manifest {
            from(sharedManifest)
        }
    }
     */
}
