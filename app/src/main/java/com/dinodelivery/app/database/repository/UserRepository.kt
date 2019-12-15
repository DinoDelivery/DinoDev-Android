package com.dinodelivery.app.database.repository

import com.dinodelivery.app.DinoDeliveryApp.Companion.db
import com.dinodelivery.app.database.dao.UserDao
import com.dinodelivery.app.database.entities.User

class UserRepository {

    val userDao: UserDao? = db?.userDao()

    suspend fun saveUser(user: User) {
        userDao?.saveUser(user)
    }

    suspend fun getUser(username: String, password: String): User? {
        return userDao?.getUser(username, password)
    }

    suspend fun getUserById(id: Int): User? {
        return userDao?.getUserById(id)
    }
}