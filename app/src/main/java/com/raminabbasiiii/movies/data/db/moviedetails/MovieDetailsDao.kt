package com.raminabbasiiii.movies.data.db.moviedetails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetailsEntity: MovieDetailsEntity)

    @Query("SELECT * FROM tbl_movie_details WHERE id = :movieId ")
    suspend fun getMovieDetails(movieId : Int) : MovieDetailsEntity

    @Query("SELECT EXISTS (SELECT 1 FROM tbl_movie_details WHERE id = :movieId)")
    suspend fun isExist(movieId: Int): Boolean
}