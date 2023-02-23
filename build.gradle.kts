buildscript {
    val appVersionName by extra("2.0.4")
    val appVersionCode by extra(204)
    val minSdkVersion by extra(26)
    val targetSdkVersion by extra(33)
    val coreVersion by extra("1.9.0")
    val appcompatVersion by extra("1.7.0-alpha02")
    val materialVersion by extra("1.9.0-alpha01")
    val YuKiHookVersion by extra("1.1.8")
    val accompanistVersion by extra("0.28.0")
    val activityVersion by extra("1.8.0-alpha01")
    val navComposeVersion by extra("2.6.0-alpha05")
    val fastJson2Version by extra("2.0.24") //2.0.21.android
    val kotlinxJsonVersion by extra("1.4.1")
    val coilVersion by extra("2.2.2")
    val landscapistVersion by extra("2.1.3")
    val kotlinCompilerExtensionVersion by extra("1.4.1-dev-k1.8.10-c312d77f4cb")
}
plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") apply false
    kotlin("plugin.serialization") apply false
    //id("org.jetbrains.dokka")
    id("com.google.devtools.ksp") apply false
}