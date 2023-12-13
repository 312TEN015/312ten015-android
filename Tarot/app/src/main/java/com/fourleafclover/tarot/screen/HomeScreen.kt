package com.fourleafclover.tarot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.white


@Composable
fun HomeScreen(navController: NavHostController) {

    Column(modifier = Modifier.background(color = gray_8).padding(horizontal = 20.dp, vertical = 20.dp).fillMaxSize()) {
        Text(
            text = "타로 카드를 뽑고\n운세를 확인해보세요!",
            style = getTextStyle(26, FontWeight.Medium, white),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Column {
            Text(
                text = "주제별 운세",
                style = getTextStyle(16, FontWeight.Medium, white),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(Modifier.padding(bottom = 24.dp)) {
                Column(modifier = Modifier.padding(end = 8.dp)) {
                    Image(modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable {
                            navController.navigate(ScreenEnum.InputScreen.name) {
                                navController.graph.startDestinationRoute?.let {
                                    popUpTo(it) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }, painter = painterResource(id = R.drawable.category_love), contentDescription = "연애운")
                    Image(painter = painterResource(id = R.drawable.category_dream), contentDescription = "소망운")
                }

                Column {
                    Image(modifier = Modifier.padding(bottom = 8.dp), painter = painterResource(id = R.drawable.category_study), contentDescription = "학업운")
                    Image(painter = painterResource(id = R.drawable.category_job), contentDescription = "취업운")
                }
            }

            Column {
                Text(
                    text = "오늘의 운세",
                    style = getTextStyle(16, FontWeight.Medium, white),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Image(painter = painterResource(id = R.drawable.category_today), contentDescription = "오늘의 운세")

            }




        }
    }

}