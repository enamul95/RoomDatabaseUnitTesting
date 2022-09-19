package com.era.app.room.datasource

import com.era.app.room.dao.SpendDao
import com.era.app.room.entity.Spend

class SpendsTrackerDataSource(
    private val db: SpendDao
) {

    suspend fun addSpend(spend: Spend) = db.addSpend(spend)

    suspend fun getLast20Spends() = db.getLast20Spends()
}