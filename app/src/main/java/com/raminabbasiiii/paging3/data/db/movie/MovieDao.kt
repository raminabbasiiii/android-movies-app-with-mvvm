package com.raminabbasiiii.paging3.data.db.movie

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movieEntities: List<MovieEntity>)

    @Query("SELECT * FROM tbl_movie")
    fun getMovies() : LiveData<List<MovieEntity>>

    @Query("SELECT * FROM tbl_movie")
    fun getAllMovie() : PagingSource<Int, MovieEntity>

    @Query("DELETE FROM tbl_movie")
    suspend fun deleteAllMovies()

    @Query("SELECT COUNT(id) from tbl_movie")
    suspend fun count(): Int
}