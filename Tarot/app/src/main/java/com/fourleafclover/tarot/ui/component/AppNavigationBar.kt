package com.fourleafclover.tarot.ui.component

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.data.TarotSubjectData
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.fourleafclover.tarot.ui.screen.findActivity
import com.fourleafclover.tarot.ui.screen.harmony.ResultViewModel
import com.fourleafclover.tarot.ui.theme.backgroundColor_1
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getPickedTopic
import com.fourleafclover.tarot.utils.getTarotRequest


val backgroundModifier = Modifier
    .background(color = backgroundColor_1)
    .fillMaxSize()

fun getBackgroundModifier(color: Color): Modifier = Modifier
    .background(color = color)
    .fillMaxSize()

fun setStatusbarColor(view: View, color: Color) {
    val window = (view.context as Activity).window
    window.statusBarColor = color.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
        (color != backgroundColor_1 && color != backgroundColor_2)
    if (color == backgroundColor_1 || color == backgroundColor_2) {
        window.navigationBarColor = color.toArgb()
    }
}

fun setStatusbarColor(localContext: Context, view: View, color: Color) {
    val window = localContext.findActivity()?.window ?: return
    window.statusBarColor = color.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
        (color != backgroundColor_1 && color != backgroundColor_2)
    if (color == backgroundColor_1 || color == backgroundColor_2) {
        window.navigationBarColor = color.toArgb()
    }
}

/* AppBar ---------------------------------------------------------------------------------- */

val appBarModifier = Modifier
    .height(48.dp)
    .fillMaxWidth()

@Composable
@Preview
fun AppBarPlain(
    navController: NavHostController = rememberNavController(),
    title: String = "MY 타로",
    backgroundColor: Color = backgroundColor_1,
    backButtonVisible: Boolean = true,
    backButtonResource: Int = R.drawable.arrow_left
) {
    Box(
        modifier = appBarModifier
            .background(color = backgroundColor)
            .padding(top = 10.dp, bottom = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = getTextStyle(
                16,
                FontWeight.Medium,
                if (backgroundColor == backgroundColor_1 || backgroundColor == backgroundColor_2) white else gray_9
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )

        if (backButtonVisible) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterStart)
                    .clickable {
                        // 뒤로가기
                        navController.popBackStack()
                    }
            ) {
                Image(
                    painter = painterResource(id = backButtonResource),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 20.dp),
                    alignment = Alignment.CenterStart
                )
            }
        }
    }
}

@Composable
@Preview
fun AppBarClose(
    navController: NavHostController = rememberNavController(),
    pickedTopicTemplate: TarotSubjectData = getPickedTopic(0),
    backgroundColor: Color = backgroundColor_1,
    isTitleVisible: Boolean = true
) {

    var openDialog by remember {
        mutableStateOf(false)
    }

    if (openDialog) {
        Dialog(onDismissRequest = { openDialog = false }) {
            CloseDialog(onClickNo = { openDialog = false },
                onClickOk = {
                    navigateInclusive(navController, ScreenEnum.HomeScreen.name)
                })
        }
    }

    Box(
        modifier = appBarModifier
            .background(color = backgroundColor)
            .padding(top = 10.dp, bottom = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isTitleVisible) pickedTopicTemplate.majorTopic else "",
            style = getTextStyle(
                16,
                FontWeight.Medium,
                if (backgroundColor == backgroundColor_1 || backgroundColor == backgroundColor_2) white else gray_9
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
        ) {
            Image(
                painter = painterResource(id = if (backgroundColor == backgroundColor_1 || backgroundColor == backgroundColor_2) R.drawable.cancel else R.drawable.cancel_black),
                contentDescription = "닫기버튼",
                modifier = Modifier
                    .clickable { openDialog = true }
                    .size(28.dp),
                alignment = Alignment.Center
            )
        }
    }
}

@Composable
@Preview
fun AppBarCloseTarotResult(
    navController: NavHostController = rememberNavController(),
    pickedTopicTemplate: TarotSubjectData = getPickedTopic(0),
    backgroundColor: Color = backgroundColor_1,
    isTitleVisible: Boolean = true,
    resultViewModel: ResultViewModel = remember { ResultViewModel() }
) {


    Box(
        modifier = appBarModifier
            .background(color = backgroundColor)
            .padding(top = 10.dp, bottom = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isTitleVisible) pickedTopicTemplate.majorTopic else "",
            style = getTextStyle(
                16,
                FontWeight.Medium,
                if (backgroundColor == backgroundColor_1 || backgroundColor == backgroundColor_2) white else gray_9
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
        ) {
            Image(
                painter = painterResource(id = if (backgroundColor == backgroundColor_1 || backgroundColor == backgroundColor_2) R.drawable.cancel else R.drawable.cancel_black),
                contentDescription = "닫기버튼",
                modifier = Modifier
                    .clickable { resultViewModel.openCloseDialog() }
                    .size(28.dp),
                alignment = Alignment.Center
            )
        }
    }
}

@Composable
fun OpenCloseDialog(
    navController: NavHostController,
    resultViewModel: ResultViewModel = remember { ResultViewModel() }
) {
    // 닫기 버튼 눌렀을 때 && 타로 결과 저장 안한 경우
    if (resultViewModel.isCloseDialogOpen() && !resultViewModel.isSaved()) {
        Dialog(onDismissRequest = { resultViewModel.closeCloseDialog() }) {
            CloseWithoutSaveDialog(onClickNo = { resultViewModel.closeCloseDialog() },
                onClickOk = {
                    navigateInclusive(navController, ScreenEnum.HomeScreen.name)
                })
        }
    }
    // 닫기 버튼 눌렀을 때 && 타로 결과 저장 한 경우
    else if (resultViewModel.isCloseDialogOpen() && resultViewModel.isSaved()) {
        Dialog(onDismissRequest = { resultViewModel.closeCloseDialog() }) {
            CloseDialog(onClickNo = { resultViewModel.closeCloseDialog() },
                onClickOk = {
                    navigateInclusive(navController, ScreenEnum.HomeScreen.name)
                })
        }
    }

}

@Composable
fun OpenCompleteDialog(resultViewModel: ResultViewModel = remember { ResultViewModel() }) {
    // 타로 결과 저장 버튼 눌렀을 때
    if (resultViewModel.isCompleteDialogOpen()) {
        Dialog(onDismissRequest = { resultViewModel.closeCompleteDialog() }) {
            SaveCompletedDialog(onClickOk = { resultViewModel.closeCompleteDialog() })
        }
    }
}

@Composable
fun ControlDialog(
    navController: NavHostController,
    resultViewModel: ResultViewModel = remember { ResultViewModel() }
) {
    BackHandler { resultViewModel.closeCloseDialog() }
    OpenCloseDialog(navController = navController)
    OpenCompleteDialog()

}


/* BottomMenu ---------------------------------------------------------------------------------- */



@Composable
fun BottomNavigationBar(navController: NavHostController = rememberNavController()) {
    val localContext = LocalContext.current
    val items = listOf<BottomNavItem>(
        BottomNavItem.Home,
        BottomNavItem.MyTarot
    )
    Column {
        Divider(
            color = gray_8,
            thickness = 1.dp,
            modifier = Modifier
        )

        BottomNavigation(
            backgroundColor = gray_9
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(
                                id = if (currentRoute == item.screenName) item.selectedIcon
                                else item.unselectedIcon
                            ),
                            contentDescription = item.title,
                            modifier = Modifier
                                .width(26.dp)
                                .height(26.dp)
                        )
                    },
                    selectedContentColor = highlightPurple,
                    label = {
                        Text(
                            text = item.title, style = getTextStyle(
                                fontSize = 12,
                                fontWeight = FontWeight.Normal,
                                color = if (currentRoute == item.screenName) highlightPurple else gray_6
                            )
                        )
                    },
                    unselectedContentColor = gray_6,
                    selected = currentRoute == item.screenName,
                    alwaysShowLabel = true,
                    onClick = {
                        if (item.screenName == ScreenEnum.MyTarotScreen.name) {
                            val tarotResultArray = MyApplication.prefs.getTarotResultArray()
                            getTarotRequest(localContext, tarotResultArray)
                        }
                        navigateInclusive(navController, item.screenName)
                    }
                )
            }
        }
    }
}

sealed class BottomNavItem(
    val title: String, val selectedIcon: Int, val unselectedIcon: Int, val screenName: String
) {
    object Home : BottomNavItem(
        "홈",
        R.drawable.home_filled,
        R.drawable.home_lined,
        ScreenEnum.HomeScreen.name
    )

    object MyTarot : BottomNavItem(
        "MY",
        R.drawable.my_filled,
        R.drawable.my_lined,
        ScreenEnum.MyTarotScreen.name
    )
}
