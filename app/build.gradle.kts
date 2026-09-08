plugins {
    id("com.android.application")
}

android {
    namespace="com.example.youtubetvtest"
    compileSdk=35

    defaultConfig {
        applicationId="com.example.youtubetvtest"
        minSdk=23
        targetSdk=28
        versionCode=1
        versionName="1.0"
    }

    compileOptions {
        sourceCompatibility=JavaVersion.VERSION_1_8
        targetCompatibility=JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.exoplayer:exoplayer:2.19.1")
}
