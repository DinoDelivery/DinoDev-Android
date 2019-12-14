package com.dinodelivery.app.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dinodelivery.app.entities.UserProfileData

@Entity(tableName = "users")
data class User(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "username") var username: String? = null,
    @ColumnInfo(name = "password") var password: String? = null,
    @ColumnInfo(name = "phone") var phone: String? = null,
    @ColumnInfo(name = "card") var card: String? = null,
    @ColumnInfo(name = "photo") var photo: String? = null
) {
    fun toUserProfileData(): UserProfileData {
        return UserProfileData(username, phone, card, photo)
    }
}