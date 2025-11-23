package com.nicholas.rutherford.potter.head.core

import co.touchlab.kermit.Logger

/**
 * Factory for creating Metro-generated module instances using reflection.
 *
 * This factory provides a generic way to create instances of Metro-generated dependency graph
 * implementations. It handles class loading, error handling, and logging for all Metro modules.
 *
 * Metro generates implementation classes with the pattern: `ModuleName$$$MetroGraph`
 * This factory abstracts away the reflection complexity and provides a simple API.
 *
 * @author Nicholas Rutherford
 */
object MetroModuleFactory {

    /**
     * Kermit Logger for this class.
     */
    val log = Logger.withTag(tag = "MetroModuleFactory")

    /**
     * Creates a Metro-generated module instance using the provided class names.
     *
     * This function attempts to create the module instance using the default class loader.
     * If that fails, it tries a fallback approach using the interface's class loader.
     *
     * Usage example:
     * ```
     * val networkModule = MetroModuleFactory.create<NetworkModule>(
     *     interfaceClassName = Constants.NETWORK_MODULE_CLASS_NAME,
     *     implementationClassName = Constants.NETWORK_MODULE_METRO_GRAPH_CLASS_NAME,
     *     moduleName = "NetworkModule"
     * )
     * ```
     *
     * @param T The type of the module interface to create.
     * @param interfaceClassName The full class name of the module interface (e.g., NetworkModule).
     * @param implementationClassName The full class name of the Metro-generated implementation (e.g., NetworkModule$$$MetroGraph).
     * @param moduleName A human-readable name for the module (used in error messages and logging).
     * @return An instance of the Metro-generated module implementation.
     * @throws IllegalStateException if the module instance cannot be created.
     */
    inline fun <reified T> create(
        interfaceClassName: String,
        implementationClassName: String,
        moduleName: String
    ): T {
        return try {
            createFromDefaultClassLoader<T>(implementationClassName)
        } catch (e: ClassNotFoundException) {
            log.w("Failed to create $moduleName using default class loader, attempting fallback")
            createWithFallbackClassLoader<T>(interfaceClassName, implementationClassName, moduleName, e)
        } catch (e: Exception) {
            val errorMessage = "Failed to create $moduleName instance. Make sure Metro has generated the graph. Error: ${e.message}"
            log.e("$errorMessage --- Exception: ${e.javaClass.simpleName}")
            throw IllegalStateException(errorMessage, e)
        }
    }

    /**
     * Attempts to create a module instance using the default class loader.
     *
     * This method uses `Class.forName()` which uses the default class loader.
     * This is the primary approach and works in most cases.
     *
     * @param T The type of the module interface to create.
     * @param implementationClassName The full class name of the Metro-generated implementation.
     * @return An instance of the Metro-generated module implementation.
     * @throws ClassNotFoundException if the class cannot be found using the default class loader.
     */
    @PublishedApi
    internal inline fun <reified T> createFromDefaultClassLoader(implementationClassName: String): T {
        val graphClass = Class.forName(implementationClassName)
        return graphClass.getDeclaredConstructor().newInstance() as T
    }

    /**
     * Attempts to create a module instance using the interface's class loader as a fallback.
     *
     * This method is used when the default class loader approach fails. It loads the interface
     * class first, then uses that class's class loader to load the implementation class.
     * This is useful when modules are in different class loaders (e.g., in multi-module projects).
     *
     * @param T The type of the module interface to create.
     * @param interfaceClassName The full class name of the module interface.
     * @param implementationClassName The full class name of the Metro-generated implementation.
     * @param moduleName A human-readable name for the module (used in error messages and logging).
     * @param originalException The original [ClassNotFoundException] that triggered this fallback.
     * @return An instance of the Metro-generated module implementation.
     * @throws IllegalStateException if the interface class loader is null or if the instance cannot be created.
     */
    @PublishedApi
    internal inline fun <reified T> createWithFallbackClassLoader(
        interfaceClassName: String,
        implementationClassName: String,
        moduleName: String,
        originalException: ClassNotFoundException
    ): T {
        return try {
            val interfaceClass = Class.forName(interfaceClassName)
            val classLoader = interfaceClass.classLoader
                ?: run {
                    val errorMessage = "$moduleName class loader is null"
                    log.e(errorMessage)
                    throw IllegalStateException(errorMessage)
                }

            val graphClass = classLoader.loadClass(implementationClassName)
            graphClass.getDeclaredConstructor().newInstance() as T
        } catch (e: Exception) {
            val errorMessage = "Failed to create $moduleName instance. " +
                "Make sure Metro has generated the graph and the $moduleName module is on the classpath. " +
                "Original error: ${originalException.message}, Fallback error: ${e.message}"
            log.e("$errorMessage --- Original exception: ${originalException.javaClass.simpleName}, Fallback exception: ${e.javaClass.simpleName}")
            throw IllegalStateException(errorMessage, e)
        }
    }
}

