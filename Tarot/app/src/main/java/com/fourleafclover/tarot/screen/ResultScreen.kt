@file:OptIn(ExperimentalFoundationApi::class)

package com.fourleafclover.tarot.screen

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.white
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.absoluteValue

@Preview
@Composable
fun ResultScreen(){
    val localContext = LocalContext.current

    Column {

        Column(
            modifier = Modifier
                .background(color = gray_8)
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "연애운",
                    style = getTextStyle(16, FontWeight.Medium, white),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center
                )

                Image(
                    painter = painterResource(id = R.drawable.close), contentDescription = "닫기버튼",
                    modifier = Modifier.fillMaxWidth(), alignment = Alignment.CenterEnd
                )
            }

            Text(
                text = "선택하신 카드는\n이런 의미를 담고 있어요.",
                style = getTextStyle(22, FontWeight.Medium, white),
                modifier = Modifier.padding(bottom = 32.dp)
            )


        }

        CardPagerLayout()
    }


}

// extension method for current page offset
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

fun Modifier.pagerFadeTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
        translationX = pageOffset * size.width
        alpha = 1- pageOffset.absoluteValue
    }

@Composable
fun CardPagerLayout() {
    val cardDrawableList = arrayListOf(R.drawable.card_1, R.drawable.card_2, R.drawable.card_3)

    val pagerState = rememberPagerState(pageCount = { Int.MAX_VALUE })
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    // 초기페이지 설정
    LaunchedEffect(key1 = Unit) {
        var initialPage = Int.MAX_VALUE / 2

//        // 초기페이지를 0으로 잡는다.
//        while (initialPage % bannerList.size != 0) {
//            initialPage++
//        }
        pagerState.scrollToPage(initialPage)
    }

    LaunchedEffect(key1 = isDragged) {

        snapshotFlow { isDragged }
            .collectLatest { isDragged ->
                if (isDragged) return@collectLatest
                while (true) {
                    delay(2_000)
                    // 일어날린 없지만 유저가 약 10억번 스크롤할지 몰라.. 하는 사람을 위해..
                    if (pagerState.currentPage + 1 in 0..Int.MAX_VALUE) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }

    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = gray_9)
            .padding(0.dp, 48.dp, 0.dp, 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            modifier = Modifier,
            state = pagerState
        ) { page ->
            Image(
                painter = painterResource(id = cardDrawableList[page % (cardDrawableList.size)]),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }

        DotsIndicator(
            totalDots = cardDrawableList.size,
            selectedIndex = if (isDragged) pagerState.currentPage % (cardDrawableList.size) else pagerState.targetPage % (cardDrawableList.size),
        )

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
            .padding(0.dp, 32.dp, 0.dp, 0.dp)
    ) {
        items(totalDots) { index ->
            Box(
                modifier = modifier
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(if (index == selectedIndex) selectedColor else unSelectedColor)
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}