package com.raminabbasiiii.paging3.datasource.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.raminabbasiiii.paging3.datasource.db.entity.Movie
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

        val movieItem = Movie(1,"war","url","1995","italy")
        val movies : List<Movie> = listOf(movieItem)
        dao.insertAllMovies(movies)

        val allMovie = dao.getMovies().getOrAwaitValue()

        assertThat(allMovie).contains(movieItem)
    }

    @Test
    fun deleteMovieItem() = runBlockingTest {
        val movieItem = Movie(1,"war","url","1995","italy")
        val movies : List<Movie> = listOf(movieItem)
        dao.insertAllMovies(movies)
        dao.deleteAllMovies()

        val allMovie = dao.getMovies().getOrAwaitValue()

        assertThat(allMovie).doesNotContain(movieItem)

    }

    @Test
    fun countMovieItemId() = runBlockingTest {
        val movieItem1 = Movie(1,"war","url","1995","italy")
        val movieItem2 = Movie(2,"war","url","1995","italy")
        val movieItem3 = Movie(3,"war","url","1995","italy")
        val movieItem4 = Movie(4,"war","url","1995","italy")
        val movies : List<Movie> = listOf(movieItem1,movieItem2,movieItem3,movieItem4)
        dao.insertAllMovies(movies)

        val allId = dao.count()

        assertThat(allId).isEqualTo(4)
    }

}


























