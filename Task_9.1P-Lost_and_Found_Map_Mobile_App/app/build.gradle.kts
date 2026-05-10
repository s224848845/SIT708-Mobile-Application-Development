plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.task71lostandfoundapp"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.task71lostandfoundapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Reads the Google Maps API key from local.properties
        manifestPlaceholders["MAPS_API_KEY"] = project.findProperty("MAPS_API_KEY") ?: ""
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

    // Google Maps SDK displays saved lost/found items on a map.
    implementation("com.google.android.gms:play-services-maps:19.0.0")

    // Fused Location Provider is used for selecting the user's current location.
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // Places SDK is used for location autocomplete when creating an advert.
    implementation("com.google.android.libraries.places:places:4.0.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}