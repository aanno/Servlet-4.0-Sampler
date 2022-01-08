plugins {
    war
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
}


tasks {
    withType<Wrapper> {
        gradleVersion = "7.3.3"
        distributionType = Wrapper.DistributionType.ALL
    }

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
}
