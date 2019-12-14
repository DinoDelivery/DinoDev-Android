package com.dinodelivery.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.dinodelivery.app.database.DinoDeliveryDatabase
import com.dinodelivery.app.database.DinoDeliveryDatabase.Companion.getDatabase
import com.google.gson.Gson


class DinoDeliveryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = getDatabase(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    companion object {
        var instance: DinoDeliveryApp? = null
        val MAPPER = Gson()
        var db: DinoDeliveryDatabase? = null
        private lateinit var mContext: DinoDeliveryApp
        val context: Context
            get() = mContext
    }

    init {
        mContext = this@DinoDeliveryApp
    }
}