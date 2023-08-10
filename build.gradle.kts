buildscript {
    val appVersionName by extra("2.1.9")
    val appVersionCode by extra(219)
}
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
}
true // Needed to make the Suppress annotation work for the plugins block