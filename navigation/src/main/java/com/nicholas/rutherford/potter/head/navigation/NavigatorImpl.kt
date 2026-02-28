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

    private val navActionsMutableStateFlow = MutableStateFlow<NavigationAction?>(value = null)

    private val popRouteActionsMutableStateFlow = MutableStateFlow<String?>(value = null)

    private val progressActionsMutableStateFlow = MutableStateFlow<ProgressAction?>(value = null)

    override val navActions: StateFlow<NavigationAction?> = navActionsMutableStateFlow.asStateFlow()
    override val popRouteActions: StateFlow<String?> = popRouteActionsMutableStateFlow.asStateFlow()

    override val progressActions: StateFlow<ProgressAction?> = progressActionsMutableStateFlow.asStateFlow()

    override fun navigate(navigationAction: NavigationAction?) = navActionsMutableStateFlow.update { navigationAction }

    override fun pop(routeAction: String?) = popRouteActionsMutableStateFlow.update { routeAction }

    override fun progress(progressAction: ProgressAction?) = progressActionsMutableStateFlow.update { progressAction }
    
    override fun resetNavAction() = navActionsMutableStateFlow.update { null }
    
    override fun resetPopAction() = popRouteActionsMutableStateFlow.update { null }
}