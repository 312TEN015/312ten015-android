package com.fourleafclover.tarot.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.ui.component.DotsIndicator
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highlightPurple

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun PagerOnBoarding(navController: NavHostController = rememberNavController()) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { 3 }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = gray_9),
        horizontalAlignment = Alignment.CenterHorizontally) {

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
            pageSpacing = 0.dp,
            userScrollEnabled = true,
            reverseLayout = false,
            beyondBoundsPageCount = 0,
            pageSize = PageSize.Fill,
            key = null,
            pageContent = { page ->

                OnBoardPage(page)

            }
        )

        DotsIndicator(
            totalDots = onboardPagesList.size,
            selectedIndex = pagerState.currentPage
        )

        Button(
            onClick = {
                navigateInclusive(navController, ScreenEnum.HomeScreen.name)
                MyApplication.prefs.setOnBoardingComplete()
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 49.dp, end = 20.dp, start = 20.dp)
                .alpha(if (pagerState.currentPage == onboardPagesList.size - 1) 1f else 0f),
            colors = ButtonDefaults.buttonColors(
                containerColor = highlightPurple,
                contentColor = gray_1,
                disabledContainerColor = gray_5,
                disabledContentColor = gray_6
            ),
            enabled = pagerState.currentPage == onboardPagesList.size-1
        ) {
            Text(text = "시작하기",
                modifier = Modifier.padding(vertical = 8.dp),
                style = getTextStyle(
                    fontSize = 16,
                    fontWeight = FontWeight.Medium
                ))
        }



    }


}

@Composable
fun OnBoardPage(page: Int = 0){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = gray_9)
        .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            modifier = Modifier.padding(top = 80.dp, bottom = 8.dp),
            text = onboardPagesList[page].title,
            style = getTextStyle(fontSize = 26, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.padding(bottom = 48.dp),
            text = onboardPagesList[page].description,
            style = getTextStyle(fontSize = 16, fontWeight = FontWeight.Bold, color = gray_3),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = onboardPagesList[page].imageRes),
            contentDescription = null,
            modifier = Modifier.wrapContentSize()
        )
    }
}

data class OnboardPage(
    val imageRes: Int, val title: String, val description: String
)

val onboardPagesList = listOf(
    OnboardPage(
        imageRes = R.drawable.illust_onboarding_tarot,
        title = "타로 카드로\n운세를 확인해보세요!",
        description = "오늘의 운세부터 주제별 운세까지\n다양한 운세를 점쳐볼 수 있어요."
    ), OnboardPage(
        imageRes = R.drawable.illust_onboarding_compass,
        title = "마음 속 고민거리를\n해결해 드려요.",
        description = "명확한 해결책이 필요하신가요?\n타로포유가 길잡이가 되어드릴게요."
    ), OnboardPage(
        imageRes = R.drawable.illust_onboarding_clover,
        title = "타로포유로\n행운을 잡아보세요.",
        description = "타로포유와 함께 인생 속\n크고 작은 행운을 찾아보세요!"
    )
)