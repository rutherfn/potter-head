package com.nicholas.rutherford.potter.head.base.view.model

import com.nicholas.rutherford.potter.head.navigation.Navigator

/**
 * Interface for providing Navigator.
 * Allows entry-point module to access Navigator without compile-time dependency on app module.
 *
 * @author Nicholas Rutherford
 */
interface NavigatorProvider {
    fun getNavigator(): Navigator
}
