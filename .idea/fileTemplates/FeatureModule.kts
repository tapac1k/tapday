plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}


#set($FEATURE_NAME_UNDERSCORE_= $FEATURE_NAME_UNDERSCORE)
#set($CONTRACT_INITIAL_CLASS_ = $CONTRACT_INITIAL_CLASS)
#set($CONTRACT_UI_INITIAL_CLASS_ = $CONTRACT_UI_INITIAL_CLASS)
#set($DOMAIN_INITIAL_CLASS_ = $DOMAIN_INITIAL_CLASS)
#set($DATA_INITIAL_CLASS_ = $DATA_INITIAL_CLASS)
#set($PRESENTATION_INITIAL_CLASS_ = $PRESENTATION_INITIAL_CLASS)


android {
    namespace = "com.tapac1k.${FEATURE_NAME_UNDERSCORE}.presentation"
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
    implementation(project(":${FEATURE_NAME}:contract-ui"))
    implementation(project(":${FEATURE_NAME}:contract"))
    implementation(project(":${FEATURE_NAME}:domain"))

    implementation(project(":integration:firebase"))
    implementation(project(":utils:compose"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.ui.tooling)
}