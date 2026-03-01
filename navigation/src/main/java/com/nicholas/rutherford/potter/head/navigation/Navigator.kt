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
    /**
     * StateFlow that emits navigation actions to be consumed by the UI layer.
     *
     * When a [NavigationAction] is emitted, the UI layer should observe it and
     * perform the actual navigation using NavController.
     * After navigation is performed, [resetNavAction] should be called to clear the action.
     */
    val navActions: StateFlow<NavigationAction?>

    /**
     * StateFlow that emits route strings when the back stack should be popped.
     *
     * When a route string is emitted, the UI layer should observe it and
     * perform the pop operation using NavController.
     * If `null` is emitted, it means to pop back without a specific route.
     * After the pop is performed, [resetPopAction] should be called to clear the action.
     */
    val popRouteActions: StateFlow<String?>

    /**
     * StateFlow that emits progress actions to be consumed by the UI layer.
     *
     * When a [ProgressAction] is emitted, the UI should observe it and
     * show the progress action until it has been updated.
     */
    val progressActions: StateFlow<ProgressAction?>


    /**
     * Triggers a navigation action to the specified destination.
     *
     * This method updates the [navActions] StateFlow, which will be observed
     * by the UI layer to perform the actual navigation.
     *
     * @param navigationAction The [NavigationAction] containing the destination and navigation options.
     *                         If `null`, no navigation will occur.
     */
    fun navigate(navigationAction: NavigationAction?)

    /**
     * Triggers a pop action on the navigation back stack.
     *
     * This method updates the [popRouteActions] StateFlow, which will be observed
     * by the UI layer to perform the actual pop operation.
     *
     * @param routeAction The route to pop back to. If `null`, pops back without a specific route.
     */
    fun pop(routeAction: String?)

    /**
     * Triggers a progress action.
     *
     * This method updates the [progressActions] StateFlow, which will be observed
     * by the UI layer to show the progress Compose UI.
     *
     * @param progressAction The [ProgressAction] to display. If `null`, hides the progress dialog.
     */
    fun progress(progressAction: ProgressAction?)
    
    /**
     * Resets the navigation action StateFlow after navigation has been handled.
     *
     * This should be called by the UI layer (typically MainActivity) after performing
     * the navigation action to clear the StateFlow and allow new navigation actions to be emitted.
     */
    fun resetNavAction()
    
    /**
     * Resets the pop action StateFlow after pop has been handled.
     *
     * This should be called by the UI layer (typically MainActivity) after performing
     * the pop operation to clear the StateFlow and allow new pop actions to be emitted.
     */
    fun resetPopAction()
}