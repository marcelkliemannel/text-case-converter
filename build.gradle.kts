plugins {
    `java-library`
}

allprojects {
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.1")
        testImplementation("org.assertj:assertj-core:2.4.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    }

    testing {
        suites {
            val test by getting(JvmTestSuite::class) {
                useJUnitJupiter()
            }
        }
    }
}
