plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.laboratorio7"

    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.laboratorio7"
        minSdk = 26
        targetSdk = 36
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

    buildFeatures {
        compose = true
    }
}

dependencies {

    // Dependencias originales del proyecto
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Navegacion de los laboratorios anteriores
    implementation(libs.androidx.navigation.compose)

    // Para utilizar Serializable
    implementation(libs.kotlinx.serialization.json)

    // Para cargar imagenes de internet con Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Iconos de Material
    implementation("androidx.compose.material:material-icons-extended")

    // Nuevas dependencias del Laboratorio 9

    // Para utilizar ViewModel en Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Para observar StateFlow desde las pantallas
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Para utilizar corrutinas y delay
    implementation(libs.kotlinx.coroutines.android)

    // Dependencias para pruebas
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
