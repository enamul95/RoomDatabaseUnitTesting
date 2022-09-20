package com.era.app.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.era.app.room.database.SpendsDatabase
import com.era.app.room.datasource.SpendsTrackerDataSource
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpendViewModelTest : TestCase() {

    private lateinit var spendsDatabase: SpendsDatabase
    private lateinit var viewModel: SpendViewModel

    //InstantTaskExecutorRule setup all task synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        spendsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SpendsDatabase::class.java
        )
            .allowMainThreadQueries().build()
        val database = SpendsTrackerDataSource(spendsDatabase.getSpendDao())
        viewModel = SpendViewModel(database)
    }


    @Test
    fun testAddingSpend() {
        //add data
        viewModel.addSpend(100, "Eggs")
        viewModel.getLast20Spends()
        val result = viewModel.last20SpendsLiveData.getOrAwaitValue().find {
            Log.e("it--", it.toString())
            it.amount == 100 && it.description == "Eggs"

        }
        Log.e("result--", result.toString())
        assertThat(result != null).isTrue()
    }

    @After
    @Throws(IndexOutOfBoundsException::class)
    fun closeDb() {
        //close databse
        spendsDatabase.close()
    }
}