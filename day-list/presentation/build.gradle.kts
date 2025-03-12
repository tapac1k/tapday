plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}




android {
    namespace = "com.tapac1k.day_list.presentation"
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

    buildFeatures {
        compose = true
    }
}

configureComposeDependencies()
hilt()

dependencies {
    implementation(project(":day-list:contract-ui"))
    implementation(project(":day-list:contract"))
    implementation(project(":day-list:domain"))

    implementation(project(":day:contract"))

    implementation(project(":utils:compose"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.ui.tooling)
}