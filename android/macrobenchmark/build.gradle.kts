plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.androidx.benchmark)
}

android {
    namespace = "com.mathbord.ai.macrobenchmark"
    compileSdk = 37
    defaultConfig {
        minSdk = 23
        targetSdk = 37
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    targetProjectPath = ":android:app"
}

dependencies {
    androidTestImplementation(libs.androidx.benchmark.macro.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
}