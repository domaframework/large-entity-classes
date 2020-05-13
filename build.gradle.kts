plugins {
    base
    id("com.diffplug.eclipse.apt") version "3.22.0" apply false
}

val encoding: String by project

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.diffplug.eclipse.apt")

    val path = "src/main/java/example"
    val entitySize = 200
    val propertySize = 500

    val delete by tasks.registering(Delete::class) {
        delete(path)
    }

    val generate by tasks.registering {
        mustRunAfter(delete)
        doLast {
            val dir = project.file(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            for (i in 1..entitySize) {
                val lines = mutableListOf<String>()
                lines.add("package example;")
                lines.add("")
                lines.add("import org.seasar.doma.Entity;")
                lines.add("")
                lines.add("@Entity")
                lines.add("public class Entity${i} {")
                for (j in 1..propertySize) {
                    lines.add("  public String p${j};")
                }
                lines.add("}")
                val file = dir.resolve("Entity${i}.java")
                file.writeText(lines.joinToString("\n"))
            }
        }
    }

    val clean by tasks.existing {
        dependsOn(delete, generate)
    }

    val compileJava by tasks.existing(JavaCompile::class) {
        options.encoding = encoding
    }

    val compileTestJava by tasks.existing(JavaCompile::class) {
        options.encoding = encoding
        options.compilerArgs = listOf("-proc:none")
    }

    val test by tasks.existing(Test::class) {
        maxHeapSize = "1g"
        useJUnitPlatform()
    }

    dependencies {
        "testImplementation"("org.junit.jupiter:junit-jupiter-api:5.6.2")
        "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:5.6.2")
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    configure<org.gradle.plugins.ide.eclipse.model.EclipseModel> {
        classpath {
            file {
                whenMerged {
                    val classpath = this as org.gradle.plugins.ide.eclipse.model.Classpath
                    classpath.entries.removeAll {
                        when (it) {
                            is org.gradle.plugins.ide.eclipse.model.Output -> it.path == ".apt_generated"
                            else -> false
                        }
                    }
                }
                withXml {
                    val node = asNode()
                    node.appendNode("classpathentry", mapOf("kind" to "src", "output" to "bin/main", "path" to ".apt_generated"))
                }
            }
        }
        jdt {
            javaRuntimeName = "JavaSE-1.8"
        }
    }
}
