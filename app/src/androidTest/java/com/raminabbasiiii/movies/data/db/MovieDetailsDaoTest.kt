package com.raminabbasiiii.movies.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.raminabbasiiii.movies.data.db.moviedetails.MovieDetailsDao
import com.raminabbasiiii.movies.data.db.moviedetails.MovieDetailsEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class MovieDetailsDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var dao: MovieDetailsDao

    @Before
    fun setup(){
        hiltRule.inject()
        dao = database.movieDetailsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertMovieDetails() = runBlockingTest {
        val movieDetailsItem = MovieDetailsEntity(
            1,"war","url","1995","italy","rating","rated","released"
        ,"runtime","director","writer","actors","plot","awards"
        ,"votes",null,null)
        dao.insertMovieDetails(movieDetailsItem)

        val movieDetails = dao.getMovieDetails(1)
        assertThat(movieDetails).isEqualTo(movieDetailsItem)
    }
}



























