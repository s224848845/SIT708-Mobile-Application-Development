plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.sit708_40hd_deakin_oneai_app"

    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.sit708_40hd_deakin_oneai_app"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Gemini API key is loaded from local.properties.
        // This avoids hardcoding the key directly inside Java source files.
        val geminiApiKey = project.rootProject.file("local.properties")
            .readLines()
            .find { it.startsWith("GEMINI_API_KEY=") }
            ?.substringAfter("=") ?: ""

        buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")
    }

    buildFeatures {
        buildConfig = true
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
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // CardView is used for modern dashboard-style UI sections.
    implementation("androidx.cardview:cardview:1.0.0")

    // OkHttp is used to call Gemini API in hybrid mode.
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}