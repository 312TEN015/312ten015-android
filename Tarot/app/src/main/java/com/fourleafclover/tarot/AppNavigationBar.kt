package com.fourleafclover.tarot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.data.TarotSubjectData
import com.fourleafclover.tarot.navigation.ScreenEnum
import com.fourleafclover.tarot.screen.CloseDialog
import com.fourleafclover.tarot.screen.CloseWithoutSaveDialog
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highligtPurple
import com.fourleafclover.tarot.ui.theme.white


val backgroundModifier = Modifier
    .background(color = gray_8)
    .fillMaxSize()

/* AppBar ---------------------------------------------------------------------------------- */

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
fun AppBarClose(navController: NavHostController,
                pickedTopicTemplate: TarotSubjectData,
                backgroundColor: Color
) {

    var openDialog by remember {
        mutableStateOf(false)
    }

    if (openDialog){
        Dialog(onDismissRequest = { openDialog = false }) {
            CloseDialog(onClickNo = { openDialog = false },
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
                    selectedContentColor = highligtPurple,
                    label = {
                        Text(text = item.title, style = getTextStyle(
                            fontSize = 12,
                            fontWeight = FontWeight.Normal,
                            color = if (currentRoute == item.screenName) highligtPurple else gray_6
                        )) },
                    unselectedContentColor = gray_6,
                    selected = currentRoute == item.screenName,
                    alwaysShowLabel = true,
                    onClick = {
                        navController.navigate(item.screenName) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
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
