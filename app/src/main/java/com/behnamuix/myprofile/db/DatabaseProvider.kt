package com.behnamuix.myprofile.db

import android.content.Context
import androidx.room.Room
import com.behnamuix.myprofile.db.dao.UsersDao

object DatabaseProvider {

    private var appDatabase: AppDatabase? = null

    private fun getDatabase(context: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "profiles.db"
            ).build()
        }
        return appDatabase!!
    }

    fun getUserDao(context: Context): UsersDao {
        return getDatabase(context).usersDao()
    }
}