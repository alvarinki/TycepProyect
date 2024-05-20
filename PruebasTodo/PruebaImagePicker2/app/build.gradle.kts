plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.pruebaimagepicker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pruebaimagepicker"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Convertidor Gson para Retrofit (opcional, si deseas trabajar con JSON)
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Interceptor de registro OkHttp para Retrofit (opcional, para ver solicitudes y respuestas HTTP)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Biblioteca estándar de Kotlin para JVM (necesaria para proyectos Kotlin)
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}