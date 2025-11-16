plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val baseUrl: String = (project.findProperty("BASE_URL") as String?) ?: "https://mieli.fi"
val appName: String = (project.findProperty("APP_NAME") as String?) ?: "Mieli (Chrome)"

android {
    namespace = "fi.example.mieli.chrome"
    compileSdk = 36

    defaultConfig {
        applicationId = "fi.example.mieli.chrome"
        minSdk = 34
        targetSdk = 36
        versionCode = 3
        versionName = "1.0.3"
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        resValue("string", "app_name", appName)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    val storeFilePath: String? = System.getenv("RELEASE_STORE_FILE")
    val hasSigning = !storeFilePath.isNullOrBlank()

    signingConfigs {
        if (hasSigning) {
            create("release") {
                storeFile = file(storeFilePath!!)
                storePassword = System.getenv("RELEASE_STORE_PASSWORD")
                keyAlias = System.getenv("RELEASE_KEY_ALIAS")
                keyPassword = System.getenv("RELEASE_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            if (hasSigning) signingConfig = signingConfigs.getByName("release")
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