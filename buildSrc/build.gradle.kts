plugins {
    `kotlin-dsl`
    // kotlin("jvm") version "1.4.10"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // implementation("org.jetbrains.kotlin:kotlin-script-runtime")
    // implementation(gradleApi())
    // implementation("org.gradle:gradle-tooling-api")

    // version of used plugins
    api("com.coditory.gradle:manifest-plugin:0.1.14")
}

tasks {
    /*
    withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            allWarningsAsErrors = true
        }
    }
     */
}
