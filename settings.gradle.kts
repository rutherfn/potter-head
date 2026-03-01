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

rootProject.name = "PotterHead"

include(":app")
include(":base:view-model")
include(":compose:ui-theme")
include(":core")
include(":database")
include(":entry-point")
include(":feature:characters")
include(":feature:quizzes")
include(":feature:settings")
include(":model")
include(":navigation")
include(":network")
include(":saved-state")
include(":scope")
include(":test-utils")
include(":compose:components")
