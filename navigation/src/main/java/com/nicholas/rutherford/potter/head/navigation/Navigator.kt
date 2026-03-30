package com.nicholas.rutherford.potter.head.navigation

import kotlinx.coroutines.flow.StateFlow

/**
 * Interface for handling navigation actions in a decoupled manner.
 *
 * This interface provides a way for ViewModels and other components to trigger navigation
 * without directly accessing [androidx.navigationNavController. Navigation actions are
 * emitted via [StateFlow] and consumed by the UI layer (typically MainActivity.
 *
 * The Navigator pattern allows for:
 * - Decoupling navigation logic from UI components
 * - Testing navigation without Compose Navigation dependencies
 * - Centralized navigation handling in the UI layer
 *
 * @property navActions A [StateFlow] that emits [NavigationAction] objects when navigation should occur.
 *                      The UI layer should observe this and perform the actual navigation.
 * @property popRouteActions A [StateFlow] that emits route strings when the back stack should be popped.
 *                           The UI layer should observe this and perform the pop operation.
 *
 * @see NavigatorImpl for the default implementation
 * @see NavigationAction for the action types that can be emitted
 *
 * @author Nicholas Rutherford
 */
interface Navigator {
    val alertActions: StateFlow<AlertAction?>
    val navActions: StateFlow<NavigationAction?>
    val popRouteActions: StateFlow<String?>
    val popOnceRequests: StateFlow<Boolean>
    val progressActions: StateFlow<ProgressAction?>

    fun alert(alertAction: AlertAction?)
    fun navigate(navigationAction: NavigationAction?)
    fun pop(routeAction: String?)

    /**
     * Requests a single pop of the back stack (e.g. to return to the previous screen).
     * Prefer this over [pop] when the previous destination has a parameterized route.
     */
    fun pop()
    fun progress(progressAction: ProgressAction?)
    fun resetNavAction()
    fun resetPopAction()
    fun resetPopOnceRequest()
}