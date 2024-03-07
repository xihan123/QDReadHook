import com.android.tools.build.apkzlib.sign.SigningExtension
import com.android.tools.build.apkzlib.sign.SigningOptions
import com.android.tools.build.apkzlib.zfile.ZFiles
import com.android.tools.build.apkzlib.zip.AlignmentRules
import com.android.tools.build.apkzlib.zip.CompressionMethod
import com.android.tools.build.apkzlib.zip.ZFile
import com.android.tools.build.apkzlib.zip.ZFileOptions
import org.jetbrains.dokka.DokkaConfiguration.Visibility
import org.jetbrains.dokka.gradle.DokkaTask
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.net.URL
import java.security.KeyStore
import java.security.cert.X509Certificate
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jgit)
}

val repo = jgit.repo()
val commitCount = (repo?.commitCount("refs/remotes/origin/master") ?: 1) + 23
val latestTag = repo?.latestTag?.removePrefix("v") ?: "3.x.x-SNAPSHOT"

val verCode by extra(commitCount)
val verName by extra(latestTag)
val androidTargetSdkVersion by extra(34)
val androidMinSdkVersion by extra(26)

android {
    namespace = "cn.xihan.qdds"
    compileSdk = androidTargetSdkVersion

    androidResources.additionalParameters += arrayOf(
        "--allow-reserved-package-id", "--package-id", "0x64"
    )

    defaultConfig {
        minSdk = androidMinSdkVersion
        targetSdk = androidTargetSdkVersion
        versionCode = verCode
        versionName = verName

        ndk.abiFilters += listOf("arm64-v8a")

        resourceConfigurations.addAll(listOf("zh"))
    }

    buildTypes {
        release {
            isDebuggable = false
            isJniDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            isPseudoLocalesEnabled = true
            signingConfig = signingConfigs["debug"]
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions.jvmTarget = "17"

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions.kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()

    packagingOptions.apply {
        resources.excludes += mutableSetOf(
            "META-INF/**",
            "**/*.properties",
            "okhttp3/**",
            "schema/**",
            "**.bin",
            "kotlin**"
        )
        dex.useLegacyPackaging = true
    }

    lint.abortOnError = false
}

dependencies {

    implementation(fileTree("dir" to file("libs"), "include" to listOf("*.jar")))

    implementation(libs.core.ktx)
    implementation(libs.fast.json)
    implementation(libs.android.material)
    implementation(libs.compose.coil)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.material3)
    implementation(libs.compose.activity)
    implementation(libs.compose.lottie)
    implementation(libs.compose.navigation)
//    implementation(libs.kotlin.reflect)
    implementation(libs.dexkit)

    implementation(libs.yukihook.api)
    ksp(libs.yukihook.ksp)

    compileOnly(libs.xposed.api)
}
