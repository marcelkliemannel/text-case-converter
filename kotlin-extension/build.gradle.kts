plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    id("org.jetbrains.dokka") version "1.7.10"
}

dependencies {
    implementation(rootProject)
}

tasks {
    val dokkaJar by creating(Jar::class) {
        dependsOn("dokkaHtml")
        group = "build"
        archiveClassifier.set("javadoc")
        from(getByPath("dokkaHtml").outputs)
    }

    artifacts {
        add("archives", dokkaJar)
    }
}

publishing.publications.getByName<MavenPublication>(project.name).pom.name.set("Text Case Converter - Kotlin Extension")