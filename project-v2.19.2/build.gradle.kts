plugins {
    java
}

val domaVersion = "2.19.2"

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    annotationProcessor("org.seasar.doma:doma:$domaVersion")
    implementation("org.seasar.doma:doma:$domaVersion")
}
