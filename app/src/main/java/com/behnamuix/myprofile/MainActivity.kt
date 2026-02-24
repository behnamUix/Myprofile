package com.behnamuix.myprofile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.behnamuix.myprofile.db.config.DatabaseProvider
import com.behnamuix.myprofile.db.entity.UsersEntity
import com.behnamuix.myprofile.ui.theme.MyprofileTheme
import com.behnamuix.myprofile.view.screen.AddProfileSc
import com.behnamuix.myprofile.view.screen.ListProfileSc
import com.behnamuix.myprofile.viewmodel.ProfileViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            var expanded by remember { mutableStateOf(false) }

            val size by animateDpAsState(
                targetValue = if (expanded) 300.dp else 100.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessMediumLow
                ),
                label = ""
            )

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
                FloatingActionButton (
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.width(size),
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            expanded = true
                            delay(1000)
                            nav.push(AddProfileSc(userDao, users))
                        }

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
