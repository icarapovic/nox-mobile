import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(libs.versions.jdk.get())
    }
}

dependencies {
    testImplementation(libs.junit)
}
