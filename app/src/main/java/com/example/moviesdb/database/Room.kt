package com.example.moviesdb.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by PATINIK-CONT on 03-Oct-19.
 */

@Dao
interface MovieSearchKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKey(movieSearchKey: MovieSearchKey)

    @Query("Select * from MovieSearchKey ORDER BY date DESC")
    fun getAllSearchKeys(): LiveData<List<MovieSearchKey>>
}

@Database(version = 1, entities = [MovieSearchKey::class])
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieSearchKeyDao: MovieSearchKeyDao
}

private lateinit var INSTANCE: MovieDatabase

fun getDatabase(context: Context): MovieDatabase {
    synchronized(MovieDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java, "Movies"
            ).build()
        }
    }
    return INSTANCE;
}