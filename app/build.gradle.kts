import java.io.FileInputStream
import java.util.Properties

apply {
    from("${rootProject.projectDir}/gradle/release.gradle")
}
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)
    alias(libs.plugins.com.google.devtools.ksp)
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

val androidTargetSdkVersion by extra(33)
val androidMinSdkVersion by extra(26)


android {
    namespace = "cn.xihan.qdds"
    compileSdk = androidTargetSdkVersion
    compileSdkPreview = "UpsideDownCake"

    androidResources.additionalParameters += arrayOf(
        "--allow-reserved-package-id",
        "--package-id",
        "0x64"
    )

    signingConfigs {
        create("xihantest") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
            enableV3Signing = true
            enableV4Signing = true
        }
    }

    defaultConfig {
        minSdk = androidMinSdkVersion
        targetSdk = androidTargetSdkVersion
        versionCode = rootProject.extra["appVersionCode"] as Int
        versionName = rootProject.extra["appVersionName"] as String

        resourceConfigurations.addAll(listOf("zh"))

        buildConfigField("long", "BUILD_TIMESTAMP", "${System.currentTimeMillis()}L")

        signingConfig = signingConfigs.getByName("xihantest")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isJniDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            isPseudoLocalesEnabled = true
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

    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = "1.4.7-dev-k1.9.0-Beta-bb7dc8b44eb"

    packagingOptions.apply {
        resources.excludes += mutableSetOf(
            "META-INF/*******",
            "**/*.txt",
            "**/*.xml",
            "**/*.properties",
            "DebugProbesKt.bin",
            "java-tooling-metadata.json",
            "kotlin-tooling-metadata.json"
        )
        dex.useLegacyPackaging = true
    }

    lint.abortOnError = false
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.kotlin.json)
    implementation(libs.fast.json)
    implementation(libs.landscapist.coil) {
        exclude(group = "io.coil-kt")
    }
    implementation(libs.xxpermissions) {
        exclude(group = "com.android.support")
    }
    implementation(libs.io.coil.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.foundation)
    implementation(libs.runtime)
    implementation(libs.animation)
    implementation(libs.material3)
    implementation(libs.navigation.compose)
    implementation(libs.activity.compose)
    implementation(libs.com.google.accompanist.systemuicontroller)
    implementation(libs.com.google.accompanist.themeadapter.material3)
    implementation(libs.com.google.android.material)

    implementation(libs.yukihook.api)
    ksp(libs.yukihook.ksp)

    compileOnly(libs.xposed.api)

    testImplementation(libs.junit)
}

val service = project.extensions.getByType<JavaToolchainService>()
val customLauncher = service.launcherFor {
    languageVersion.set(JavaLanguageVersion.of("17"))
}
project.tasks.withType<org.jetbrains.kotlin.gradle.tasks.UsesKotlinJavaToolchain>().configureEach {
    kotlinJavaToolchain.toolchain.use(customLauncher)
}

kotlin {
    sourceSets.all {
        languageSettings.apply {
            languageVersion = "2.0"
        }
    }
}





