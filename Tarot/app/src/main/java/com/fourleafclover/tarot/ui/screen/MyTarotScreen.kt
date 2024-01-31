
package com.fourleafclover.tarot.ui.screen

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.utils.getPickedTopic
import com.fourleafclover.tarot.myTarotResults
import com.fourleafclover.tarot.selectedTarotResult
import com.fourleafclover.tarot.ui.component.AppBarPlain
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateSaveState
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_2
import com.fourleafclover.tarot.ui.theme.gray_4
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.white

var showSheet = mutableStateOf(false)

@Preview
@Composable
fun MyTarotScreen(navController: NavHostController = rememberNavController()) {

    Box {

        if (showSheet.value) {
            BottomSheet()
        }

        Column(
            modifier = Modifier
                .background(color = gray_9)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
        ) {

            // MY 타로 앱바
            AppBarPlain(title = "MY 타로", backgroundColor = backgroundColor_2)

            // 갯수 표시
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    text = "${myTarotResults.size}", style = getTextStyle(
                        fontSize = 14,
                        fontWeight = FontWeight.Medium,
                        color = highlightPurple,
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

            // 메인
            Box {

                // 목록이 비어있는 경우
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 60.dp)
                        .alpha(if (myTarotResults.size == 0) 1f else 0f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.illust_crystalball),
                        contentDescription = "아직 저장된 타로 기록이 없어요!",
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    Text(
                        text = "아직 저장된\n타로 기록이 없어요!",
                        style = getTextStyle(
                            fontSize = 14,
                            fontWeight = FontWeight.Medium,
                            color = gray_5
                        ),
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }

                // 목록 있는 경우
                LazyColumn(
                    Modifier.padding(bottom = 50.dp),
                    contentPadding = PaddingValues(vertical = 10.dp),
                    content = {
                        items(myTarotResults.size) {
                            MyTarotItemComponent(navController, it)
                        }

                    })
            }
        }
    }
}

@Composable
fun MyTarotItemComponent(
    navController: NavHostController = rememberNavController(),
    idx: Int = 0
){
    Box(modifier = Modifier
        .padding(bottom = 16.dp)
        .clickable {
            selectedTarotResult = myTarotResults[idx]
            navigateSaveState(navController, ScreenEnum.MyTarotDetailScreen.name)
        }) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = gray_8, shape = RoundedCornerShape(10.dp))
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(painter = painterResource(id = when (myTarotResults[idx].tarotType) {
                0 -> R.drawable.icon_love
                1 -> R.drawable.icon_study
                2 -> R.drawable.icon_dream
                3 -> R.drawable.icon_job
                4 -> R.drawable.icon_today
                else -> R.drawable.icon_love
            }),
                contentDescription = null,
                modifier = Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = getPickedTopic(myTarotResults[idx].tarotType).majorTopic,
                        style = getTextStyle(
                            fontSize = 14,
                            fontWeight = FontWeight.Medium,
                            color = white
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Text(
                        text = getPickedTopic(myTarotResults[idx].tarotType).majorQuestion,
                        style = getTextStyle(
                            fontSize = 12,
                            fontWeight = FontWeight.Normal,
                            color = gray_2,
                        ),
                        modifier = Modifier
                    )


                }

                Text(
                    text = myTarotResults[idx].createdAt,
                    style = getTextStyle(
                        fontSize = 12,
                        fontWeight = FontWeight.Normal,
                        color = gray_4
                    ),
                    modifier = Modifier,
                    textAlign = TextAlign.End
                )
            }

            Image(
                painter = painterResource(id = R.drawable.dots_2),
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 6.dp, end = 6.dp)
                    .fillMaxHeight()
                    .align(Alignment.Top)
                    .clickable {
                        selectedTarotResult = myTarotResults[idx]
                        showSheet.value = true
                    },
                contentDescription = ""
            )
        }
    }
}

@Composable
fun BottomSheet() {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { showSheet.value = false },
        sheetState = modalBottomSheetState,
        contentColor = BottomSheetDefaults.ContainerColor
    ) {
        Text(
            text = "삭제하기",
            style = getTextStyle(
                fontSize = 18,
                fontWeight = FontWeight.Normal,
                color = gray_9
            ),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
                .clickable {
                    showSheet.value = false
                    MyApplication.prefs.deleteTarotResult()
                })
    }

}
