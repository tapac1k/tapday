plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization")
}

android {
    namespace = "com.tapac1k.presentation"
    compileSdk = rootProject.ext["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.ext["minSdk"] as Int
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

hilt()
configureComposeDependencies()

dependencies {
    implementation(project(":main:contract-ui"))
    implementation(project(":auth:contract"))

    implementation(project(":training:contract-ui"))
    implementation(project(":training:contract"))
    implementation(project(":day:contract-ui"))
    implementation(project(":day:contract"))
    implementation(project(":settings:contract-ui"))

    implementation(libs.androidx.material)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}