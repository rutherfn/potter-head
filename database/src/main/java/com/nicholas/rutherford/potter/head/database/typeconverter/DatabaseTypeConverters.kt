package com.nicholas.rutherford.potter.head.database.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.entity.AnswerEntity
import com.nicholas.rutherford.potter.head.database.entity.QuestionEntity
import com.nicholas.rutherford.potter.head.database.entity.ResultsInfoEntity
import com.nicholas.rutherford.potter.head.database.entity.SavedAnswerItem
import com.nicholas.rutherford.potter.head.database.entity.SavedQuestionItem
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

    @TypeConverter
    fun fromStringIntMap(value: Map<String, Int>?): String? {
        return if (value == null) {
            null
        } else {
            gson.toJson(value)
        }
    }

    @TypeConverter
    fun toStringIntMap(value: String?): Map<String, Int>? {
        return if (value == null) {
            null
        } else {
            val mapType = object : TypeToken<Map<String, Int>>() {}.type
            gson.fromJson(value, mapType)
        }
    }

    @TypeConverter
    fun fromResultsInfoEntityList(value: List<ResultsInfoEntity>?): String? {
        return if (value == null) {
            null
        } else {
            gson.toJson(value)
        }
    }

    @TypeConverter
    fun toResultsInfoEntityList(value: String?): List<ResultsInfoEntity> {
        if (value.isNullOrBlank()) return emptyList()
        val listType = object : TypeToken<List<ResultsInfoEntity>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

    @TypeConverter
    fun fromQuestionEntityList(value: List<QuestionEntity>?): String? {
        return if (value == null) {
            null
        } else {
            gson.toJson(value)
        }
    }

    @TypeConverter
    fun toQuestionEntityList(value: String?): List<QuestionEntity>? {
        return if (value == null) {
            null
        } else {
            val listType = object : TypeToken<List<QuestionEntity>>() {}.type
            gson.fromJson(value, listType)
        }
    }

    @TypeConverter
    fun fromAnswerEntityList(value: List<AnswerEntity>?): String? {
        return if (value == null) {
            null
        } else {
            gson.toJson(value)
        }
    }

    @TypeConverter
    fun toAnswerEntityList(value: String?): List<AnswerEntity>? {
        return if (value == null) {
            null
        } else {
            val listType = object : TypeToken<List<AnswerEntity>>() {}.type
            gson.fromJson(value, listType)
        }
    }

    @TypeConverter
    fun fromSavedAnswerItemList(value: List<SavedAnswerItem>?): String? {
        return if (value == null) {
            null
        } else {
            gson.toJson(value)
        }
    }

    @TypeConverter
    fun toSavedAnswerItemList(value: String?): List<SavedAnswerItem>? {
        return if (value == null) {
            null
        } else {
            val listType = object : TypeToken<List<SavedAnswerItem>>() {}.type
            gson.fromJson(value, listType)
        }
    }

    @TypeConverter
    fun fromSavedQuestionItemList(value: List<SavedQuestionItem>?): String? {
        return if (value == null) {
            null
        } else {
            gson.toJson(value)
        }
    }

    @TypeConverter
    fun toSavedQuestionItemList(value: String?): List<SavedQuestionItem>? {
        return if (value == null) {
            null
        } else {
            val listType = object : TypeToken<List<SavedQuestionItem>>() {}.type
            gson.fromJson(value, listType)
        }
    }
}

