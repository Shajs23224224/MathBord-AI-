plugins {
    alias(libs.plugins.android.test)
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
    implementation(libs.androidx.benchmark.macro.junit4)
    implementation(libs.androidx.test.ext.junit)
}