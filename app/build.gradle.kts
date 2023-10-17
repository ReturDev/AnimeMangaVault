plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.allopen")
    kotlin ("kapt")
}

allOpen{
    annotation("androidx.annotation.OpenForTesting")
}

android {
    namespace = "github.returdev.animemangavault"
    compileSdk = 33

    defaultConfig {
        applicationId = "github.returdev.animemangavault"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "github.returdev.animemangavault.di.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_API_URL", "\"https://api.jikan.moe/v4/\"")
        }
        getByName("debug"){
            isDebuggable = true
            buildConfigField("String", "BASE_API_URL", "\"https://api.jikan.moe/v4/\"")
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas".toString())
        }
    }
}

dependencies {

    val hiltVersion = "2.48"
    val retrofitVersion = "2.9.0"
    val okhttp3Version = "4.11.0"
    val mockitoVersion = "3.12.4"
    val roomVersion = "2.5.0"
    val pagingVersion = "3.2.1"
    val navVersion = "2.6.0"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-beta01")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    //Navigation
    implementation("androidx.navigation:navigation-compose:$navVersion")

    //Constraint
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:$hiltVersion")

    //HiltNavigation
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    //Logging Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3Version")

    //Mockito
    implementation("org.mockito:mockito-core:$mockitoVersion")
    implementation("org.mockito:mockito-inline:$mockitoVersion")
    androidTestImplementation ("org.mockito:mockito-core:$mockitoVersion")
    androidTestImplementation ("org.mockito:mockito-android:$mockitoVersion")

    //Room
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt ("androidx.room:room-compiler:$roomVersion")
    implementation ("androidx.room:room-paging:$roomVersion")

    //Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Paging
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    implementation("androidx.paging:paging-compose:$pagingVersion")

}

kapt {
    correctErrorTypes = true
}