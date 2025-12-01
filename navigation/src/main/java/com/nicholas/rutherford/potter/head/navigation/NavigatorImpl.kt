package com.nicholas.rutherford.potter.head.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Default implementation of [Navigator] using [StateFlow] for navigation actions.
 *
 * This class provides a concrete implementation of the [Navigator] interface that uses
 * [MutableStateFlow] internally to manage navigation actions. Navigation actions are
 * emitted via StateFlow and consumed by the UI layer (typically MainActivity).
 *
 * The implementation maintains two StateFlows:
 * - [navActions]: Emits [NavigationAction] objects for forward navigation
 * - [popRouteActions]: Emits route strings for back stack pop operations
 *
 * Both StateFlows start with `null` values and are updated when navigation actions occur.
 * The UI layer should observe these StateFlows and reset them after handling the navigation.
 *
 * @see Navigator for the interface definition
 *
 * @author Nicholas Rutherford
 */
class NavigatorImpl : Navigator {

    /**
     * Internal mutable StateFlow for navigation actions.
     * Exposed as read-only [StateFlow] via [navActions].
     */
    private val _navActions = MutableStateFlow<NavigationAction?>(value = null)

    /**
     * Internal mutable StateFlow for pop route actions.
     * Exposed as read-only [StateFlow] via [popRouteActions].
     */
    private val _popRouteActions = MutableStateFlow<String?>(value = null)

    override val navActions: StateFlow<NavigationAction?> = _navActions.asStateFlow()
    override val popRouteActions: StateFlow<String?> = _popRouteActions.asStateFlow()

    override fun navigate(navigationAction: NavigationAction?) = _navActions.update { navigationAction }

    override fun pop(routeAction: String?) = _popRouteActions.update { routeAction }
    
    override fun resetNavAction() = _navActions.update { null }
    
    override fun resetPopAction() = _popRouteActions.update { null }
}