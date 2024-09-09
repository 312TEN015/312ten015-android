
package com.fourleafclover.tarot.ui.screen.fortune

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.constant.questionCount
import com.fourleafclover.tarot.fortuneViewModel
import com.fourleafclover.tarot.ui.component.AppBarClose
import com.fourleafclover.tarot.ui.component.QuestionsComponent
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.component.setStatusbarColor
import com.fourleafclover.tarot.ui.theme.backgroundColor_2

@Preview
@Composable
fun InputScreen(navController: NavHostController = rememberNavController()) {
    val localContext = LocalContext.current
    val pickedTopicTemplate by fortuneViewModel.pickedTopicState

    setStatusbarColor(LocalView.current, pickedTopicTemplate.topicSubjectData.primaryColor)

    Column(modifier = getBackgroundModifier(backgroundColor_2))
    {

        AppBarClose(
            navController = navController,
            pickedTopicTemplate = pickedTopicTemplate.topicSubjectData,
            backgroundColor = pickedTopicTemplate.topicSubjectData.primaryColor
        )

        Column(modifier = Modifier) {

            LazyColumn(content = {
                // numberOfQuestion + header + footer
                items(questionCount + 2){
                    QuestionsComponent(pickedTopicTemplate.topicSubjectData, it, navController, localContext)
                }

            })

        }
    }

}

