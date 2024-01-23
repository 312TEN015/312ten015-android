
package com.fourleafclover.tarot.ui.screen

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.data.TarotInputDto
import com.fourleafclover.tarot.data.TarotSubjectData
import com.fourleafclover.tarot.utils.getPickedTopic
import com.fourleafclover.tarot.utils.getSubjectImoji
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.tarotInputDto
import com.fourleafclover.tarot.ui.component.AppBarClose
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.component.setStatusbarColor
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.fourleafclover.tarot.ui.navigation.navigateSaveState
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.transparent
import com.fourleafclover.tarot.ui.theme.white

val questionNumberModifier = Modifier
    .padding(end = 10.dp)
    .background(color = highlightPurple, shape = RoundedCornerShape(2.dp))
    .padding(start = 4.dp, end = 4.dp)

var text1 = mutableStateOf(TextFieldValue(""))
var text2 = mutableStateOf(TextFieldValue(""))
var text3 = mutableStateOf(TextFieldValue(""))

@Preview
@Composable
fun InputScreen(navController: NavHostController = rememberNavController()) {
    val localContext = LocalContext.current

    val pickedTopicTemplate by remember { mutableStateOf( getPickedTopic(pickedTopicNumber) ) }

    setStatusbarColor(LocalView.current, pickedTopicTemplate.primaryColor)

    Column(modifier = getBackgroundModifier(gray_9))
    {

        AppBarClose(navController = navController, pickedTopicTemplate = pickedTopicTemplate, backgroundColor = pickedTopicTemplate.primaryColor)

        Column(modifier = Modifier) {

            val numberOfQuestion = remember { 3 }

            var allFilled by remember { mutableStateOf(false) }

            allFilled = (text1.value.text.isNotBlank() && text2.value.text.isNotBlank() && text3.value.text.isNotBlank())

            LazyColumn(content = {
                // numberOfQuestion + header + footer
                items(numberOfQuestion + 2){
                    QuestionsComponent(allFilled, pickedTopicTemplate, it, navController, localContext)
                }

            })

        }
    }

}

@Composable
fun QuestionsComponent(allFilled: Boolean = false,
                       pickedTopicTemplate: TarotSubjectData = getPickedTopic(pickedTopicNumber),
                       idx: Int = 0,
                       navController: NavHostController = rememberNavController(),
                       context: Context
) {
    // header -----------------------------------------------------------

    if (idx == 0){
        Column(modifier = Modifier.padding(bottom = 40.dp)) {

            val imoji = getSubjectImoji(context, pickedTopicNumber)
            Text(
                text = "$imoji ${pickedTopicTemplate.majorQuestion}",
                style = getTextStyle(22, FontWeight.Bold, gray_9),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = pickedTopicTemplate.primaryColor)
                    .padding(top = 15.dp, bottom = 43.dp),
                textAlign = TextAlign.Center
            )

            Row(modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 12.dp).padding(horizontal = 20.dp)) {

                Text(
                    text = "마음속에 있는 고민거리를\n입력해보세요!",
                    style = getTextStyle(22, FontWeight.Medium, white),
                    modifier = Modifier.weight(1f).padding(top = 16.dp),
                )

                Image(painter = painterResource(id = when(pickedTopicNumber){
                    0 -> R.drawable.illust_heartkey
                    1 -> R.drawable.illust_study
                    2 -> R.drawable.illust_dream
                    3 -> R.drawable.illust_career
                    else -> {R.drawable.illust_heartkey
                    }
                }), contentDescription = null,
                    modifier = Modifier.width(83.dp).fillMaxHeight(),
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.CenterEnd)
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "TIP!",
                    style = getTextStyle(12, FontWeight.Normal, highlightPurple),
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )

                Text(
                    text = "구체적으로 입력할수록 더욱 상세한 결과를 받아볼 수 있어요.",
                    style = getTextStyle(12, FontWeight.Normal, gray_5),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        return
    }

    // footer ------------------------------------------------------------

    if (idx == 4){
        Button(
            onClick = {
                navigateSaveState(navController, ScreenEnum.PickTarotScreen.name)
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 72.dp, bottom = 44.dp)
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = highlightPurple,
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

        return
    }

    // body --------------------------------------------------------------

    var text by remember { mutableStateOf(TextFieldValue("")) }
    val maxChar = remember { 50 }

    Column(modifier = Modifier.padding(bottom = 32.dp).padding(horizontal = 20.dp)) {


        Column(modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)) {
            Text(
                text = "0${idx}",
                style = getTextStyle(12, FontWeight.Bold, gray_9),
                modifier = questionNumberModifier

            )
            Text(
                text = pickedTopicTemplate.subQuestions[idx-1],
                style = getTextStyle(18, FontWeight.SemiBold, gray_3),
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
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
                if (text.text.length >= maxChar){
                    Toast.makeText(context, "${maxChar}자 이상 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    text = newText
                    when (idx) {
                        1 -> text1.value = newText
                        2 -> text2.value = newText
                        3 -> text3.value = newText
                    }
                }
                            },
            value = text,
            placeholder = {
                Text(
                    text = pickedTopicTemplate.placeHolders[idx-1],
                    style = getTextStyle(fontSize = 14, fontWeight = FontWeight.Medium, color = gray_6),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)) },
            singleLine = false,
            colors = TextFieldDefaults.colors(
                cursorColor = highlightPurple,
                focusedTextColor = gray_3,
                focusedContainerColor = gray_8,
                unfocusedContainerColor = gray_8,
                disabledContainerColor = gray_8,
                focusedIndicatorColor = transparent,
                disabledIndicatorColor = transparent,
                unfocusedIndicatorColor = transparent
            )
        )
    }
}