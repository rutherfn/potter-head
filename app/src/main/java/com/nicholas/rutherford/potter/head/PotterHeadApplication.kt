package com.nicholas.rutherford.potter.head

import android.app.Application
import com.nicholas.rutherford.potter.head.base.view.model.ViewModelFactoryProvider
import com.nicholas.rutherford.potter.head.di.AppGraph
import com.nicholas.rutherford.potter.head.di.ViewModelFactory

class PotterHeadApplication :
    Application(),
    ViewModelFactoryProvider {
    /**
     * Metro generates AppGraph$$$MetroGraph as the implementation.
     * This provides access to all dependency graphs in the application.
     */
    val appGraph: AppGraph by lazy {
        try {
            val baseName = "com.nicholas.rutherford.potter.head.di.AppGraph"
            val dollarSign = "\$"
            val className = "$baseName$dollarSign$dollarSign$dollarSign" + "MetroGraph"
            Class
                .forName(className)
                .getDeclaredConstructor()
                .newInstance() as AppGraph
        } catch (e: Exception) {
            throw IllegalStateException(
                "Failed to create AppGraph instance. Make sure Metro has generated the graph.",
                e
            )
        }
    }

    /**
     * ViewModelFactory instance using AppGraph.
     * This is the single source of truth for ViewModel creation.
     */
    val viewModelFactory: ViewModelFactory by lazy {
        ViewModelFactory(appGraph)
    }

    override fun getViewModelFactory(): androidx.lifecycle.ViewModelProvider.Factory = viewModelFactory

    override fun onCreate() {
        super.onCreate()
        // Graph and factory are lazily initialized on first access
    }

    companion object {
        @JvmStatic
        fun from(context: android.content.Context): PotterHeadApplication = context.applicationContext as PotterHeadApplication
    }
}
