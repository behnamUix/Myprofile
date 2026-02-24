package com.behnamuix.myprofile.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.behnamuix.myprofile.db.dao.UsersDao
import com.behnamuix.myprofile.db.entity.UsersEntity
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.behnamuix.myprofile.view.screen.EditProfileSc
import java.io.File


@Composable
fun ProfileRow(
    userDao: UsersDao,
    user: UsersEntity,
    onDelete: () -> Unit
) {
    val nav= LocalNavigator.currentOrThrow
    val imageFile = user.profileImage?.let { File(it) }

    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                AsyncImage(
                    contentScale = ContentScale.FillBounds,
                    model = imageFile?.takeIf { it.exists() },
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(user.fullName, style = MaterialTheme.typography.headlineSmall)
                    Text(user.phoneNumber, style = MaterialTheme.typography.labelMedium)
                    Text(user.jobTitle, style = MaterialTheme.typography.labelMedium)
                    Text(user.bio, style = MaterialTheme.typography.labelMedium)
                }


            }
            Row {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1f),
                    onClick = { onDelete() }
                ) {
                    Text("Delete")
                }
                TextButton(modifier = Modifier.weight(1f), onClick = {
                    nav.push(EditProfileSc(user,userDao))
                }) { Text("edit") }
            }


        }


    }


}