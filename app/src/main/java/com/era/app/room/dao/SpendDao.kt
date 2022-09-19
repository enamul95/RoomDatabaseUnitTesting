package com.era.app.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.era.app.room.entity.Spend

@Dao
interface SpendDao {

    @Insert
    suspend fun addSpend(spend: Spend)

    @Query("SELECT * FROM spends ORDER BY date DESC LIMIT 20")
    suspend fun getLast20Spends():List<Spend>

}