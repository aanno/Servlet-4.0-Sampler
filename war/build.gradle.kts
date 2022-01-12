plugins {
    war
    `maven-publish`
}

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(components["web"])
        }
    }

    repositories {
        maven {
            name = "LocalMavenRepo"
            url = uri("../mvnrepo")
        }
    }
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

    named<War>("war") {
        manifest = project.the<JavaPluginExtension>().manifest {
            attributes (
                "Implementation-Title" to "MyWeb Application",
                "Implementation-Version" to "1.0",
                "Implementation-Vendor-Id" to "com.readlearncode.servlet4.mapping",
                "Created-By" to "Gradle"
            )
        }
    }

    // needed for publishing war with given manifest
    named("generateMetadataFileForWarPublication") {
        dependsOn("war")
    }

    // needed for building war with given manifest
    named("build") {
        dependsOn("war")
    }

}
