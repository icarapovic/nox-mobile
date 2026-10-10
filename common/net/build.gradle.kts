plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.koin.compiler)
}

android {
    namespace = "dev.nox.net"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
    }
}

dependencies {
    implementation(project(":common:logging"))

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    api(libs.retrofit)
    api(libs.retrofit.converter.kotlinx.serialization)
    api(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging.interceptor)

    testImplementation(libs.junit)
}