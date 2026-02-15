package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("wands")
data class WandEntity(
    @PrimaryKey
    val core: String,
    val wood: String?,
    val length: Double
)