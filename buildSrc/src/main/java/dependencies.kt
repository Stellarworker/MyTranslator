import org.gradle.api.JavaVersion

object Config {
    const val applicationId = "com.example.mytranslator"
    const val compileSdk = 33
    const val minSdk = 24
    const val targetSdk = 33
    val javaVersion = JavaVersion.VERSION_1_8
    const val versionCode = 1
    const val versionName = "1.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val isMinifyEnabled = false
    const val defaultProguardFilename = "proguard-android-optimize.txt"
    const val proguardRulesFile = "proguard-rules.pro"
    const val consumerProguardFilename = "consumer-rules.pro"
    const val jvmTarget = "1.8"
    const val viewBinding = true
}

object Modules {
    const val app = ":app"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"
}

object Versions {

    // Android
    val androidx_core_ktx = "1.9.0"
    val androidx_appcompat = "1.6.1"
    val android_material = "1.8.0"
    val androidx_constraintlayout = "2.1.4"

    // Navigation
    val androidx_navigation = "2.5.3"

    // Retrofit
    val retrofit = "2.9.0"

    // Room
    val room = "2.5.1"

    // Koin
    val koin = "3.2.0"

    // Coil
    val coil = "2.1.0"

    // Tests
    val junit = "4.13.2"
    val ext_junit = "1.1.5"
    val espresso = "3.5.1"
}

object Android {
    val androidx_core_ktx = "androidx.core:core-ktx:${Versions.androidx_core_ktx}"
    val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.androidx_appcompat}"
    val android_material = "com.google.android.material:material:${Versions.android_material}"
    val androidx_constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraintlayout}"
}

object Navigation {
    val navigation_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.androidx_navigation}"
    val navigation_ui_ktx =
        "androidx.navigation:navigation-ui-ktx:${Versions.androidx_navigation}"
}

object Retrofit {
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}

object Room {
    val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    val room_ktx = "androidx.room:room-ktx:${Versions.room}"
}

object Koin {
    val koin = "io.insert-koin:koin-android:${Versions.koin}"
}

object Coil {
    val coil = "io.coil-kt:coil:${Versions.coil}"
}

object Tests {
    val junit = "junit:junit:${Versions.junit}"
    val ext_junit = "androidx.test.ext:junit:${Versions.ext_junit}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}
