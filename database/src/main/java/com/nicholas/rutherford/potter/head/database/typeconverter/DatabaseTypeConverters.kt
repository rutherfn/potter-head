package com.nicholas.rutherford.potter.head.database.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.entity.WandEntity

/**
 * Type converters for Room database.
 * Handles conversion between complex types and database-compatible types.
 *
 * @author Nicholas Rutherford
 */
object DatabaseTypeConverters {
    /**
     * Gson instance for JSON parsing.
     */
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return if (value == null) {
            null
        } else {
            gson.toJson(value)
        }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return if (value == null) {
            null
        } else {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(value, listType)
        }
    }

    @TypeConverter
    fun fromWandEntity(value: WandEntity?): String? {
        return if (value == null) {
            null
        } else {
            gson.toJson(value)
        }
    }

    @TypeConverter
    fun toWandEntity(value: String?): WandEntity? {
        return if (value == null) {
            null
        } else {
            gson.fromJson(value, WandEntity::class.java)
        }
    }

    @TypeConverter
    fun fromCharacterFilterType(value: CharacterFilterType?): String? {
        return value?.name
    }

    @TypeConverter
    fun toCharacterFilterType(value: String?): CharacterFilterType? {
        return if (value == null) {
            null
        } else {
            CharacterFilterType.valueOf(value)
        }
    }
}

