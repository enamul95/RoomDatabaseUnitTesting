package com.era.app.room.database


import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.era.app.room.dao.SpendDao
import com.era.app.room.entity.Spend
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class SpendsDatabaseTest: TestCase(){

    private lateinit var spendsDao: SpendDao
    private lateinit var db: SpendsDatabase

    @Before
    public override fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SpendsDatabase::class.java
        ).build()
        spendsDao = db.getSpendDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * we first insert a spend to the database, and then we are getting the last 20 spends.
     * If our code is correct and working fine, the 20 spends fetched should contain the spend that we just inserted.
     * And we are asserting the same in the last line.
     * */
    @Test
    fun writeAndReadSpend() = runBlocking{
        val spend = Spend(Date(), 100, "for Bread")
        spendsDao.addSpend(spend)
        val spends = spendsDao.getLast20Spends()
        Log.e("spend--->",spend.toString())
        assertThat(spends.contains(spend)).isTrue()
    }


}