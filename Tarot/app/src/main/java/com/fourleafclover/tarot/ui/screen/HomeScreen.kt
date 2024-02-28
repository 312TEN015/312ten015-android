package com.fourleafclover.tarot.ui.screen

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.component.setStatusbarColor
import com.fourleafclover.tarot.ui.navigation.FinishOnBackPressed
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateSaveState
import com.fourleafclover.tarot.ui.theme.TextH01M26
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.receiveShareRequest

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Preview
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    var initialize by remember { mutableStateOf(false) }

    /* 한번만 실행 */
    if (!initialize){

        // 공유하기 확인
        val activity = LocalContext.current.findActivity()
        if (activity != null && activity.intent != null) {
            receiveShareRequest(activity, navController)
        }

        // 인풋 초기화
        text1.value = TextFieldValue("")
        text2.value = TextFieldValue("")
        text3.value = TextFieldValue("")

        initialize = true
    }

    // 상태바 초기화
    setStatusbarColor(LocalView.current, backgroundColor_2)

    // 뒤로가기 시 행동
    FinishOnBackPressed()

    Column(modifier = getBackgroundModifier(backgroundColor_2)
        .padding(horizontal = 20.dp)
        .padding(bottom = 60.dp)
        .verticalScroll(rememberScrollState())) {

        TextH01M26(
            text = "타로 카드를 뽑고\n운세를 확인해보세요!",
            modifier = Modifier.padding(top = 26.dp, bottom = 24.dp),
            color = white
        )

        Column {
            Text(
                text = "주제별 운세",
                style = getTextStyle(16, FontWeight.Medium, gray_3),
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Row(Modifier.padding(bottom = 32.dp)) {
                Column(modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1f)) {
                    Image(modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable {
                            pickedTopicNumber = 0
                            navigateSaveState(navController, ScreenEnum.InputScreen.name)
                        }, painter = painterResource(id = R.drawable.category_love), contentDescription = "연애운")
                    Image(painter = painterResource(id = R.drawable.category_dream),
                        contentDescription = "소망운",
                        modifier = Modifier.clickable {
                            pickedTopicNumber = 2
                            navigateSaveState(navController, ScreenEnum.InputScreen.name)
                        }
                    )
                }

                Column(modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(1f)) {
                    Image(modifier = Modifier
                        .padding(bottom = 6.dp)
                        .clickable {
                            pickedTopicNumber = 1
                            navigateSaveState(navController, ScreenEnum.InputScreen.name)
                        },
                        painter = painterResource(id = R.drawable.category_study),
                        contentDescription = "학업운")
                    Image(painter = painterResource(id = R.drawable.category_job),
                        contentDescription = "취업운",
                        modifier = Modifier.clickable {
                            pickedTopicNumber = 3
                            navigateSaveState(navController, ScreenEnum.InputScreen.name)
                        }
                    )
                }
            }

            Column(modifier = Modifier.padding(bottom = 42.dp)) {
                Text(
                    text = "오늘의 운세",
                    style = getTextStyle(16, FontWeight.Medium, gray_3),
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Image(painter = painterResource(id = R.drawable.category_today),
                    contentDescription = "오늘의 운세",
                    Modifier.clickable {
                        pickedTopicNumber = 4
                        navigateSaveState(navController, ScreenEnum.PickTarotScreen.name)
                    })

            }




        }
    }

}