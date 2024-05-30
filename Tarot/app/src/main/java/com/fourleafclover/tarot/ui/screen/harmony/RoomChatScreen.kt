package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.ui.component.AppBarClose
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.utils.getPickedTopic

@Composable
@Preview
fun RoomChatScreen(navController: NavHostController = rememberNavController()){

    Column(modifier = getBackgroundModifier(backgroundColor_2)) {
        AppBarClose(
            navController = navController,
            pickedTopicTemplate = getPickedTopic(pickedTopicNumber),
            backgroundColor = backgroundColor_2,
            isTitleVisible = false
        )

        Box(modifier = Modifier){


        }
    }

}

@Composable
@Preview
fun ChattingBox(){

}