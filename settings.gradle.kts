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
include(":build-type")
include(":compose:components")
include(":compose:ui-theme")
include(":core")
include(":database")
include(":data-store")
include(":entry-point")
include(":feature:characters")
include(":feature:quizzes")
include(":feature:settings")
include(":feature:spells")
include(":model")
include(":navigation")
include(":network")
include(":scope")
include(":test-utils")
