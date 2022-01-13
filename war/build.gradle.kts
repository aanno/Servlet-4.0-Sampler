plugins {
    `war-test-convention`
}

dependencies {
    compileOnly(project(":ejb2"))

    compileOnly("jakarta.servlet:jakarta.servlet-api:5.0.0")
    implementation("commons-codec:commons-codec:1.15")
}

tasks {

    // works only with gradle 7
    /*
    named<War>("war") {
        manifest = project.the<JavaPluginExtension>().manifest {
            attributes(
                "Class-Path" to "ejb1.jar ejb2.jar",
                "Comment2" to  "manifest by gradle-war-manifest-attributes"
            )
        }
    }
     */

}
