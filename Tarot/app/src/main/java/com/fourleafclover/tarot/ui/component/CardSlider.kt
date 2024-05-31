package com.fourleafclover.tarot.ui.component

import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.ui.theme.TextB02M16
import com.fourleafclover.tarot.ui.theme.TextB04M12
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_2
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getCardImageId
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardSlider(
    modifier: Modifier = Modifier,
    tarotResult: TarotOutputDto,
    localContext: Context
) {

    val sliderList: MutableList<Int> = arrayListOf(0, 0, 0)
    for ((idx, value) in tarotResult.cards.withIndex()) {
        sliderList[idx] = getCardImageId(localContext, value.toString())
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        3
    }

    Column(
        modifier = Modifier
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            val itemWidth by remember { mutableStateOf(140.dp) }
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val horizontalPadding by remember { mutableStateOf(screenWidth / 2 - itemWidth / 2) }

            HorizontalPager(
                modifier = modifier.weight(1f),
                state = pagerState,
                pageSpacing = 12.dp,
                userScrollEnabled = true,
                reverseLayout = false,
                contentPadding = PaddingValues(horizontal = horizontalPadding),
                beyondBoundsPageCount = 0,
                pageSize = PageSize.Fixed(itemWidth),
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
                                .width(itemWidth)
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
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            TextB02M16(text = if (pagerState.currentPage == 0) "첫번째 카드"
            else if(pagerState.currentPage == 1) "두번째 카드"
            else "세번째 카드",
                color = highlightPurple,
                modifier = Modifier.padding(bottom = 12.dp))


            LazyRow(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {

                items(3) {

                    TextB04M12(
                        text = "# ${tarotResult.cardResults?.get(pagerState.currentPage)?.keywords?.get(it)}",
                        color = gray_2,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(color = gray_8, shape = RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                }
            }

            TextB02M16(text = "${tarotResult.cardResults?.get(pagerState.currentPage)?.description }",
                color = gray_3,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Visible,
                modifier = Modifier.padding(top = 12.dp).height(56.dp))
        }
    }
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = white,
    unSelectedColor: Color = gray_6,
    dotSize: Dp = 6.dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(top = 32.dp, bottom = 40.dp)
    ) {
        items(totalDots) { index ->
            Box(
                modifier = modifier
                    .width(if (index == selectedIndex) 18.dp else dotSize)
                    .height(dotSize)
                    .background(
                        color = if (index == selectedIndex) selectedColor else unSelectedColor,
                        shape = RoundedCornerShape(4.dp)
                    )
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}

