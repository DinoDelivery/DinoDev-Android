package com.dinodelivery.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dinodelivery.app.database.dao.UserDao
import com.dinodelivery.app.database.entities.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class DinoDeliveryDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: DinoDeliveryDatabase? = null

        fun getDatabase(context: Context): DinoDeliveryDatabase? {
            if (INSTANCE == null) {
                synchronized(DinoDeliveryDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DinoDeliveryDatabase::class.java, "dinodelivery.db"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}