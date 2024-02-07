package com.fourleafclover.tarot.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.sharedTarotResult
import com.fourleafclover.tarot.ui.component.AppBarPlain
import com.fourleafclover.tarot.ui.component.CardSlider
import com.fourleafclover.tarot.ui.component.backgroundModifier
import com.fourleafclover.tarot.ui.component.setStatusbarColor
import com.fourleafclover.tarot.ui.theme.backgroundColor_1
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_2
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_4
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getPickedTopic
import com.fourleafclover.tarot.utils.getSubjectImoji

@Composable
@Preview
fun ShareDetailScreen(navController: NavHostController = rememberNavController()){
    val localContext = LocalContext.current
    val tarotSubjectData = getPickedTopic(sharedTarotResult.tarotType)
    setStatusbarColor(LocalView.current, backgroundColor_1)

    Column(modifier = backgroundModifier)
    {

        AppBarPlain(navController = navController, title = "공유하기", backgroundColor = backgroundColor_1, backButtonVisible = false)

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
        {
            Column(
                modifier = Modifier
                    .background(color = gray_8)
                    .padding(top = 32.dp)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tarotSubjectData.majorTopic,
                    style = getTextStyle(16, FontWeight.Medium, gray_2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )

                val imoji = getSubjectImoji(localContext, sharedTarotResult.tarotType)
                Text(
                    text = "$imoji ${tarotSubjectData.majorQuestion} $imoji",
                    style = getTextStyle(22, FontWeight.Bold, gray_2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = sharedTarotResult.createdAt,
                    style = getTextStyle(14, FontWeight.Medium, gray_4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    textAlign = TextAlign.Center
                )
            }

            CardSlider(tarotResult = sharedTarotResult, localContext = localContext)

            Column(
                modifier = Modifier
                    .background(color = gray_8)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {

                Text(
                    text = "타로 카드 종합 리딩",
                    style = getTextStyle(
                        fontSize = 26,
                        fontWeight = FontWeight.Medium,
                        color = highlightPurple
                    ),
                    modifier = Modifier.padding(top = 48.dp)
                )

                Text(
                    text = sharedTarotResult.overallResult?.summary.toString(),
                    style = getTextStyle(
                        fontSize = 18,
                        fontWeight = FontWeight.Medium,
                        color = white
                    ),
                    lineHeight = 28.sp,
                    modifier = Modifier.padding(top = 24.dp)
                )

                Text(
                    text = sharedTarotResult.overallResult?.full.toString(),
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
            }
        }
    }

}
