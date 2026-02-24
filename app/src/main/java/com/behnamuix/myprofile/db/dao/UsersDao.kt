package com.behnamuix.myprofile.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.behnamuix.myprofile.db.entity.UsersEntity

@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UsersEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UsersEntity)

    @Delete
    suspend fun delete(user: UsersEntity)

    @Query(" UPDATE users SET fullName = :name, phoneNumber = :phone, jobTitle = :job, bio = :bioTitle ")
   suspend fun edit(name:String,phone:String,job:String,bioTitle:String)

    @Update
    suspend fun update(user: UsersEntity)


}