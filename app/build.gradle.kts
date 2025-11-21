plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val baseUrl: String = (project.findProperty("BASE_URL") as String?) ?: "https://mieli.fi"
val appName: String = (project.findProperty("APP_NAME") as String?) ?: "Mieli (Chrome)"

val domainSuffix = baseUrl
    .removePrefix("https://")
    .removePrefix("http://")
    .substringBefore("/")
    .replace(".", "")
    .replace("-", "")
    .lowercase()

val urlHash = baseUrl.hashCode().toLong().let { if (it < 0) -it else it }
val uniqueSuffix = (urlHash % 100000).toString().padStart(5, '0')

val basePackage = "fi.example.mieli.chrome"
val fullPackage = "$basePackage.$domainSuffix$uniqueSuffix"

android {
    namespace = "fi.example.mieli.chrome"
    compileSdk = 36

    defaultConfig {
        applicationId = fullPackage
        minSdk = 34
        targetSdk = 36
        versionCode = 5
        versionName = "1.0.5"
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

    buildFeatures {
        // Required because we declare custom buildConfigField above.
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}

kotlin {
    jvmToolchain(21)
}