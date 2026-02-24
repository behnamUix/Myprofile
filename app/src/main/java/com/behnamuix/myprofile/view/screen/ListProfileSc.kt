package com.behnamuix.myprofile.view.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.behnamuix.myprofile.db.dao.UsersDao
import com.behnamuix.myprofile.db.entity.UsersEntity
import com.behnamuix.myprofile.view.component.ProfileRow

@Composable
fun ListProfileSc(
    users: List<UsersEntity>,
    onDeleteUser: (UsersEntity) -> Unit,
    userDao: UsersDao,
) {
    var visible by remember { mutableStateOf(true) }

    if (users.isEmpty()) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("📭", style = MaterialTheme.typography.displayMedium)
                Text("No profiles yet")
                Text("Tap + to add one")
            }
        }
    } else {
        LazyColumn(modifier = Modifier.animateContentSize(tween(1000))) {
            items(users) { user ->
                AnimatedVisibility(visible = visible, exit = shrinkVertically() + fadeOut()) {
                    ProfileRow(
                        userDao,
                        user = user,
                        onDelete = { onDeleteUser(user)}
                    )
                }

            }
        }
    }
}