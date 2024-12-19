import java.io.FileInputStream
import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.serialization)
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.osmdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.osmdemo"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", "\"${localProperties["API_KEY"]}\"")
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

    flavorDimensions += "version"

    productFlavors {
        create("hafas") {
            dimension = "version"
            applicationIdSuffix = ".hafas"
            versionNameSuffix = "-hafas"
            buildConfigField("String", "BASE_URL", "\"https://moovia.demo.hafas.cloud/restproxy/\"")
        }

        create("dev") {
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "BASE_URL", "\"http://52.20.240.37:5005/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation ("com.google.android.material:material:1.9.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    /** OSM Android*/
    implementation (libs.osmdroid.android)
    implementation (libs.osmdroid.wms)
    implementation("com.github.MKergall:osmbonuspack:6.9.0")

    /** Retrofit */
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    /** OK HTTP */
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation (libs.okhttp.urlconnection)

    /** Dagger Hilt */
    implementation(libs.hilt.android)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.swiperefreshlayout)
    ksp(libs.hilt.android.compiler)

    /** Navigation Component */
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}