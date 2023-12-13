
package com.fourleafclover.tarot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.white

@Preview
@Composable
fun PickTarotScreen(navController: NavHostController = rememberNavController()) {
    val localContext = LocalContext.current

    Column(modifier = Modifier
        .background(color = gray_8)
        .padding(horizontal = 24.dp, vertical = 24.dp)
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

        }

        Box {

        }

    }
}
