package com.fourleafclover.tarot.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.fourleafclover.tarot.ui.component.CardSlider
import com.fourleafclover.tarot.ui.component.CloseDialog
import com.fourleafclover.tarot.ui.component.CloseWithoutSaveDialog
import com.fourleafclover.tarot.ui.component.SaveCompletedDialog
import com.fourleafclover.tarot.ui.component.appBarModifier
import com.fourleafclover.tarot.ui.component.backgroundModifier
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.fourleafclover.tarot.ui.theme.backgroundColor_1
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getCardImageId
import com.fourleafclover.tarot.utils.getPickedTopic
import com.fourleafclover.tarot.utils.setDynamicLink

var openCloseDialog = mutableStateOf(false) // close dialog state
var saveState = mutableStateOf(false)   // 타로 결과 저장했는지 여부
var openCompleteDialog = mutableStateOf(false)  // 타로 저장 완료 dialog state

@Composable
fun ResultScreen(navController: NavHostController){

    var initialize by remember { mutableStateOf(false) }

    // 변수 초기화
    if (!initialize){
        openCloseDialog.value = false
        saveState.value = false
        openCompleteDialog.value = false
        initialize = true
    }

    val localContext = LocalContext.current

    Column(modifier = backgroundModifier.verticalScroll(rememberScrollState()))
    {

        ControlDialog(navController)


        Box(
            modifier = appBarModifier
                .background(color = backgroundColor_1)
                .padding(top = 10.dp, bottom = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getPickedTopic(pickedTopicNumber).majorTopic,
                style = getTextStyle(16, FontWeight.Medium, white),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(id = R.drawable.cancel),
                contentDescription = "닫기버튼",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { openCloseDialog.value = true }
                    .padding(end = 20.dp)
                    .size(28.dp),
                alignment = Alignment.Center
            )
        }

        Column(modifier = Modifier

        ) {

            Text(
                text = "선택하신 카드는\n이런 의미를 담고 있어요.",
                style = getTextStyle(22, FontWeight.Medium, white),
                modifier = Modifier
                    .background(color = gray_8)
                    .padding(horizontal = 20.dp, vertical = 32.dp)
                    .fillMaxWidth()
            )

            CardSlider(tarotResult = tarotOutputDto, localContext = localContext)

            OverallResult()

        }

    }
}

@Preview
@Composable
fun ResultScreenPreview(navController: NavHostController = rememberNavController()){
    val localContext = LocalContext.current

    tarotOutputDto = TarotOutputDto(
        "0",
        0,
        arrayListOf(0, 1, 2),
        "2024-01-14T12:38:23.000Z",
        arrayListOf(
            CardResultData(arrayListOf("keyword1", "keyword2", "keyword3"), "description1"),
            CardResultData(arrayListOf("keyword", "keyword2", "keyword3"), "description1"),
            CardResultData(arrayListOf("keyword", "keyword2", "keyword3"), "description1")),
        OverallResultData("summary result", "full result")
    )

    Column(modifier = backgroundModifier.verticalScroll(rememberScrollState()))
    {

        ControlDialog(navController)


        Box(
            modifier = appBarModifier
                .background(color = backgroundColor_1)
                .padding(top = 10.dp, bottom = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getPickedTopic(pickedTopicNumber).majorTopic,
                style = getTextStyle(16, FontWeight.Medium, white),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(id = R.drawable.cancel),
                contentDescription = "닫기버튼",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { openCloseDialog.value = true }
                    .padding(end = 20.dp)
                    .size(28.dp),
                alignment = Alignment.Center
            )
        }

        Column(modifier = Modifier

        ) {

            Text(
                text = "선택하신 카드는\n이런 의미를 담고 있어요.",
                style = getTextStyle(22, FontWeight.Medium, white),
                modifier = Modifier
                    .background(color = gray_8)
                    .padding(horizontal = 20.dp, vertical = 32.dp)
                    .fillMaxWidth()
            )


            val tmpList = arrayListOf<Int>()
            for (i in tarotOutputDto.cards) {
                tmpList.add(getCardImageId(localContext, i.toString()))
            }

            CardSlider(tarotResult = tarotOutputDto, localContext = localContext)

            OverallResult()

        }

    }
}

@Preview
@Composable
fun OverallResult(){
    Column(
        modifier = Modifier
            .background(color = gray_8)
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val localContext = LocalContext.current

        Text(
            text = "타로 카드 종합 리딩",
            style = getTextStyle(
                fontSize = 26,
                fontWeight = FontWeight.Medium,
                color = highlightPurple
            ),
            modifier = Modifier.padding(top = 48.dp).fillMaxWidth(),
            textAlign = TextAlign.Left
        )

        Text(
            text = tarotOutputDto.overallResult?.summary.toString(),
            style = getTextStyle(
                fontSize = 18,
                fontWeight = FontWeight.Medium,
                color = white
            ),
            lineHeight = 28.sp,
            modifier = Modifier.padding(top = 24.dp).fillMaxWidth(),
            textAlign = TextAlign.Left
        )

        Text(
            text = tarotOutputDto.overallResult?.full.toString(),
            style = getTextStyle(
                fontSize = 16,
                fontWeight = FontWeight.Medium,
                color = gray_3
            ),
            lineHeight = 28.sp,
            modifier = Modifier
                .padding(top = 12.dp, bottom = 64.dp)
                .wrapContentHeight()
        )

        Button(
            onClick = {
                openCompleteDialog.value = true

                // 타로 결과 id 저장
                MyApplication.prefs.addTarotResult(tarotOutputDto.tarotId)
                saveState.value = true
            },
            shape = RoundedCornerShape(10.dp),
            enabled = !saveState.value,
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

            if (saveState.value){
                Image(painter = painterResource(id = R.drawable.check_filled_disabled),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp).padding(end = 4.dp),
                    alignment = Alignment.Center
                )
            }

            Text(
                text = if (saveState.value) "저장 완료!" else "타로 저장하기",
                modifier = Modifier.padding(vertical = 8.dp),
                style = getTextStyle(
                    fontSize = 16,
                    fontWeight = FontWeight.Medium,
                    color = if (!saveState.value) white else gray_5
                ),
            )
        }

        Button(
            onClick = { openCloseDialog.value = true },
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
            Text(text = "홈으로 돌아가기", modifier = Modifier.padding(vertical = 8.dp),
                style = getTextStyle(
                    fontSize = 16,
                    fontWeight = FontWeight.Medium,
                    color = white
                )
                )
        }

        Row(modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .padding(bottom = 45.dp)
            .clickable {
                setDynamicLink(localContext, tarotOutputDto.tarotId)
            },
            horizontalArrangement = Arrangement.Center)
        {
            Image(painter = painterResource(id = R.drawable.share),
                contentDescription = null,
                modifier = Modifier.padding(end = 3.dp))
            Text(
                text = "공유하기",
                style = getTextStyle(
                    fontSize = 16,
                    fontWeight = FontWeight.Medium,
                    color = gray_3
                )
            )
        }
    }
}



/* Dialog ------------------------------------------------------------------------------------- */
@Composable
fun OpenCloseDialog(navController: NavHostController){
    // 닫기 버튼 눌렀을 때 && 타로 결과 저장 안한 경우
    if (openCloseDialog.value && !saveState.value){
        Dialog(onDismissRequest = { openCloseDialog.value = false }) {
            CloseWithoutSaveDialog(onClickNo = { openCloseDialog.value = false },
                onClickOk = {
                    navigateInclusive(navController, ScreenEnum.HomeScreen.name)
                })
        }
    }
    // 닫기 버튼 눌렀을 때 && 타로 결과 저장 한 경우
    else if(openCloseDialog.value && saveState.value){
        Dialog(onDismissRequest = { openCloseDialog.value = false }) {
            CloseDialog(onClickNo = { openCloseDialog.value = false },
                onClickOk = {
                    navigateInclusive(navController, ScreenEnum.HomeScreen.name)
                })
        }
    }

}

@Composable
fun OpenCompleteDialog(){
    // 타로 결과 저장 버튼 눌렀을 때
    if (openCompleteDialog.value){
        Dialog(onDismissRequest = { openCompleteDialog.value = false }) {
            SaveCompletedDialog(onClickOk = { openCompleteDialog.value = false })
        }
    }
}

@Composable
fun ControlDialog(navController: NavHostController){
    BackHandler { openCloseDialog.value = true }
    OpenCloseDialog(navController = navController)
    OpenCompleteDialog()

}