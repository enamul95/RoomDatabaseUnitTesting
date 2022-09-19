package com.era.app.viewmodel

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.era.app.room.database.SpendsDatabase
import com.era.app.room.datasource.SpendsTrackerDataSource
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpendViewModelTest :TestCase(){

    private lateinit var spendsDatabase: SpendsDatabase
    private lateinit var viewModel: SpendViewModel

    //https://www.youtube.com/watch?v=mNUBSRmj0Uk
    @Before
    public override fun setUp() {
       // super.setUp()

        val db  = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),SpendsDatabase::class.java)
            .allowMainThreadQueries().build()
        val database = SpendsTrackerDataSource(db.getSpendDao())
        viewModel = SpendViewModel(database)
    }

    @After
    @Throws(IndexOutOfBoundsException::class)
    fun closeDb() {
        spendsDatabase.close()
    }

    @Test
    fun testAddingSpend(){
        viewModel.addSpend(100, "Eggs")
        viewModel.getLast20Spends()
    }
}