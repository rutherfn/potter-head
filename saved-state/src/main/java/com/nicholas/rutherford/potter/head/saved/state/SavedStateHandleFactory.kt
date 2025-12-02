package com.nicholas.rutherford.potter.head.saved.state

import androidx.lifecycle.SavedStateHandle

/**
 * Factory for creating [SavedStateHandle] instances.
 *
 * This factory provides a way to create [SavedStateHandle] instances that can be used in ViewModels.
 *
 * @author Nicholas Rutherford
 */
object SavedStateHandleFactory {

    /**
     * Creates a new [SavedStateHandle] instance with the provided initial state.
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
     * @return A new empty [SavedStateHandle] instance.
     */
    fun createEmpty(): SavedStateHandle = create(initialState = null)
}

