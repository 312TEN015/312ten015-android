
package com.fourleafclover.tarot.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.utils.getPickedTopic
import com.fourleafclover.tarot.utils.getSubjectImoji
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.tarotInputDto
import com.fourleafclover.tarot.ui.component.AppBarClose
import com.fourleafclover.tarot.ui.component.backgroundModifier
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_2
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highligtPurple
import com.fourleafclover.tarot.ui.theme.transparent
import com.fourleafclover.tarot.ui.theme.white

@Preview
@Composable
fun InputScreen(navController: NavHostController = rememberNavController()) {
    val localContext = LocalContext.current

    val pickedTopicTemplate = getPickedTopic(pickedTopicNumber)

    var text1 by remember { mutableStateOf(TextFieldValue("")) }
    var text2 by remember { mutableStateOf(TextFieldValue("")) }
    var text3 by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = backgroundModifier)
    {

        AppBarClose(navController = navController, pickedTopicTemplate = pickedTopicTemplate, gray_9)

        val imoji = getSubjectImoji(localContext, pickedTopicNumber)
        Text(
            text = "$imoji ${pickedTopicTemplate.majorQuestion} $imoji",
            style = getTextStyle(22, FontWeight.Bold, gray_2),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = gray_9)
                .padding(top = 16.dp, bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        Column(modifier = Modifier
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "마음속에 있는 고민거리를\n입력해보세요!",
                style = getTextStyle(22, FontWeight.Medium, white),
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "TIP!",
                    style = getTextStyle(12, FontWeight.Normal, highligtPurple),
                    modifier = Modifier
                        .background(color = gray_7, shape = RoundedCornerShape(2.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )

                Text(
                    text = "구체적으로 입력할수록 더욱 상세한 결과를 받아볼 수 있어요.",
                    style = getTextStyle(12, FontWeight.Normal, gray_5),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Divider(
                color = gray_7,
                thickness = 1.dp,
                modifier = Modifier.padding(0.dp, 21.dp, 0.dp, 24.dp)
            )

            val maxChar = 50

            var allFilled by remember { mutableStateOf(false) }

            allFilled = (text1.text.isNotBlank() && text2.text.isNotBlank() && text3.text.isNotBlank())

            Column(modifier = Modifier.padding(bottom = 32.dp)) {
                Row(modifier = Modifier
                    .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "01",
                        style = getTextStyle(18, FontWeight.Bold, highligtPurple),
                        modifier = Modifier.padding(start = 4.dp, end = 10.dp)
                    )
                    Text(
                        text = pickedTopicTemplate.subQuestions[0],
                        style = getTextStyle(16, FontWeight.Normal, gray_3)
                    )
                }


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
                            text = pickedTopicTemplate.placeHolders[0],
                            style = getTextStyle(fontSize = 14, fontWeight = FontWeight.Medium, color = gray_7),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp)) },
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
                        text = pickedTopicTemplate.subQuestions[1],
                        style = getTextStyle(16, FontWeight.Medium, gray_3)
                    )
                }

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
                            text = pickedTopicTemplate.placeHolders[1],
                            style = getTextStyle(fontSize = 14, fontWeight = FontWeight.Medium, color = gray_7),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp)) },
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

            Column(modifier = Modifier.padding(bottom = 64.dp)) {
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(
                        text = "03",
                        style = getTextStyle(18, FontWeight.Bold, highligtPurple),
                        modifier = Modifier.padding(start = 4.dp, end = 10.dp)
                    )
                    Text(
                        text = pickedTopicTemplate.subQuestions[2],
                        style = getTextStyle(16, FontWeight.Medium, gray_3)
                    )
                }

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
                            text = pickedTopicTemplate.placeHolders[2],
                            style = getTextStyle(fontSize = 14, fontWeight = FontWeight.Medium, color = gray_7),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp)) },
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
                    tarotInputDto.firstAnswer = text1.text
                    tarotInputDto.secondAnswer = text2.text
                    tarotInputDto.thirdAnswer = text3.text

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
                    .padding(bottom = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = highligtPurple,
                    contentColor = gray_1,
                    disabledContainerColor = gray_6,
                    disabledContentColor = gray_5),
                enabled = allFilled
            ) {
                Text(text = "다음",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = getTextStyle(
                        fontSize = 16,
                        fontWeight = FontWeight.Medium,
                        color = if (allFilled) white else gray_5
                    ))
            }
        }
    }

}