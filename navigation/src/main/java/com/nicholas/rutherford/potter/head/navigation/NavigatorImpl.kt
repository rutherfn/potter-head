package com.nicholas.rutherford.potter.head.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigatorImpl : Navigator {

    private val _navActions = MutableStateFlow<NavigationAction?>(value = null)
    private val _popRouteActions = MutableStateFlow<String?>(value = null)

    override val navActions: StateFlow<NavigationAction?> = _navActions.asStateFlow()
    override val popRouteActions: StateFlow<String?> = _popRouteActions.asStateFlow()

    override fun navigate(navigationAction: NavigationAction?) = _navActions.update { navigationAction }

    override fun pop(routeAction: String?) = _popRouteActions.update { routeAction }
    
    override fun resetNavAction() = _navActions.update { null }
    
    override fun resetPopAction() = _popRouteActions.update { null }
}