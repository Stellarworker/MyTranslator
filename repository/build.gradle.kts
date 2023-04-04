plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.repository"
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

    // Modules
    implementation(project(Modules.model))
    implementation(project(Modules.utils))

    // Android
    implementation(Android.androidx_core_ktx)
    implementation(Android.androidx_appcompat)

    // Koin
    implementation(Koin.koin)

    // Room
    implementation(Room.room_runtime)
    implementation(Room.room_ktx)
    kapt(Room.room_compiler)

    // Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.converter_gson)

    // Tests
    testImplementation(Tests.junit)
    androidTestImplementation(Tests.ext_junit)
    androidTestImplementation(Tests.espresso)
}