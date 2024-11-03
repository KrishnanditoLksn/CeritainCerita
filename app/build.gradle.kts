plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "app.ditodev.ceritain"
    compileSdk = 34

    defaultConfig {
        applicationId = "app.ditodev.ceritain"
        minSdk = 28
        //noinspection OldTargetApi
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //RETROFIT ,OKHTTP , LOGGININTERCEPTOR
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //LIFECYCLE KTX
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //COROUTINE
    implementation(libs.kotlinx.coroutines.android)

    //PREFERENCES DATASTORE
    implementation(libs.androidx.datastore.preferences)

    //GLIDE
    implementation(libs.glide)

    //MOCKITO
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)

    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.mockito.kotlin)// Pastikan versinya sesuai dengan versi terbaru yang kompatibel
}