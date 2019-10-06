package com.example.moviesdb.database

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by PATINIK-CONT on 03-Oct-19.
 */
class Converters {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toTimeStamp(date: Date?): Long? {
        return date?.time
    }
}