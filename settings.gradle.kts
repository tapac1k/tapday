pluginManagement {
    repositories {
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
        google()
        mavenCentral()
    }
}

rootProject.name = "TapMyDay"
include(":app")

include(":auth:data")
include(":auth:domain")
include(":auth:contract")
include(":auth:contract-ui")
include(":auth:presentation")
include(":auth:di")

include(":main:contract-ui")
include(":main:presentation")
include(":main:di")

include(":day-list:contract-ui")
include(":day-list:presentation")
include(":day-list:domain")
include(":day-list:data")
include(":day-list:contract")
include(":day-list:di")

include(":settings:contract-ui")
include(":settings:presentation")
include(":settings:domain")
include(":settings:data")
include(":settings:contract")
include(":settings:di")

include(":integration:firebase")
include(":utils:common")
include(":utils:compose")
