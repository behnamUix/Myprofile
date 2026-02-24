package com.behnamuix.myprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.myprofile.db.dao.UsersDao
import com.behnamuix.myprofile.db.entity.UsersEntity
import kotlinx.coroutines.launch

class ProfileViewmodel(private val usersDao: UsersDao) : ViewModel() {
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
    }
}