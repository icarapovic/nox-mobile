plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.koin.compiler)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.chapz.data"
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
    implementation(project(":news:domain"))
    implementation(project(":common:net"))

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)

    testImplementation(libs.junit)
}