package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a debug toggle in the database.
 *
 * @property toggleKey Unique identifier for the toggle (e.g., "shouldSimulateNoInternetConnection").
 * @property isEnabled Whether the toggle is currently enabled.
 *
 * @author Nicholas Rutherford
 */
@Entity(tableName = "debugToggles")
data class DebugToggleEntity(
    @PrimaryKey
    val toggleKey: String,
    val isEnabled: Boolean
)
