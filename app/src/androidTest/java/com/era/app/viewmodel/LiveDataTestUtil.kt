package com.era.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun<T> LiveData<T>.getOrAwaitValue():T{
    var data:T? = null
    //wait for other thead to complete
    var latch = CountDownLatch(1)

    val observer = object :Observer<T>{
        override fun onChanged(t: T) {
            data =t
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)

        }
    }
    this.observeForever(observer)

    // wait until the life data get & wait for only 2 second
    // 2 second wait if live data not come
    try {
        if(!latch.await(2,TimeUnit.SECONDS)){
            throw TimeoutException("Live Data Never get its value")

        }
    } finally {
        this.removeObserver(observer)
    }


    return data as T
}