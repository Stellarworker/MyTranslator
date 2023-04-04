plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.utils"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = Config.testInstrumentationRunner
        consumerProguardFiles(Config.consumerProguardFilename)
    }

    buildTypes {
        release {
            isMinifyEnabled = Config.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile(Config.defaultProguardFilename),
                Config.proguardRulesFile
            )
        }
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
}

dependencies {

    // Android
    implementation(Android.androidx_core_ktx)
    implementation(Android.androidx_appcompat)
    implementation(Android.android_material)

    // Tests
    testImplementation(Tests.junit)
    androidTestImplementation(Tests.ext_junit)
    androidTestImplementation(Tests.espresso)
}