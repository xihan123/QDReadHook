pluginManagement {
    repositories {
//        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
//        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        google()
        mavenCentral()
        maven("https://jitpack.io"){
            content {
                includeGroupByRegex("com\\.github.*")
            }
        }
        maven("https://api.xposed.info/"){
            mavenContent {
                includeGroup("de.robv.android.xposed")
            }
        }
        maven("https://androidx.dev/storage/compose-compiler/repository/")
    }
}

rootProject.name = "QDReadHook"
include(":app")
