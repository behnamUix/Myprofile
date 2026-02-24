package com.behnamuix.myprofile.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.behnamuix.myprofile.db.dao.UsersDao
import com.behnamuix.myprofile.db.entity.UsersEntity

@Database(entities = [UsersEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}