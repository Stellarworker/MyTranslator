plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = Config.applicationId
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.testInstrumentationRunner
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
    buildFeatures {
        viewBinding = Config.viewBinding
    }
}

dependencies {

    // Modules
    implementation(project(Modules.utils))
    implementation(project(Modules.repository))
    implementation(project(Modules.model))

    // Android
    implementation(Android.androidx_core_ktx)
    implementation(Android.androidx_appcompat)
    implementation(Android.android_material)
    implementation(Android.androidx_constraintlayout)

    // Navigation
    implementation(Navigation.navigation_fragment_ktx)
    implementation(Navigation.navigation_ui_ktx)

    // Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.converter_gson)

    // Koin
    implementation(Koin.koin)

    // Coil
    implementation(Coil.coil)

    // Tests
    testImplementation(Tests.junit)
    androidTestImplementation(Tests.ext_junit)
    androidTestImplementation(Tests.espresso)

}