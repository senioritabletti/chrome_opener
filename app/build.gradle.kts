plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "fi.example.mieli.chrome"
    compileSdk = 36

    defaultConfig {
        applicationId = "fi.example.mieli.chrome"
        minSdk = 34
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    signingConfigs {
        create("release") {
            val sf = System.getenv("RELEASE_STORE_FILE") ?: ""
            if (sf.isNotBlank()) {
                storeFile = file(sf)
                storePassword = System.getenv("RELEASE_STORE_PASSWORD")
                keyAlias = System.getenv("RELEASE_KEY_ALIAS")
                keyPassword = System.getenv("RELEASE_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.findByName("release")
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}

kotlin {
    jvmToolchain(21)
}