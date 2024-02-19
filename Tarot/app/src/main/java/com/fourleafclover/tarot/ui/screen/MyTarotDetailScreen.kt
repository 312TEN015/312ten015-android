package com.fourleafclover.tarot.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.selectedTarotResult
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
import com.fourleafclover.tarot.utils.setDynamicLink

@Composable
@Preview
fun MyTarotDetailScreen(navController: NavHostController = rememberNavController()){
    val localContext = LocalContext.current
    val tarotSubjectData = getPickedTopic(selectedTarotResult.tarotType)
    setStatusbarColor(LocalView.current, backgroundColor_1)

    Column(modifier = backgroundModifier)
    {

        AppBarPlain(navController = navController, title = "MY 타로", backgroundColor = backgroundColor_1, backButtonVisible = true)

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

                val imoji = getSubjectImoji(localContext, selectedTarotResult.tarotType)
                Text(
                    text = "$imoji ${tarotSubjectData.majorQuestion} $imoji",
                    style = getTextStyle(22, FontWeight.Bold, gray_2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = selectedTarotResult.createdAt,
                    style = getTextStyle(14, FontWeight.Medium, gray_4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    textAlign = TextAlign.Center
                )
            }


            CardSlider(tarotResult = selectedTarotResult, localContext = localContext)

            Column(
                modifier = Modifier
                    .background(color = gray_8)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

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
                    text = selectedTarotResult.overallResult?.summary.toString(),
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
                    text = selectedTarotResult.overallResult?.full.toString(),
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

                Row(modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .padding(bottom = 45.dp)
                    .clickable {
                        setDynamicLink(localContext, selectedTarotResult.tarotId)
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
    }

}
