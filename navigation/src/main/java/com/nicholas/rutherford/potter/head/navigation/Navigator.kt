package com.nicholas.rutherford.potter.head.navigation

import kotlinx.coroutines.flow.StateFlow

interface Navigator {
    val navActions: StateFlow<NavigationAction?>
    val popRouteActions: StateFlow<String?>

    fun navigate(navigationAction: NavigationAction?)
    fun pop(routeAction: String?)
    
    /**
     * Resets the navigation action StateFlow after navigation has been handled.
     * This should be called by MainActivity after performing the navigation.
     */
    fun resetNavAction()
    
    /**
     * Resets the pop action StateFlow after pop has been handled.
     * This should be called by MainActivity after performing the pop.
     */
    fun resetPopAction()
}