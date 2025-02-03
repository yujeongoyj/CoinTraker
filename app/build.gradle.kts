plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.project.cointraker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.cointraker"
        minSdk = 24
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.support.annotations)
    implementation(libs.androidx.annotation)
    // implementation(libs.support.v4)
    implementation(libs.androidx.legacy.support.v4)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // timber
    implementation(libs.timber)
    // splash
    implementation(libs.androidx.core.splashscreen)
    // navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    // retrofit (네트워크 요청을 만들고 API 응답처리를 위해)
    implementation (libs.com.squareup.retrofit2.retrofit)
    // JSON 데이터를 GSON 라이브러리를 사용하여 객체로 변환하고 다시 JSON으로 변환하도록
    implementation (libs.converter.gson)
    // Coroutine
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    // DateStore
    implementation(libs.androidx.datastore.preferences)
    // Lottie
    implementation (libs.lottie)

    // room 라이브러리는 sql 작성을 쉽게 할 수 있도록 도와줌
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler.v261)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Coroutine WorkManager
    implementation(libs.androidx.work.runtime.ktx)




}