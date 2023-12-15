package com.fourleafclover.tarot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highligtPurple

/* AppBar ---------------------------------------------------------------------------------- */

@Composable
fun AppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "뒤로가기"
                    )
                }
            }
        }
    )
}


/* BottomMenu ---------------------------------------------------------------------------------- */


@Preview
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
                            painter = painterResource(id = item.icon),
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
    val title: String, val icon: Int, val screenName: String
) {
    object Home : BottomNavItem("홈", R.drawable.home_fill, ScreenEnum.HomeScreen.name)
    object MyTarot : BottomNavItem("MY 타로", R.drawable.my_lined, ScreenEnum.MyTarotScreen.name)
}
