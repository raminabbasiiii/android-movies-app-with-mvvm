package com.raminabbasiiii.paging3.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.raminabbasiiii.paging3.data.db.movie.MovieDao
import com.raminabbasiiii.paging3.data.db.movie.MovieEntity
import com.raminabbasiiii.paging3.getOrAwaitValue
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
//@RunWith(AndroidJUnit4::class) without hilt for testing
@SmallTest
@HiltAndroidTest
class MovieDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        //without hilt for testing
        /*database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()*/
        hiltRule.inject()
        dao = database.movieDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertMovieItem() = runBlockingTest {

        val movieEntityItem = MovieEntity(
            1,"war","url","1995","italy","rating",null,null)
        val movieEntities : List<MovieEntity> = listOf(movieEntityItem)
        dao.insertAllMovies(movieEntities)

        val allMovie = dao.getMovies().getOrAwaitValue()

        assertThat(allMovie).contains(movieEntityItem)
    }

    @Test
    fun deleteMovieItem() = runBlockingTest {
        val movieEntityItem = MovieEntity(
            1,"war","url","1995","italy","rating",null,null)
        val movieEntities : List<MovieEntity> = listOf(movieEntityItem)
        dao.insertAllMovies(movieEntities)
        dao.deleteAllMovies()

        val allMovie = dao.getMovies().getOrAwaitValue()

        assertThat(allMovie).doesNotContain(movieEntityItem)

    }

    @Test
    fun countMovieItemId() = runBlockingTest {
        val movieEntityItem1 = MovieEntity(
            1,"war","url","1995","italy","rating",null,null)
        val movieEntityItem2 = MovieEntity(
            2,"war","url","1995","italy","rating",null,null)
        val movieEntityItem3 = MovieEntity(
            3,"war","url","1995","italy","rating",null,null)
        val movieEntityItem4 = MovieEntity(
            4,"war","url","1995","italy","rating",null,null)
        val movieEntities : List<MovieEntity> = listOf(
            movieEntityItem1,movieEntityItem2,movieEntityItem3,movieEntityItem4)
        dao.insertAllMovies(movieEntities)

        val allId = dao.count()

        assertThat(allId).isEqualTo(4)
    }

}


























