plugins {
    java
}

val domaVersion = "2.34.0"

val compileJava by tasks.existing(JavaCompile::class) {
    options.compilerArgs = listOf("-Adoma.criteria.enabled=false")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    annotationProcessor("org.seasar.doma:doma-processor:$domaVersion")
    implementation("org.seasar.doma:doma-core:$domaVersion")
}
