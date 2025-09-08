
plugins {
  id("com.android.application")
  kotlin("android")
}

android {
  namespace = "com.example.aiconsciousness"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.aiconsciousness"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
  }

  buildToolsVersion = "34.0.0"
compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    // coreLibraryDesugaringEnabled = true  // uncomment if you need new Java APIs
  }
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  buildFeatures { compose = true }
  composeOptions { kotlinCompilerExtensionVersion = "1.5.14" }
  kotlinOptions { jvmTarget = "17" }
  packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

dependencies {
  val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
  implementation(composeBom)
  androidTestImplementation(composeBom)

  implementation("androidx.core:core-ktx:1.13.1")
  implementation("androidx.activity:activity-compose:1.9.2")
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-tooling-preview")
  debugImplementation("androidx.compose.ui:ui-tooling")
  implementation("androidx.compose.material3:material3")
  implementation("androidx.navigation:navigation-compose:2.7.7")
  implementation("androidx.datastore:datastore-preferences:1.1.1")
  implementation("androidx.compose.material:material-icons-extended")
}
