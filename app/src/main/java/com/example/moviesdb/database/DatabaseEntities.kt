package com.example.moviesdb.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.moviesdb.model.SearchKeyModel
import java.util.*

/**
 * Created by PATINIK-CONT on 03-Oct-19.
 */
@Entity(indices = [Index(value = ["key"], unique = true)])
data class MovieSearchKey constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val key: String,
    val date: Date
) {
    constructor(key: String, date: Date) : this(null, key, date)
}

fun List<MovieSearchKey>.asDomainModel(): List<SearchKeyModel> {
    return map {
        SearchKeyModel(
            id = it.id,
            key = it.key,
            date = it.date
        )
    }
}