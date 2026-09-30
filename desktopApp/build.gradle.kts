import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    sourceSets {
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(compose.material)
            implementation(project(":shared"))
        }
        jvmTest.dependencies {
            implementation(kotlin("test"))
        }
    }
    jvmToolchain(17)
}

compose.desktop {
    application {
        mainClass = "com.mathbord.ai.desktop.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Exe, TargetFormat.Msi)
            packageName = "MathBordAI"
            packageVersion = providers.gradleProperty("mathbordVersion").get()
            description = "MathBord AI — interactive mathematics learning"
            vendor = "MathBord AI"
            windows {
                console = false
                menuGroup = "MathBord AI"
            }
        }
    }
}
