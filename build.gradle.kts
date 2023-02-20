plugins {
    `java-library`
}

allprojects {
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }

    testing {
        suites {
            getting(JvmTestSuite::class) {
                useJUnitJupiter("5.9.1")
            }
        }
    }
}
