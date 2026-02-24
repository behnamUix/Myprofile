package com.behnamuix.myprofile.view.screen

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.behnamuix.myprofile.R
import com.behnamuix.myprofile.db.dao.UsersDao
import com.behnamuix.myprofile.db.entity.UsersEntity
import com.behnamuix.myprofile.viewmodel.ProfileViewmodel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

data class AddProfileSc(val userDao: UsersDao, val users: SnapshotStateList<UsersEntity>) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        var ctx = LocalContext.current
        var userFullName by rememberSaveable { mutableStateOf("") }
        var userPhoneNumber by rememberSaveable { mutableStateOf("") }
        var userJob by rememberSaveable { mutableStateOf("") }
        var userBio by rememberSaveable { mutableStateOf("") }
        var userImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
        var textfieldErr by remember { mutableStateOf(false) }
        val viewModel = ProfileViewmodel(userDao,ctx)
        val maxLength = 50
        val pickMedia = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                userImageUri = uri
                Log.d("PhotoPicker", "Selected URI: $uri")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
        Scaffold(

            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { nav.pop() }) {
                            Text("<", style = MaterialTheme.typography.headlineSmall)
                        }
                    },
                    title = { Text("Add Profile") }
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {


                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.background,
                                    MaterialTheme.colorScheme.primary
                                )
                            )
                        )
                )
                {
                    if (userImageUri != null) {
                        AsyncImage(
                            contentScale = ContentScale.FillBounds,
                            model = userImageUri,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            IconButton(
                                onClick = {
                                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.icon_camera),
                                    contentDescription = "Select Photo",
                                    tint = Color.White,
                                    modifier = Modifier.size(56.dp)
                                )
                            }
                        }

                    } else {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            IconButton(
                                onClick = {
                                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.icon_camera),
                                    contentDescription = "Select Photo",
                                    tint = Color.White,
                                    modifier = Modifier.size(56.dp)
                                )
                            }
                        }

                    }


                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedTextField(
                        isError = textfieldErr,
                        value = userFullName,
                        onValueChange = {
                            userFullName = it;if (userFullName.isNotEmpty()) {
                            textfieldErr = false
                        }
                        },
                        label = { Text("Name") },
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        isError = textfieldErr,
                        value = userJob,
                        onValueChange = { userJob = it },
                        label = { Text("Job") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(

                    value = userPhoneNumber,
                    onValueChange = {
                        if (it.all { c -> c.isDigit() } && it.length <= 11) {
                            userPhoneNumber = it
                        }
                    },
                    label = { Text("Phone") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    isError = userBio.length > 35,
                    value = userBio,
                    onValueChange = {
                        if (it.length <= maxLength) userBio = it
                    },
                    label = { Text("enter your bio....") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    maxLines = 5,
                    supportingText = {
                        Text("${userBio.length} / $maxLength")
                    }
                )
                Spacer(Modifier.weight(1f))

                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        if (userFullName.isEmpty() && userJob.isEmpty()) {
                            Toast.makeText(ctx, "please filled textfield!", Toast.LENGTH_SHORT)
                                .show()
                            textfieldErr = true
                        } else {
                            textfieldErr = false
                            try {
                                val savedPath = userImageUri?.let {
                                    ctx.copyUriToInternalFile(
                                        it,
                                        "profile_${System.currentTimeMillis()}.jpg"
                                    )
                                }
                                viewModel.insert(
                                    UsersEntity(
                                        fullName = userFullName,
                                        phoneNumber = userPhoneNumber,
                                        jobTitle = userJob,
                                        bio = userBio,
                                        profileImage = savedPath
                                    )
                                )
                                users.clear()
                                viewModel.viewModelScope.launch {
                                    users.addAll(userDao.getAll() as Collection<UsersEntity>)
                                }
                                nav.pop()
                            } catch (e: Exception) {
                                Log.e("DB_ERROR", "Insert failed", e)
                            }


                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save", Modifier.padding(8.dp))
                }
            }
        }

    }

    fun Context.copyUriToInternalFile(uri: Uri, filename: String): String? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val file = File(filesDir, filename)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}

data class EditProfileSc(val users: UsersEntity, val userDao: UsersDao) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        var ctx = LocalContext.current
        var userFullName by rememberSaveable { mutableStateOf(users.fullName) }
        var userPhoneNumber by rememberSaveable { mutableStateOf(users.phoneNumber) }
        var userJob by rememberSaveable { mutableStateOf(users.jobTitle) }
        var userBio by rememberSaveable { mutableStateOf(users.bio) }
        var textfieldErr by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        var viewModel = ProfileViewmodel(userDao,ctx)
        val maxLength = 50
        Scaffold(

            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { nav.pop() }) {
                            Text("<", style = MaterialTheme.typography.headlineSmall)
                        }
                    },
                    title = { Text("Edit Profile") }
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedTextField(
                        isError = textfieldErr,
                        value = userFullName,
                        onValueChange = {
                            userFullName = it;if (userFullName.isNotEmpty()) {
                            textfieldErr = false
                        }
                        },
                        label = { Text("Name") },
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        isError = textfieldErr,
                        value = userJob,
                        onValueChange = { userJob = it },
                        label = { Text("Job") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(

                    value = userPhoneNumber,
                    onValueChange = {
                        if (it.all { c -> c.isDigit() } && it.length <= 11) {
                            userPhoneNumber = it
                        }
                    },
                    label = { Text("Phone") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    isError = userBio.length > 35,
                    value = userBio,
                    onValueChange = {
                        if (it.length <= maxLength) userBio = it
                    },
                    label = { Text("enter your bio....") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    maxLines = 5,
                    supportingText = {
                        Text("${userBio.length} / $maxLength")
                    }
                )
                Spacer(Modifier.weight(1f))

                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        Toast.makeText(ctx, userFullName, Toast.LENGTH_SHORT).show()

                            textfieldErr = false
                                try {
                                    viewModel.update(
                                        UsersEntity(
                                            id = users.id,
                                            fullName = userFullName,
                                            phoneNumber = userPhoneNumber,
                                            jobTitle = userJob,
                                            bio = userBio,
                                        )
                                    )
                                    Toast.makeText(ctx, "پروفایل بروزرسانی شد", Toast.LENGTH_SHORT).show()
                                    nav.pop()


                                } catch (e: Exception) {
                                    Log.e("DB_ERROR", "edit failed", e)
                                }

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save", Modifier.padding(8.dp))
                }
            }
        }

    }

}