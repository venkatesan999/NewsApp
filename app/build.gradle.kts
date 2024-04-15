plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.devtools.ksp)
    kotlin("kapt")
}

android {
    namespace = "com.appsmindstudio.readinnews"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.appsmindstudio.readinnews"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            val apiVersion = "v2"
            buildConfigField("String", "APIKEY", "\"e2ad484ef42848518a38efb9640f2653\"")
            buildConfigField(
                "String",
                "BASEURL",
                "\"https://newsapi.org/${apiVersion}/\""
            )
            buildConfigField("String", "APPLICATION_ID", "\"com.appsmindstudio.readinnews\"")
            buildConfigField("String", "API_VERSION", "\"${apiVersion}\"")
            buildConfigField("boolean", "LOG", "true")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    /*ViewModel Lifecycle*/
    implementation(libs.bundles.lifecle)

    /*API*/
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.moshi.kotlin)
    implementation(libs.okhttp)
    implementation(libs.converter.moshi)
    implementation(libs.converter.gson)

    /*Hilt*/
    implementation(libs.hilt.android)
    implementation(project(":utilities"))
    kapt(libs.hilt.android.compiler)

    /*Coroutines*/
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    /*Navigation*/
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    /*Splash screen*/
    implementation(libs.androidx.core.splashscreen)

    /*Image load*/
    implementation(libs.coil.compose)

    /*Room*/
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation (libs.androidx.runtime.livedata)

    implementation (libs.androidx.datastore.preferences)

    /*Testing dependencies*/
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}