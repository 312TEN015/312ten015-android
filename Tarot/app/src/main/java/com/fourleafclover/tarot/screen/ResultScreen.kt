@file:OptIn(ExperimentalFoundationApi::class)

package com.fourleafclover.tarot.screen

import android.graphics.drawable.GradientDrawable
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.interaction.collectIsDraggedAsState
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_2
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highligtPurple
import com.fourleafclover.tarot.ui.theme.white
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.absoluteValue


@Preview
@Composable
fun ResultScreen(navController: NavHostController = rememberNavController()){
    val localContext = LocalContext.current

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .background(color = gray_8)) {

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

        CustomSlider()



        Column(modifier = Modifier
            .background(color = gray_8)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)) {

            Text(text = "타로 카드 종합 리딩",
                style = getTextStyle(
                    fontSize = 26,
                    fontWeight = FontWeight.Medium,
                    color = highligtPurple
                ),
                modifier = Modifier.padding(top = 48.dp))

            Text(text = "당신에게는 어려운 상황에서도 포기하지 않고 끝까지 이겨내는 힘이 있습니다.",
                style = getTextStyle(
                    fontSize = 18,
                    fontWeight = FontWeight.Medium,
                    color = white
                ),
                textAlign = TextAlign.Center,
                lineHeight = 28.sp,
                modifier = Modifier.padding(top = 24.dp))

            Text(text = "당신에게는 어려운 상황에서도 포기하지 않고 끝까지 이겨내는 힘이 있습니다. 포기하지 않고 매달 100만원씩 적금을 들고 있는 것이 그 예시겠네요. 하지만, 때로는 지나친 의욕 때문에 무리한 목표를 세우다가 실패로 이어지는 경우도 있지요. " +
                    " 따라서, 지금까지의 생활 패턴을 유지하면서 안정감을 추구하려는 마음가짐이 중요합니다. 그리고, 다른 사람들을 위해 헌신하거나 배려하는 자세 역시 꼭 필요해요. 자신의 목표만 바라보기보다, 다른 사람에게 양보하는 마음가짐을 가지고 인생의 균형을 지켜가는 것이 중요합니다. " +
                    " 이러한 부분들이 잘 지켜진다면, 조만간 경제적으로 여유로워질 기회가 찾아올 것입니다. 30살이 되기 전에 1억을 모으는 것도 가능할지도 몰라요!",
                style = getTextStyle(
                    fontSize = 16,
                    fontWeight = FontWeight.Medium,
                    color = gray_3
                ),
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 64.dp)
                    .wrapContentHeight())

            var saveState by rememberSaveable { mutableStateOf(false) }

            Button(
                onClick = {
                    saveState = true
                },
                shape = RoundedCornerShape(10.dp),
                enabled = !saveState,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = highligtPurple,
                    contentColor = gray_1,
                    disabledContainerColor = gray_5,
                    disabledContentColor = gray_6)
            ) {
                Text(text = if (saveState) "저장 완료" else "타로 저장하기", modifier = Modifier.padding(vertical = 8.dp))
            }

            Button(
                onClick = {
                    navController.navigate(ScreenEnum.HomeScreen.name) {
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
                    .padding(bottom = 76.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = gray_9,
                    contentColor = gray_1,
                    disabledContainerColor = gray_5,
                    disabledContentColor = gray_6)
            ) {
                Text(text = "홈으로 돌아가기", modifier = Modifier.padding(vertical = 8.dp))
            }
        }


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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    sliderList: MutableList<Int> = mutableListOf(R.drawable.card_1, R.drawable.card_2, R.drawable.card_3),
    dotsActiveColor: Color = Color.DarkGray,
    dotsInActiveColor: Color = Color.LightGray,
    dotsSize: Dp = 10.dp,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 65.dp),
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 250.dp,
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        3
    }

    Column(
        modifier = Modifier.background(color = gray_9).padding(top = 48.dp),
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
                    val pageOffset =
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

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
            .padding(horizontal = 20.dp))
        {
            Text(text = "첫번째 카드",
                style = getTextStyle(
                    fontSize = 16,
                    fontWeight = FontWeight.Medium,
                    color = highligtPurple
                ),
                lineHeight = 28.sp,
                modifier = Modifier.padding(top = 12.dp, bottom = 48.dp))


            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {

                Text(text = "# Keyword",
                    style = getTextStyle(
                        fontSize = 12,
                        fontWeight = FontWeight.Medium,
                        color = gray_2
                    ),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(color = gray_8, shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp))

                Text(text = "# Keyword",
                    style = getTextStyle(
                        fontSize = 12,
                        fontWeight = FontWeight.Medium,
                        color = gray_2
                    ),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(color = gray_8, shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp))

                Text(text = "# Keyword",
                    style = getTextStyle(
                        fontSize = 12,
                        fontWeight = FontWeight.Medium,
                        color = gray_2
                    ),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(color = gray_8, shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp))
            }

            Text(text = "어려움이나 역경 등 힘든 상황에서도 포기하지 않고 끝까지 이겨내는 힘이 필요합니다.",
                style = getTextStyle(
                    fontSize = 16,
                    fontWeight = FontWeight.Medium,
                    color = gray_3
                ),
                lineHeight = 28.sp,
                modifier = Modifier.padding(top = 12.dp, bottom = 48.dp))
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
            .padding(vertical = 32.dp)
    ) {
        items(totalDots) { index ->
            Box(
                modifier = modifier
                    .width(if (index == selectedIndex) 18.dp else dotSize)
                    .height(6.dp)
                    .background(color = if (index == selectedIndex) selectedColor else unSelectedColor,
                        shape = RoundedCornerShape(4.dp)
                    )
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}