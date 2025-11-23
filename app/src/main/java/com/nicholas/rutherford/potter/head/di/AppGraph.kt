package com.nicholas.rutherford.potter.head.di

import com.nicholas.rutherford.potter.head.network.di.NetworkModule
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

/**
 * Root dependency graph for the application.
 * Aggregates all feature modules and provides access to their dependencies.
 * 
 * Metro will generate AppGraph$$$MetroGraph that provides NetworkModule.
 * NetworkModule is provided by creating NetworkModule$$$MetroGraph instance.
 */
@DependencyGraph
interface AppGraph {
    val networkModule: NetworkModule
    
    companion object {
        /**
         * Provides the NetworkModule instance using Metro's generated graph.
         * Metro generates NetworkModule$$$MetroGraph which implements NetworkModule.
         */
        @Provides
        fun provideNetworkModule(): NetworkModule {
            val baseName = "com.nicholas.rutherford.potter.head.network.di.NetworkModule"
            val dollarSign = "\$"
            val className = "$baseName$dollarSign$dollarSign$dollarSign" + "MetroGraph"
            return try {
                Class.forName(className)
                    .getDeclaredConstructor()
                    .newInstance() as NetworkModule
            } catch (e: ClassNotFoundException) {
                // Fallback: try using the NetworkModule class loader
                try {
                    val networkModuleClass = Class.forName("com.nicholas.rutherford.potter.head.network.di.NetworkModule")
                    val graphClassName = "$baseName$dollarSign$dollarSign$dollarSign" + "MetroGraph"
                    val classLoader = networkModuleClass.classLoader
                        ?: throw IllegalStateException("NetworkModule class loader is null")
                    val graphClass = classLoader.loadClass(graphClassName)
                    graphClass.getDeclaredConstructor().newInstance() as NetworkModule
                } catch (e2: Exception) {
                    throw IllegalStateException(
                        "Failed to create NetworkModule instance. " +
                        "Make sure Metro has generated the graph and the network module is on the classpath. " +
                        "Original error: ${e.message}, Fallback error: ${e2.message}",
                        e2
                    )
                }
            } catch (e: Exception) {
                throw IllegalStateException(
                    "Failed to create NetworkModule instance. Make sure Metro has generated the graph. Error: ${e.message}",
                    e
                )
            }
        }
    }
}

