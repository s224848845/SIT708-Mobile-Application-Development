plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.task81c_llmchatbot"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.task81c_llmchatbot"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Room is used to store chat history locally using SQLite.
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // OkHttp is used to send HTTP requests to the LLM backend/API.
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}
