package com.behnamuix.myprofile.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String,
    val phoneNumber: String,
    val jobTitle: String,
    val bio: String,
    val profileImage: String? = null

)