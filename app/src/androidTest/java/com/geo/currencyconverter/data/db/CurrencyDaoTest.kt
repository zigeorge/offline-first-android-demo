package com.geo.currencyconverter.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.geo.currencyconverter.data.FakeCurrencyDataAndroidTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CurrencyDaoTest {

    @get:Rule
    var instantTaskExecutionRule = InstantTaskExecutorRule()

    lateinit var database: CurrencyDb
    private lateinit var dao: CurrencyDao

    @Before
    fun setUp() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, CurrencyDb::class.java).build()
        dao = database.currencyDao()
        dao.insertAll(FakeCurrencyDataAndroidTest.currencyData)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun verifyInsertAll() = runTest {
        val expectation = FakeCurrencyDataAndroidTest.currencyData.size

        val count = dao.count()
        assertThat(count).isEqualTo(expectation)
    }

    @Test
    fun verifyDeletion() = runTest {
        dao.delete()

        val count = dao.count()
        assertThat(count).isEqualTo(0)
    }

    @Test
    fun verifyGetCurrencyDataAsFlow() = runTest {
        val currencyData = CurrencyData(
            name = "GBP",
            value = 0.810852,
            base = 1.233270683
        )
        dao.delete()
        dao.insertAll(listOf(currencyData))
        assertThat(dao.getCurrencyDataAsFlow().first().size).isEqualTo(1)
    }

}