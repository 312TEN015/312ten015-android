package com.fourleafclover.tarot.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.AppBarClose
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.backgroundModifier
import com.fourleafclover.tarot.data.getCardImageId
import com.fourleafclover.tarot.data.getPickedTopic
import com.fourleafclover.tarot.data.getSubjectImoji
import com.fourleafclover.tarot.data.selectedTarotResult
import com.fourleafclover.tarot.data.tarotOutputDto
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_2
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_4
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highligtPurple
import com.fourleafclover.tarot.ui.theme.white
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.absoluteValue

@Composable
@Preview
fun MyTarotDetailScreen(navController: NavHostController = rememberNavController()){
    val localContext = LocalContext.current
    Log.d("", "${selectedTarotResult.overallResult?.full }")
    val tarotSubjectData = getPickedTopic(selectedTarotResult.tarotType)

    Column(modifier = backgroundModifier)
    {

        AppBarClose(
            navController = navController,
            pickedTopicTemplate = tarotSubjectData,
            gray_9
        )

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
        {


            val imoji = getSubjectImoji(localContext, selectedTarotResult.tarotType)
            Text(
                text = "$imoji ${tarotSubjectData.majorQuestion} $imoji",
                style = getTextStyle(22, FontWeight.Bold, gray_2),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = gray_9)
                    .padding(top = 16.dp, bottom = 16.dp),
                textAlign = TextAlign.Center
            )


//        val date = Date(selectedTarotResult.createdAt)
//        val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
//        val simpleDate: String = simpleDateFormat.format(date)
//        val simpleDateParse: Date = simpleDateFormat.parse(simpleDate)

            Text(
                text = selectedTarotResult.createdAt,
                style = getTextStyle(14, FontWeight.Medium, gray_4),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = gray_9)
                    .padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )

            //////
            val tmpList = arrayListOf<Int>()
            for (i in selectedTarotResult.cards) {
                tmpList.add(getCardImageId(localContext, i.toString()))
            }

            CustomMySlider(sliderList = tmpList)

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
                        color = highligtPurple
                    ),
                    modifier = Modifier.padding(top = 48.dp)
                )

                Text(
                    text = selectedTarotResult.overallResult?.summary.toString(),
                    style = getTextStyle(
                        fontSize = 18,
                        fontWeight = FontWeight.Medium,
                        color = white
                    ),
                    lineHeight = 28.sp,
                    modifier = Modifier.padding(top = 24.dp)
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
            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomMySlider(
    modifier: Modifier = Modifier,
    sliderList: MutableList<Int> = mutableListOf(R.drawable.card_1, R.drawable.card_2, R.drawable.card_3)
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        3
    }

    Column(
        modifier = Modifier
            .background(color = gray_9)
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            HorizontalPager(
                modifier = modifier.weight(1f),
                state = pagerState,
                pageSpacing = 0.dp,
                userScrollEnabled = true,
                reverseLayout = false,
                contentPadding = PaddingValues(horizontal = 100.dp),
                beyondBoundsPageCount = 0,
                pageSize = PageSize.Fill,
                key = null,
                pageContent = { page ->
                    val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                    val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)

                    Box(modifier = modifier
                        .graphicsLayer {
                            scaleX = scaleFactor
                            scaleY = scaleFactor
                        }
                        .alpha(
                            scaleFactor.coerceIn(0f, 1f)
                        )) {

                        val painter = painterResource(id = sliderList[page])
                        val imageRatio = painter.intrinsicSize.width / painter.intrinsicSize.height
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .width(140.dp)
                                .aspectRatio(imageRatio)
                        )
                    }

                }
            )

        }

        DotsIndicator(
            totalDots = sliderList.size,
            selectedIndex = pagerState.currentPage
        )

        Column(modifier = Modifier
            .background(color = gray_9)
            .fillMaxSize()
            .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = if (pagerState.currentPage == 0) "첫번째 카드"
            else if(pagerState.currentPage == 1) "두번째 카드"
            else "세번째 카드",
                style = getTextStyle(
                    fontSize = 16,
                    fontWeight = FontWeight.Medium,
                    color = highligtPurple
                ),
                lineHeight = 28.sp,
                modifier = Modifier.padding(bottom = 12.dp))


            LazyRow(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {

                items(3) {

                    Text(
                        text = "# ${selectedTarotResult.cardResults?.get(pagerState.currentPage)?.keywords?.get(it)}",
                        style = getTextStyle(
                            fontSize = 12,
                            fontWeight = FontWeight.Medium,
                            color = gray_2
                        ),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(color = gray_8, shape = RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                }
            }

            Text(text = "${selectedTarotResult.cardResults?.get(pagerState.currentPage)?.description }",
                style = getTextStyle(
                    fontSize = 16,
                    fontWeight = FontWeight.Medium,
                    color = gray_3
                ),
                textAlign = TextAlign.Center,
                lineHeight = 28.sp,
                modifier = Modifier.padding(top = 12.dp, bottom = 48.dp))
        }
    }
}
