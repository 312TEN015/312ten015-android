
package com.fourleafclover.tarot.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highligtPurple
import com.fourleafclover.tarot.ui.theme.transparent
import com.fourleafclover.tarot.ui.theme.white

@Preview
@Composable
fun InputScreen(navController: NavHostController = rememberNavController()) {
    val localContext = LocalContext.current

    Column(modifier = Modifier
        .background(color = gray_8)
        .padding(horizontal = 24.dp, vertical = 24.dp)
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "연애운",
                style = getTextStyle(16, FontWeight.Medium, white),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center
            )

            Image(painter = painterResource(id = R.drawable.close), contentDescription = "닫기버튼",
                modifier = Modifier.fillMaxWidth(), alignment = Alignment.CenterEnd)
        }

        Text(
            text = "그 사람의 마음은?",
            style = getTextStyle(16, FontWeight.Medium, white),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "마음속에 있는 고민거리를\n입력해보세요!",
            style = getTextStyle(22, FontWeight.Medium, white),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "TIP!",
                style = getTextStyle(16, FontWeight.Medium, highligtPurple),
                modifier = Modifier
                    .background(color = gray_7, shape = RoundedCornerShape(2.dp))
                    .padding(2.dp)
            )

            Text(
                text = "구체적으로 입력할수록 더욱 상세한 결과를 받아볼 수 있어요.",
                style = getTextStyle(12, FontWeight.Normal, gray_5),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Divider(
            color = white,
            thickness = 1.dp,
            modifier = Modifier.padding(0.dp, 26.dp, 0.dp, 26.dp)
        )

        val maxChar = 50

        Column(modifier = Modifier.padding(bottom = 32.dp)) {
            Row(modifier = Modifier.padding(bottom = 10.dp)) {
                Text(
                    text = "01",
                    style = getTextStyle(18, FontWeight.Bold, highligtPurple),
                    modifier = Modifier.padding(start = 4.dp, end = 10.dp)
                )
                Text(
                    text = "그 사람과의 첫만남은 어땠나요?",
                    style = getTextStyle(16, FontWeight.Medium, gray_3)
                )
            }

            var text1 by remember { mutableStateOf(TextFieldValue("")) }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp)
                    .background(color = transparent),
                textStyle = getTextStyle(fontSize = 14, fontWeight = FontWeight.Medium, color = gray_3),
                shape = RoundedCornerShape(size = 10.dp),
                onValueChange = { newText ->
                    text1 = newText
                    if (text1.text.length >= maxChar){
                        Toast.makeText(localContext, "50자 이상 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    } },
                value = text1,
                placeholder = {
                    Text(
                        text = "대학교 동아리에서 다정하게 챙겨주는 모습을 보고 반해버렸습니다.",
                        style = getTextStyle(fontSize = 14, fontWeight = FontWeight.Medium, color = gray_7),
                        modifier = Modifier.fillMaxSize()) },
                singleLine = false,
                colors = TextFieldDefaults.colors(
                    cursorColor = highligtPurple,
                    focusedTextColor = gray_3,
                    focusedContainerColor = gray_9,
                    unfocusedContainerColor = gray_9,
                    disabledContainerColor = gray_9,
                    focusedIndicatorColor = transparent,
                    disabledIndicatorColor = transparent,
                    unfocusedIndicatorColor = transparent
                )
            )
        }

        Column(modifier = Modifier.padding(bottom = 32.dp)) {
            Row(modifier = Modifier.padding(bottom = 10.dp)) {
                Text(
                    text = "02",
                    style = getTextStyle(18, FontWeight.Bold, highligtPurple),
                    modifier = Modifier.padding(start = 4.dp, end = 10.dp)
                )
                Text(
                    text = "그 사람에 대한 현재 나의 감정은?",
                    style = getTextStyle(16, FontWeight.Medium, gray_3)
                )
            }

            var text2 by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp)
                    .background(color = transparent),
                textStyle = getTextStyle(fontSize = 14, fontWeight = FontWeight.Medium, color = gray_3),
                shape = RoundedCornerShape(size = 10.dp),
                onValueChange = { newText ->
                    text2 = newText
                    if (text2.text.length >= maxChar){
                        Toast.makeText(localContext, "50자 이상 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    } },
                value = text2,
                placeholder = {
                    Text(
                        text = "그 사람이 너무 좋아서 생각만 해도 심장이 빨리 뛰는것 같아요.",
                        style = getTextStyle(fontSize = 14, fontWeight = FontWeight.Medium, color = gray_7),
                        modifier = Modifier.fillMaxSize()) },
                singleLine = false,
                colors = TextFieldDefaults.colors(
                    cursorColor = highligtPurple,
                    focusedTextColor = gray_3,
                    focusedContainerColor = gray_9,
                    unfocusedContainerColor = gray_9,
                    disabledContainerColor = gray_9,
                    focusedIndicatorColor = transparent,
                    disabledIndicatorColor = transparent,
                    unfocusedIndicatorColor = transparent
                )
            )
        }

        Column(modifier = Modifier.padding(bottom = 32.dp)) {
            Row(modifier = Modifier.padding(bottom = 10.dp)) {
                Text(
                    text = "03",
                    style = getTextStyle(18, FontWeight.Bold, highligtPurple),
                    modifier = Modifier.padding(start = 4.dp, end = 10.dp)
                )
                Text(
                    text = "그 사람과 어떤 관계가 되고 싶나요?",
                    style = getTextStyle(16, FontWeight.Medium, gray_3)
                )
            }

            var text3 by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp)
                    .background(color = transparent),
                textStyle = getTextStyle(fontSize = 14, fontWeight = FontWeight.Medium, color = gray_3),
                shape = RoundedCornerShape(size = 10.dp),
                onValueChange = { newText ->
                    text3 = newText
                    if (text3.text.length >= maxChar){
                        Toast.makeText(localContext, "50자 이상 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    } },
                value = text3,
                placeholder = {
                    Text(
                        text =  "서로 죽고 못사는 애인 관계가 되고 싶어요.",
                        style = getTextStyle(fontSize = 14, fontWeight = FontWeight.Medium, color = gray_7),
                        modifier = Modifier.fillMaxSize()) },
                singleLine = false,
                colors = TextFieldDefaults.colors(
                    cursorColor = highligtPurple,
                    focusedTextColor = gray_3,
                    focusedContainerColor = gray_9,
                    unfocusedContainerColor = gray_9,
                    disabledContainerColor = gray_9,
                    focusedIndicatorColor = transparent,
                    disabledIndicatorColor = transparent,
                    unfocusedIndicatorColor = transparent
                )
            )
        }

        Button(
            onClick = {
                navController.navigate(ScreenEnum.PickTarotScreen.name) {
                    navController.graph.startDestinationRoute?.let {
                        popUpTo(it) { saveState = true }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                      },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 49.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = highligtPurple,
                contentColor = gray_1,
                disabledContainerColor = gray_5,
                disabledContentColor = gray_6)
        ) {
            Text(text = "다음", modifier = Modifier.padding(vertical = 8.dp))
        }
    }

}