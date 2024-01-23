package com.fourleafclover.tarot.ui.component

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
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
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.data.TarotSubjectData
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.fourleafclover.tarot.ui.theme.backgroundColor_1
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getPickedTopic


val backgroundModifier = Modifier
    .background(color = backgroundColor_1)
    .fillMaxSize()

fun getBackgroundModifier(color: Color): Modifier = Modifier
    .background(color = color)
    .fillMaxSize()

fun setStatusbarColor(view: View, color: Color){
    val window = (view.context as Activity).window
    window.statusBarColor = color.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = (color != backgroundColor_1 && color != backgroundColor_2)
}

/* AppBar ---------------------------------------------------------------------------------- */

val appBarModifier = Modifier
    .height(48.dp)
    .fillMaxWidth()

@Composable
@Preview
fun AppBarPlain(navController: NavHostController = rememberNavController(),
                title: String = "MY 타로",
                backgroundColor: Color = backgroundColor_1
) {
    Box(modifier = appBarModifier.background(color = backgroundColor), contentAlignment = Alignment.Center
    ) {
        Text(
            text = "MY 타로",
            style = getTextStyle(16, FontWeight.Medium, if (backgroundColor == backgroundColor_1 || backgroundColor == backgroundColor_2) white else gray_9),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AppBarCloseWithoutSave(navController: NavHostController,
                pickedTopicTemplate: TarotSubjectData,
                backgroundColor: Color
) {

    var openDialog by remember {
        mutableStateOf(false)
    }

    if (openDialog){
        Dialog(onDismissRequest = { openDialog = false }) {
            CloseWithoutSaveDialog(onClickNo = { openDialog = false },
                onClickOk = {
                    // go to home with clear back stack
                    navController.navigate(ScreenEnum.HomeScreen.name) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {  inclusive = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }

    Box(modifier = Modifier.background(color = backgroundColor)) {

        Box(
            modifier = Modifier
                .padding(top = 28.dp, bottom = 10.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = pickedTopicTemplate.majorTopic,
                style = getTextStyle(16, FontWeight.Medium, white),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center
            )

            Image(painter = painterResource(id = R.drawable.close), contentDescription = "닫기버튼",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp)
                    .clickable { openDialog = true }, alignment = Alignment.CenterEnd
            )
        }
    }
}

@Composable
@Preview
fun AppBarClose(navController: NavHostController = rememberNavController(),
                pickedTopicTemplate: TarotSubjectData = getPickedTopic(0),
                backgroundColor: Color = backgroundColor_1
) {

    var openDialog by remember {
        mutableStateOf(false)
    }

    if (openDialog){
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
            text = pickedTopicTemplate.majorTopic,
            style = getTextStyle(16, FontWeight.Medium, if (backgroundColor == backgroundColor_1 || backgroundColor == backgroundColor_2) white else gray_9),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = if (backgroundColor == backgroundColor_1 || backgroundColor == backgroundColor_2) R.drawable.cancel else R.drawable.cancel_black),
            contentDescription = "닫기버튼",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { openDialog = true }
                .padding(end = 20.dp)
                .size(28.dp),
            alignment = Alignment.Center
        )
    }
}


/* BottomMenu ---------------------------------------------------------------------------------- */



@Composable
fun BottomNavigationBar(navController: NavHostController = rememberNavController()) {
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
                                else item.unselectedIcon),
                            contentDescription = item.title,
                            modifier = Modifier
                                .width(26.dp)
                                .height(26.dp)
                        )
                    },
                    selectedContentColor = highlightPurple,
                    label = {
                        Text(text = item.title, style = getTextStyle(
                            fontSize = 12,
                            fontWeight = FontWeight.Normal,
                            color = if (currentRoute == item.screenName) highlightPurple else gray_6
                        )) },
                    unselectedContentColor = gray_6,
                    selected = currentRoute == item.screenName,
                    alwaysShowLabel = true,
                    onClick = {
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
    object Home : BottomNavItem("홈", R.drawable.home_filled, R.drawable.home_lined, ScreenEnum.HomeScreen.name)
    object MyTarot : BottomNavItem("MY", R.drawable.my_filled, R.drawable.my_lined, ScreenEnum.MyTarotScreen.name)
}
