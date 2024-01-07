package com.fourleafclover.tarot.ui.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.utils.getPath
import com.fourleafclover.tarot.tarotInputDto
import com.fourleafclover.tarot.tarotOutputDto
import com.fourleafclover.tarot.utils.tarotService
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_8
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
@Preview
fun LoadingScreen(navController: NavController = rememberNavController()){
    val localContext = LocalContext.current

    var send by remember { mutableStateOf(false) }

    // 요청을 한번만 보내도록 함
    if (!send){
        sendRequest(localContext, navController)
        send = true
    }

    // 로딩 화면 회전
    var currentRotation by remember { mutableStateOf(0f) }

    val rotation = remember("rotate") { Animatable(currentRotation) }

    LaunchedEffect("rotate") {
        rotation.animateTo(
            targetValue = currentRotation + 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        ) {
            currentRotation = value
        }
    }



    Column(modifier = Modifier
        .fillMaxSize()
        .background(gray_8),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.nonicons_loading_16),
            contentDescription ="",
            modifier = Modifier
                .wrapContentSize()
                .rotate(rotation.value)
        )
        Text(text = "선택하신 카드의 의미를\n열심히 해석하고 있어요!",
            style = getTextStyle(
            fontSize = 22,
            fontWeight = FontWeight.Medium
        ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp, top = 40.dp))
        Text(text = "잠시만 기다려주세요",
            style = getTextStyle(
            fontSize = 14,
            fontWeight = FontWeight.Medium,
            color = gray_5
        ))
    }
}

fun sendRequest(localContext: Context, navController: NavController) {

    tarotService.postTarotResult(tarotInputDto, getPath())
        .enqueue(object : Callback<TarotOutputDto>{
            override fun onResponse(
                call: Call<TarotOutputDto>,
                response: Response<TarotOutputDto>
            ) {

                Log.d("", "onResponse--------")
                if (response.body() == null){
                    Toast.makeText(localContext, "response null", Toast.LENGTH_SHORT).show()
                    return
                }
                
                tarotOutputDto = response.body()!!

//                tarotOutputDto.cardResults = response.body()!!.cardResults
//                tarotOutputDto.overallResult = response.body()!!.overallResult
//                tarotOutputDto.tarotId = response.body()!!.tarotId
//                tarotOutputDto.tarotType = response.body()!!.tarotType
//                tarotOutputDto.cards = response.body()!!.cards

                Log.d("", "${tarotOutputDto.cardResults}--------")
                Log.d("", "${tarotOutputDto.overallResult}--------")


                navController.navigate(ScreenEnum.ResultScreen.name) {
                    navController.graph.startDestinationRoute?.let {
                        popUpTo(it) { inclusive = true }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }

            override fun onFailure(call: Call<TarotOutputDto>, t: Throwable) {
                Log.d("", "onFailure--------!")
                Log.d("", "${t.cause}--------!")
                Log.d("", "${t.message}--------!")
                Log.d("", "${t.stackTrace}--------!")
            }
    })

}