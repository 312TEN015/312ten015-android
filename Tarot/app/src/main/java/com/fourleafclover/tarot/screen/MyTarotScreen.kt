
package com.fourleafclover.tarot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_2
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highligtPurple
import com.fourleafclover.tarot.ui.theme.white


@Preview
@Composable
fun MyTarotScreen(navController: NavHostController = rememberNavController()) {

    Column(modifier = Modifier
        .background(color = gray_9)
        .padding(horizontal = 20.dp)) {

        Box(modifier = Modifier
            .padding(top = 10.dp)
            .height(48.dp)
            .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "MY 타로",
                style = getTextStyle(16, FontWeight.Medium, white),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
            Text(
                text = "8", style = getTextStyle(
                    fontSize = 14,
                    fontWeight = FontWeight.Medium,
                    color = highligtPurple,
                )
            )

            Text(
                text = "/10", style = getTextStyle(
                    fontSize = 14,
                    fontWeight = FontWeight.Medium,
                    color = gray_6,
                )
            )

        }


        LazyColumn(
            contentPadding = PaddingValues(vertical = 10.dp),
            content = {
            items(8) {
                Box(modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clickable {
                    navController.navigate(ScreenEnum.ResultScreen.name) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = gray_7, shape = RoundedCornerShape(8.dp))
                            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "나의 하루가 궁금해!", style = getTextStyle(
                                    fontSize = 12,
                                    fontWeight = FontWeight.Medium,
                                    color = gray_2,
                                ), modifier = Modifier.padding(bottom = 4.dp)
                            )

                            Text(
                                text = "오늘의 운세", style = getTextStyle(
                                    fontSize = 18,
                                    fontWeight = FontWeight.Bold,
                                    color = white
                                )
                            )
                        }

                        Column(modifier = Modifier,
                            verticalArrangement = Arrangement.Bottom) {
                            Image(
                                painter = painterResource(id = R.drawable.dots_2),
                                modifier = Modifier
                                    .padding(top = 12.dp, bottom = 16.dp)
                                    .size(24.dp)
                                    .align(Alignment.End),
                                contentDescription = ""
                            )

                            Text(
                                text = "2023년 10월 15일", style = getTextStyle(
                                    fontSize = 12,
                                    fontWeight = FontWeight.Medium,
                                    color = gray_5
                                ),
                                modifier = Modifier,
                                textAlign = TextAlign.End
                            )



                        }
                    }
                }
            }

        })
    }
}