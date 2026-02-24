package com.behnamuix.myprofile.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.myprofile.db.dao.UsersDao
import com.behnamuix.myprofile.db.entity.UsersEntity
import kotlinx.coroutines.launch

class ProfileViewmodel(private val usersDao: UsersDao,val ctx: Context) : ViewModel() {
    fun insert(user: UsersEntity) {
        viewModelScope.launch {
            usersDao.insert(user)
        }
    }
    fun update(user: UsersEntity) {
        viewModelScope.launch {
            usersDao.update(user)
        }
    }
    fun delete(user: UsersEntity) {
        viewModelScope.launch {
            usersDao.delete(user)
        }
        Toast.makeText(ctx, "پروفایل حذف شد", Toast.LENGTH_SHORT).show()
    }
}