package com.raminabbasiiii.paging3.datasource.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.raminabbasiiii.paging3.datasource.db.entity.RemoteKey
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class RemoteKeyDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var dao: RemoteKeyDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.remoteKeyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertKey_return_true() = runBlockingTest {
        val keyItem = RemoteKey(1,21,true)
        dao.insertKey(keyItem)

        val allKey = dao.getKeys()

        assertThat(allKey).contains(keyItem)
    }

   @Test
   fun deleteKey_return_true() = runBlockingTest {
       val keyItem = RemoteKey(1,21,true)
       dao.insertKey(keyItem)
       dao.deleteAllKeys()

       val allKey = dao.getKeys()

       assertThat(allKey).doesNotContain(keyItem)
   }
}





















