package com.behnamuix.myprofile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.behnamuix.myprofile.db.DatabaseProvider
import com.behnamuix.myprofile.db.entity.UsersEntity
import com.behnamuix.myprofile.ui.theme.MyprofileTheme
import com.behnamuix.myprofile.view.screen.AddProfileSc
import com.behnamuix.myprofile.view.screen.ListProfileSc
import com.behnamuix.myprofile.viewmodel.ProfileViewmodel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyprofileTheme {
                Navigator(MainScreen)
            }
        }
    }

    object MainScreen : Screen {

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        override fun Content() {
            val nav = LocalNavigator.currentOrThrow
            val appContext = LocalContext.current
            val userDao = remember { DatabaseProvider.getUserDao(appContext) }
            val users = remember { mutableStateListOf<UsersEntity>() }
            val viewModel = ProfileViewmodel(userDao, ctx = LocalContext.current)
            suspend fun refreshContacts() {
                users.clear()
                users.addAll(userDao.getAll())
            }
            LaunchedEffect(Unit) {
                refreshContacts()
            }
            Scaffold(topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("profiles") })
            }, floatingActionButton = {
                ExtendedFloatingActionButton (
                    onClick = {
                        nav.push(AddProfileSc(userDao, users))
                    }) {
                    Icon(
                        imageVector = Icons.Default.Add, contentDescription = "Add User"
                    )
                }
            }) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {

                    ListProfileSc(
                        users = users,
                        onDeleteUser = {
                            viewModel.delete(it)
                            viewModel.viewModelScope.launch {
                                refreshContacts()

                            }


                        }, userDao
                    )


                }
            }
        }
    }

}
