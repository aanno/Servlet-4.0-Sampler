
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

/*
    jar {

    }
*/
}
