plugins {
    `java-test-convention`
}

dependencies {
    compileOnly(project(":ejb1"))

    // compileOnly(files("$was_dir/lib/j2ee.jar"))
    implementation("log4j:log4j:1.2.17")
    implementation("commons-lang:commons-lang:2.6")
}

tasks {
}
