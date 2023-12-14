
package com.fourleafclover.tarot.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.highligtPurple
import com.fourleafclover.tarot.ui.theme.white

@Preview
@Composable
fun PickTarotScreen(navController: NavHostController = rememberNavController()) {
    val localContext = LocalContext.current

    Column(modifier = Modifier
        .background(color = gray_8)
        .padding(horizontal = 20.dp, vertical = 20.dp)
        .fillMaxSize()) {

        Box(modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "연애운",
                style = getTextStyle(16, FontWeight.Medium, white),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center
            )

            Image(painter = painterResource(id = R.drawable.close), contentDescription = "닫기버튼",
                modifier = Modifier.fillMaxWidth(), alignment = Alignment.CenterEnd)
        }

        Text(
            text = "첫 번째 카드를 골라주세요.",
            style = getTextStyle(22, FontWeight.Medium, white),
            modifier = Modifier.fillMaxWidth()
        )

        Row {

            val dash = Stroke(width = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)))
            Canvas(Modifier.height(70.dp)){
                drawRoundRect(color = gray_6, style = dash)
            }


        }

        Box {

        }

        Button(
            onClick = {
                navController.navigate(ScreenEnum.ResultScreen.name) {
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
                .padding(bottom = 49.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = highligtPurple,
                contentColor = gray_1,
                disabledContainerColor = gray_5,
                disabledContentColor = gray_6
            )
        ) {
            Text(text = "다음", modifier = Modifier.padding(vertical = 8.dp))
        }

    }
}
