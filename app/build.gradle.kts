import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "tj.rsdevteam.inmuslim"
    compileSdk = libs.versions.androidSdk.get().toInt()

    defaultConfig {
        applicationId = "tj.rsdevteam.inmuslim"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidSdk.get().toInt()
        versionCode = 4
        versionName = "1.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file("$rootDir/config/debug.keystore")
            storePassword = project.properties["DEBUG_STORE_PASSWORD"] as String
            keyAlias = project.properties["DEBUG_KEY_ALIAS"] as String
            keyPassword = project.properties["DEBUG_KEY_PASSWORD"] as String
        }
        create("release") {
            val localPropertiesFile = File(rootProject.rootDir, "local.properties")
            if (localPropertiesFile.exists()) {
                val properties = Properties().apply {
                    load(FileInputStream(localPropertiesFile))
                }
                storeFile = file("$rootDir/config/release.keystore")
                storePassword = properties.getProperty("RELEASE_STORE_PASSWORD", "")
                keyAlias = properties.getProperty("RELEASE_KEY_ALIAS", "")
                keyPassword = properties.getProperty("RELEASE_KEY_PASSWORD", "")
            }
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            buildConfigField("String", "BASE_URL", "\"https://rsdevteam.ru/inmuslim/api/\"")
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".beta"
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("String", "BASE_URL", "\"https://rsdevteam.ru/inmuslim/api/\"")
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/okhttp3/internal/publicsuffix/NOTICE"
            excludes += "/kotlin/**"
            excludes += "/META-INF/androidx.*.version"
            excludes += "/META-INF/com.google.*.version"
            excludes += "/META-INF/kotlinx_*.version"
            excludes += "kotlin-tooling-metadata.json"
            excludes += "DebugProbesKt.bin"
            excludes += "/META-INF/com/android/build/gradle/app-**.properties"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.junit4)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.dagger.hilt.navigation)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.retrofit.adapters.result)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.crashlytics.ktx)
}
