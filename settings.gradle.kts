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

include(":training:contract-ui")
include(":training:presentation")
include(":training:domain")
include(":training:data")
include(":training:contract")
include(":training:di")

include(":day:contract-ui")
include(":day:presentation")
include(":day:domain")
include(":day:data")
include(":day:contract")
include(":day:di")

include(":settings:contract-ui")
include(":settings:presentation")
include(":settings:domain")
include(":settings:data")
include(":settings:contract")
include(":settings:di")

include(":integration:firebase")
include(":utils:common")
include(":utils:compose")
include(":utils:firebase-helper")
