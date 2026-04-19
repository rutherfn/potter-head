package com.nicholas.rutherford.potter.head.base.view.model

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collectLatest
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch


/**
 * A base `ViewModel` class that implements [DefaultLifecycleObserver] to provide logging
 * for lifecycle events and lifecycle-aware Flow collection management.
 *
 * This class provides:
 * - Lifecycle event logging using Kermit for debugging purposes
 * - Automatic Flow collection management that starts on resume and stops on pause
 * - Proper cleanup of all active Flow collections when ViewModel is destroyed
 *
 * Subclasses of this `BaseViewModel` will inherit both lifecycle logging and Flow management behavior.
 *
 * Usage: Extend this class in your own ViewModel classes to gain automatic lifecycle logging
 * and use the `collectFlow` method for lifecycle-aware Flow collection.
 */
abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {

    /**
     * The screen title for this ViewModel's screen.
     * Should be overridden by subclasses to return the appropriate title from [com.nicholas.rutherford.potter.head.core.Constants.ScreenTitles].
     *
     * @return The screen title string
     */
    protected abstract val screenTitle: String

    /**
     * Kermit Logger for this class.
     */
    protected val log: Logger = Logger.withTag(tag = this::class.simpleName ?: "BaseViewModel")

    private val activeFlowCollections = mutableListOf<Job>()
    private val activeJobs = mutableListOf<Job>()
    private var isResumed = false
    private var isStart = false

    /**
     * ViewModel-scoped CoroutineScope that is tied to the ViewModel lifecycle.
     * This scope is automatically cancelled when the ViewModel is cleared.
     * Uses SupervisorJob() to ensure child job failures don't cancel sibling jobs.
     */
    private val viewModelJob = SupervisorJob()
    private val viewModelScope: CoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Default)

    /**
     * Returns the ViewModel's coroutine scope.
     * Subclasses can override this to provide a custom scope for testing purposes.
     * By default, returns a ViewModel-scoped scope that is cancelled in onCleared().
     *
     * @return The CoroutineScope to use for this ViewModel
     */
    protected open fun getScope(): CoroutineScope = viewModelScope

    /**
     * Launches a coroutine in the ViewModel's scope and tracks it for cleanup.
     * All jobs launched through this method will be cancelled when the ViewModel is cleared.
     *
     * @param block The coroutine code to execute
     * @return The Job that was launched
     */
    protected fun launch(block: suspend CoroutineScope.() -> Unit): Job? {
        return getScope().let { scope ->
            val job = scope.launch(block = block)
            activeJobs.add(job)
            job
        }
    }

    /**
     * Optional method for subclasses to specify when flow collection should start.
     * If not overridden, defaults to RESUME (traditional behavior).
     */
    protected open fun getFlowCollectionTrigger(): FlowCollectionTrigger = FlowCollectionTrigger.RESUME

    /**
     * Collects a Flow in a lifecycle-aware manner.
     * The collection will automatically start when the ViewModel is resumed and stop when paused.
     * All collections are properly cleaned up when the ViewModel is destroyed.
     *
     * @param flow The Flow to collect
     * @param onCollect The action to perform for each emitted value
     */
    protected fun <T> collectFlow(
        flow: Flow<T>,
        onCollect: suspend (T) -> Unit
    ) {
        getScope().let { scope ->
            val job = scope.launch {
                flow.collectLatest { value ->
                    if (shouldCollectFlow()) {
                        onCollect(value)
                    }
                }
            }
            activeFlowCollections.add(job)
        }
    }

    /**
     * Collects multiple Flows using combine in a lifecycle-aware manner.
     * The collection will automatically start when the ViewModel is resumed and stop when paused.
     *
     * @param flow1 The first Flow to combine
     * @param flow2 The second Flow to combine
     * @param onCollect The action to perform for each combined emission
     */
    protected fun <T1, T2> collectFlows(
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        onCollect: suspend (T1, T2) -> Unit
    ) {
        getScope().let { scope ->
            val job = scope.launch {
                combine(flow1, flow2) { t1, t2 ->
                    if (shouldCollectFlow()) {
                        onCollect(t1, t2)
                    }
                }.collectLatest {}
            }
            activeFlowCollections.add(job)
        }
    }

    /**
     * Collects four Flows using combine in a lifecycle-aware manner.
     * The collection will automatically start when the ViewModel is resumed and stop when paused.
     *
     * @param flow1 The first Flow to combine
     * @param flow2 The second Flow to combine
     * @param flow3 The third Flow to combine
     * @param flow4 The fourth Flow to combine
     * @param onCollect The action to perform for each combined emission
     */
    protected fun <T1, T2, T3, T4> collectFlows(
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        onCollect: suspend (T1, T2, T3, T4) -> Unit
    ) {
        getScope().let { scope ->
            val job = scope.launch {
                combine(flow1, flow2, flow3, flow4) { t1, t2, t3, t4 ->
                    if (shouldCollectFlow()) {
                        onCollect(t1, t2, t3, t4)
                    }
                }.collectLatest {}
            }
            activeFlowCollections.add(job)
        }
    }

    private fun shouldCollectFlow(): Boolean {
        return when (getFlowCollectionTrigger()) {
            FlowCollectionTrigger.INIT -> true
            FlowCollectionTrigger.START -> isStart
            FlowCollectionTrigger.RESUME -> isResumed
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        log.d { "${this::class.simpleName} → onCreate" }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        isStart = true
        log.d { "${this::class.simpleName} → onStart" }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        isResumed = true
        log.d { "${this::class.simpleName} → onResume" }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        isResumed = false
        log.d { "${this::class.simpleName} → onPause" }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        isStart = false
        log.d { "${this::class.simpleName} → onStop" }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        log.d { "${this::class.simpleName} → onDestroy" }
    }

    override fun onCleared() {
        super.onCleared()
        activeFlowCollections.forEach { activeFlow -> activeFlow.cancel() }
        activeFlowCollections.clear()
        activeJobs.forEach { job -> job.cancel() }
        activeJobs.clear()
        viewModelJob.cancel()
        log.d { "${this::class.simpleName} → onCleared" }
    }
}
