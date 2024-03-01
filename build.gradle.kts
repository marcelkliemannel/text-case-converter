fun properties(key: String) = project.findProperty(key).toString()

plugins {
    `java-library`
    signing
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

allprojects {
    group = properties("group")
    version = properties("version")

    apply(plugin = "java-library")
    apply(plugin = "signing")
    apply(plugin = "maven-publish")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.1")
        testImplementation("org.assertj:assertj-core:2.4.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    }

    tasks {
        val sourcesJar by creating(Jar::class) {
            group = "build"
            archiveClassifier.set("sources")
            from(sourceSets["main"].allSource)
        }

        val testsJar by creating(Jar::class) {
            dependsOn(JavaPlugin.TEST_CLASSES_TASK_NAME)
            group = "build"
            archiveClassifier.set("tests")
            from(sourceSets["test"].output)
        }

        val javadocJar by creating(Jar::class) {
            dependsOn(JavaPlugin.JAVADOC_TASK_NAME)
            group = "build"
            archiveClassifier.set("javadoc")
            from(getByPath(JavaPlugin.JAVADOC_TASK_NAME).outputs)
        }

        artifacts {
            add("archives", sourcesJar)
            add("archives", testsJar)
            add("archives", javadocJar)
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    publishing {
        publications {
            create<MavenPublication>(project.name) {
                from(components["java"])
                setArtifacts(configurations.archives.get().allArtifacts)

                artifactId = project.parent?.let { "${it.name}-${project.name}" } ?: project.name

                pom {
                    // Set name on project base
                    description.set("This Java/Kotlin library provides an intuitive API for converting strings between different text cases. It has a wide range of built-in support for the most common text cases. In addition, the library is designed to be easily extended with new custom text cases, making it highly flexible and adaptable.")
                    url.set("https://github.com/marcelkliemannel/text-case-converter")
                    developers {
                        developer {
                            name.set("Marcel Kliemannel")
                            id.set("marcelkliemannel")
                            email.set("dev@marcelkliemannel.com")
                        }
                    }
                    licenses {
                        license {
                            name.set("The Apache Software License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0")
                        }
                    }
                    issueManagement {
                        system.set("GitHub")
                        url.set("https://github.com/marcelkliemannel/text-case-converter/issues")
                    }
                    scm {
                        connection.set("scm:git:git://github.com:marcelkliemannel/text-case-converter.git")
                        developerConnection.set("scm:git:ssh://github.com:marcelkliemannel/text-case-converter.git")
                        url.set("https://github.com/marcelkliemannel/text-case-converter")
                    }
                }
            }
        }
    }

    /**
     * See https://docs.gradle.org/current/userguide/signing_plugin.html#sec:signatory_credentials
     *
     * The following Gradle properties must be set:
     * - signing.keyId (last eight symbols of the key ID from 'gpg -K')
     * - signing.password
     * - signing.secretKeyRingFile ('gpg --keyring secring.gpg --export-secret-keys > ~/.gnupg/secring.gpg')
     */
    signing {
        sign(publishing.publications[project.name])
    }
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(properties("sonar.username"))
            password.set(properties("sonar.password"))
        }
    }
}

publishing.publications.getByName<MavenPublication>(project.name).pom.name.set("Text Case Converter")