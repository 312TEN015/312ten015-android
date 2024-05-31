package com.fourleafclover.tarot.ui.screen.harmony

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.data.CardResultData
import com.fourleafclover.tarot.data.OverallResultData
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.tarotOutputDto
import com.fourleafclover.tarot.ui.component.AppBarClose
import com.fourleafclover.tarot.ui.component.AppBarCloseTarotResult
import com.fourleafclover.tarot.ui.component.CardSlider
import com.fourleafclover.tarot.ui.component.CloseDialog
import com.fourleafclover.tarot.ui.component.CloseWithoutSaveDialog
import com.fourleafclover.tarot.ui.component.ControlDialog
import com.fourleafclover.tarot.ui.component.SaveCompletedDialog
import com.fourleafclover.tarot.ui.component.appBarModifier
import com.fourleafclover.tarot.ui.component.backgroundModifier
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.fourleafclover.tarot.ui.theme.TextB01M18
import com.fourleafclover.tarot.ui.theme.TextB02M16
import com.fourleafclover.tarot.ui.theme.TextB03M14
import com.fourleafclover.tarot.ui.theme.TextButtonM16
import com.fourleafclover.tarot.ui.theme.TextH01M26
import com.fourleafclover.tarot.ui.theme.TextH02M22
import com.fourleafclover.tarot.ui.theme.backgroundColor_1
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_2
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_4
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getCardImageId
import com.fourleafclover.tarot.utils.getPickedTopic
import com.fourleafclover.tarot.utils.setDynamicLink

@Composable
fun ResultScreen(
    navController: NavHostController,
    resultViewModel: ResultViewModel = remember {
        ResultViewModel()
    }
) {
}

@Preview
@Composable
fun ResultScreenPreview(
    navController: NavHostController = rememberNavController(),
    resultViewModel: ResultViewModel = remember { ResultViewModel() }
) {

    val localContext = LocalContext.current

    tarotOutputDto = TarotOutputDto(
        "0",
        0,
        arrayListOf(0, 1, 2),
        "2024-01-14T12:38:23.000Z",
        arrayListOf(
            CardResultData(arrayListOf("keyword1", "keyword2", "keyword3"), "description1"),
            CardResultData(arrayListOf("keyword", "keyword2", "keyword3"), "description1"),
            CardResultData(arrayListOf("keyword", "keyword2", "keyword3"), "description1")
        ),
        OverallResultData("summary result", "full result")
    )

    Column(modifier = getBackgroundModifier(backgroundColor_2).verticalScroll(rememberScrollState()))
    {

        ControlDialog(navController)


        AppBarCloseTarotResult(
            navController,
            getPickedTopic(0),
            backgroundColor_2,
            true,
            resultViewModel
        )

        Column(
            modifier = Modifier
        ) {

            TextH02M22(
                text = "${resultViewModel.getNickname()}님이\n선택하신 카드는\n이런 의미를 담고 있어요.",
                color = white,
                modifier = Modifier
                    .background(color = backgroundColor_2)
                    .padding(horizontal = 20.dp, vertical = 32.dp)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .background(color = gray_8, shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    TextB03M14(
                        color = gray_7,
                        text = "내가 선택한 카드",
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .background(
                                shape = RoundedCornerShape(6.dp),
                                color = gray_2
                            )
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .defaultMinSize(121.dp),
                        textAlign = TextAlign.Center
                    )
                    TextB03M14(
                        color = gray_7,
                        text = "상대방 카드",
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .background(
                                shape = RoundedCornerShape(6.dp),
                                color = gray_2
                            )
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .defaultMinSize(121.dp),
                        textAlign = TextAlign.Center
                    )
                }

                CardSlider(tarotResult = tarotOutputDto, localContext = localContext)
            }


            OverallResult()

        }

    }
}

@Preview
@Composable
fun OverallResult(resultViewModel: ResultViewModel = remember { ResultViewModel() }) {
    Column(
        modifier = Modifier
            .background(color = gray_8)
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val localContext = LocalContext.current

        TextH01M26(
            text = "타로 카드 종합 리딩",
            color = highlightPurple,
            modifier = Modifier
                .padding(top = 48.dp)
                .fillMaxWidth()
        )

        TextB01M18(
            text = tarotOutputDto.overallResult?.summary.toString(),
            color = white,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        )

        TextB02M16(
            text = tarotOutputDto.overallResult?.full.toString(),
            color = gray_3,
            modifier = Modifier
                .padding(top = 12.dp, bottom = 64.dp)
                .wrapContentHeight()
        )

        Button(
            onClick = {
                resultViewModel.openCompleteDialog()

                // 타로 결과 id 저장
                MyApplication.prefs.addTarotResult(tarotOutputDto.tarotId)
                resultViewModel.saveResult()
            },
            shape = RoundedCornerShape(10.dp),
            enabled = !resultViewModel.isSaved(),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = highlightPurple,
                contentColor = gray_1,
                disabledContainerColor = gray_6,
                disabledContentColor = gray_5
            )
        ) {

            if (resultViewModel.isSaved()) {
                Image(
                    painter = painterResource(id = R.drawable.check_filled_disabled),
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .padding(end = 4.dp),
                    alignment = Alignment.Center
                )
            }

            TextButtonM16(
                text = if (resultViewModel.isSaved()) "저장 완료!" else "타로 저장하기",
                modifier = Modifier.padding(vertical = 8.dp),
                color = if (!resultViewModel.isSaved()) white else gray_5,
            )
        }

        Button(
            onClick = { resultViewModel.openCloseDialog() },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = gray_9,
                contentColor = gray_1,
                disabledContainerColor = gray_5,
                disabledContentColor = gray_6
            )
        ) {
            TextButtonM16(
                text = "홈으로 돌아가기",
                modifier = Modifier.padding(vertical = 8.dp),
                color = gray_1
            )
        }

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .padding(bottom = 45.dp)
                .clickable {
                    setDynamicLink(localContext, tarotOutputDto.tarotId)
                },
            horizontalArrangement = Arrangement.Center
        )
        {
            Image(
                painter = painterResource(id = R.drawable.share),
                contentDescription = null,
                modifier = Modifier.padding(end = 3.dp)
            )
            TextButtonM16(
                text = "공유하기",
                color = gray_3
            )
        }
    }
}
