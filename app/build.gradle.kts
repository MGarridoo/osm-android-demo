plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
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
        buildConfigField("String", "ACCESS_ID", "\"${project.properties["ACCESS_ID"]}\"")
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
    ksp(libs.hilt.android.compiler)

    /** Navigation Component */
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}