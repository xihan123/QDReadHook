pluginManagement {
    repositories {
//        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("com.android.application").version(extra["agp.version"] as String)
        id("com.android.library").version(extra["agp.version"] as String)
        kotlin("android").version(extra["kotlin.version"] as String)
        kotlin("plugin.serialization").version(extra["kotlin.version"] as String)
        id("org.jetbrains.dokka").version(extra["kotlin.version"] as String)
        id("com.google.devtools.ksp").version("${extra["kotlin.version"] as String}-1.0.9")
    }

}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
//        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://api.xposed.info/")
        maven("https://s01.oss.sonatype.org/content/repositories/releases")
        maven("https://androidx.dev/storage/compose-compiler/repository/")
    }
}
rootProject.name = "QDReadHook"
include(":app")
