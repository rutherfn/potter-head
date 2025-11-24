package com.nicholas.rutherford.potter.head.saved.state

import androidx.lifecycle.SavedStateHandle

/**
 * Factory for creating [SavedStateHandle] instances.
 *
 * This factory provides a way to create [SavedStateHandle] instances that can be used
 * in ViewModels. Each ViewModel should have its own [SavedStateHandle] instance to properly
 * manage saved state across configuration changes and process death.
 *
 * @author Nicholas Rutherford
 */
object SavedStateHandleFactory {

    /**
     * Creates a new [SavedStateHandle] instance with the provided initial state.
     *
     * This method creates a [SavedStateHandle] that can be used in ViewModels. The initial
     * state map can contain default values or navigation arguments that should be preserved.
     *
     * When used with Compose Navigation, navigation arguments are automatically provided
     * through the SavedStateHandle by the navigation framework.
     *
     * @param initialState Optional map of initial state key-value pairs. If null, creates
     * an empty SavedStateHandle.
     * @return A new [SavedStateHandle] instance.
     */
    fun create(initialState: Map<String, Any?>? = null): SavedStateHandle {
        return SavedStateHandle(initialState ?: emptyMap())
    }

    /**
     * Creates a new empty [SavedStateHandle] instance.
     *
     * This is a convenience method for creating a SavedStateHandle without initial state.
     * Navigation arguments will be automatically populated by Compose Navigation when the
     * ViewModel is created through the navigation system.
     *
     * @return A new empty [SavedStateHandle] instance.
     */
    fun createEmpty(): SavedStateHandle = create(initialState = null)
}

