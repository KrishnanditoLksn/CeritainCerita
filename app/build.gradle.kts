plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
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
        buildConfigField("String", "API_KEY", "${project.findProperty("API_KEY")}")
        buildConfigField("String","WEB_CLIENT_ID" , "${project.findProperty("WEB_CLIENT_ID")}")
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
        viewBinding = true
        buildConfig = true
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
    implementation(libs.play.services.maps)
//    implementation(libs.firebase.auth)
//    implementation(libs.play.services.auth)
//    implementation(libs.androidx.credentials)
//    implementation(libs.androidx.play.services.auth)
//    implementation(libs.play.services.auth.v2060)


    //FIREBASE COK
    implementation(platform(libs.firebase.bom))
//    implementation(libs.play.services.auth)
//    implementation(libs.androidx.credentials)
//    implementation(libs.googleid)


    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-auth")
    implementation(libs.play.services.auth)
    implementation(libs.googleid)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.kotlinx.coroutines.test) // Sesuaikan dengan versi yang Anda butuhkan
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.coroutines.test.v132)
    testImplementation(libs.mockk) // Add MockK dependency

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

    //PAGING
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.runtime.ktx)

    //TEST DISPATCHER

    //ROOM
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    // If this project only uses Java source, use the Java annotationProcessor
    // No additional plugins are necessary
    annotationProcessor(libs.androidx.room.compiler)

}