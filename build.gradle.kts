buildscript {
    val appVersionName by extra("2.0.5")
    val appVersionCode by extra(205)
    val minSdkVersion by extra(26)
    val targetSdkVersion by extra(33)
    val coreVersion by extra("1.9.0")
    val appcompatVersion by extra("1.7.0-alpha02")
    val materialVersion by extra("1.9.0-alpha02")
    val yuKiHookVersion by extra("1.1.8")
    val accompanistVersion by extra("0.29.1-alpha")
    val activityVersion by extra("1.8.0-alpha01")
    val navComposeVersion by extra("2.6.0-alpha05")
    val fastJson2Version by extra("2.0.24") //2.0.21.android
    val kotlinxJsonVersion by extra("1.5.0")
    val coilVersion by extra("2.2.2")
    val landscapistVersion by extra("2.1.4")
    val kotlinCompilerExtensionVersion by extra("1.4.2")
}
plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") apply false
    kotlin("plugin.serialization") apply false
    //id("org.jetbrains.dokka")
    id("com.google.devtools.ksp") apply false
}