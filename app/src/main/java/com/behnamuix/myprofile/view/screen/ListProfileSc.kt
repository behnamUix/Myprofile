package com.behnamuix.myprofile.view.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.behnamuix.myprofile.R
import com.behnamuix.myprofile.db.dao.UsersDao
import com.behnamuix.myprofile.db.entity.UsersEntity
import com.behnamuix.myprofile.view.component.ProfileRow
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun ListProfileSc(
    users: List<UsersEntity>,
    onDeleteUser: (UsersEntity) -> Unit,
    userDao: UsersDao,
) {
    var visible by remember { mutableStateOf(true) }
    var show by rememberSaveable() { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        show = true
    }

    if (users.isEmpty()) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                AnimatedVisibility(
                    visible = show,
                    enter = fadeIn() + slideInHorizontally(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        InfiniteTransformEmptyBox()
                        Text("No profiles yet")
                        Text("Tap + to add one")

                    }
                }


            }
        }
    } else {
        LazyColumn(modifier = Modifier.animateContentSize(tween(1000))) {
            items(users) { user ->
                AnimatedVisibility(visible = visible, exit = shrinkVertically() + fadeOut()) {
                    ProfileRow(
                        userDao,
                        user = user,
                        onDelete = { onDeleteUser(user) }
                    )
                }

            }
        }
    }

}

@Composable
fun InfiniteTransformEmptyBox() {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val transform by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -32f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "move box to up and down"
    )

    Image(
        modifier = Modifier.offset { IntOffset(0, transform.roundToInt()) },
        painter = painterResource(R.drawable.icon_empty_list),
        contentDescription = ""
    )
}
