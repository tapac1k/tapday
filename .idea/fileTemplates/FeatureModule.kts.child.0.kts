plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.tapac1k.${FEATURE_NAME_UNDERSCORE}.di"
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
    api(project(":${FEATURE_NAME}:contract-ui"))
    api(project(":${FEATURE_NAME}:contract"))
    api(project(":${FEATURE_NAME}:domain"))
    api(project(":${FEATURE_NAME}:data"))
    api(project(":${FEATURE_NAME}:presentation"))

    implementation(project(":utils:compose"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.ui.tooling)
}