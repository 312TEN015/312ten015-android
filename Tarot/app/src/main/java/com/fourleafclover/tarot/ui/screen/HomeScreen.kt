package com.fourleafclover.tarot.ui.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.MyApplication.Companion.tarotService
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.data.TarotIdsInputDto
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.myTarotResults
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.ui.component.backgroundModifier
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.component.setStatusbarColor
import com.fourleafclover.tarot.ui.navigation.FinishOnBackPressed
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateSaveState
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.white
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Preview
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    val localContext = LocalContext.current

    setStatusbarColor(LocalView.current, backgroundColor_2)

    val tarotResultArray = MyApplication.prefs.getTarotResultArray()
    // 영상 촬영용
//    val tmpArray = MyApplication.prefs.getTarotResultArray()
//    val tarotResultArray = arrayListOf<String>()
//    tarotResultArray.add(tmpArray[(tmpArray.size-1)])

    FinishOnBackPressed()

    var send by remember { mutableStateOf(false) }

    // 요청을 한번만 보내도록 함
    if (!send){
        getTarotRequest(localContext, tarotResultArray)
        send = true
    }

    Column(modifier = getBackgroundModifier(backgroundColor_2)
        .padding(horizontal = 20.dp)
        .padding(bottom = 60.dp)
        .verticalScroll(rememberScrollState())) {
        Text(
            text = "타로 카드를 뽑고\n운세를 확인해보세요!",
            style = getTextStyle(26, FontWeight.Bold, white),
            modifier = Modifier.padding(top = 26.dp, bottom = 24.dp)
        )

        Column {
            Text(
                text = "주제별 운세",
                style = getTextStyle(16, FontWeight.Medium, gray_3),
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Row(Modifier.padding(bottom = 32.dp)) {
                Column(modifier = Modifier.padding(end = 8.dp)) {
                    Image(modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable {
                            pickedTopicNumber = 0
                            navigateSaveState(navController, ScreenEnum.InputScreen.name)
                        }, painter = painterResource(id = R.drawable.category_love), contentDescription = "연애운")
                    Image(painter = painterResource(id = R.drawable.category_dream),
                        contentDescription = "소망운",
                        modifier = Modifier.clickable {
                            pickedTopicNumber = 2
                            navigateSaveState(navController, ScreenEnum.InputScreen.name)
                        }
                    )
                }

                Column {
                    Image(modifier = Modifier
                        .padding(bottom = 6.dp)
                        .clickable {
                            pickedTopicNumber = 1
                            navigateSaveState(navController, ScreenEnum.InputScreen.name)
                        },
                        painter = painterResource(id = R.drawable.category_study),
                        contentDescription = "학업운")
                    Image(painter = painterResource(id = R.drawable.category_job),
                        contentDescription = "취업운",
                        modifier = Modifier.clickable {
                            pickedTopicNumber = 3
                            navigateSaveState(navController, ScreenEnum.InputScreen.name)
                        }
                    )
                }
            }

            Column(modifier = Modifier.padding(bottom = 42.dp)) {
                Text(
                    text = "오늘의 운세",
                    style = getTextStyle(16, FontWeight.Medium, gray_3),
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Image(painter = painterResource(id = R.drawable.category_today),
                    contentDescription = "오늘의 운세",
                    Modifier.clickable {
                        pickedTopicNumber = 4
                        navigateSaveState(navController, ScreenEnum.PickTarotScreen.name)
                    })

            }




        }
    }

}

fun getTarotRequest(
    localContext: Context,
    tarotResultArray: ArrayList<String>
) {

    tarotService.getMyTarotResult(TarotIdsInputDto(tarotResultArray))
        .enqueue(object : Callback<ArrayList<TarotOutputDto>> {
            override fun onResponse(
                call: Call<ArrayList<TarotOutputDto>>,
                response: Response<ArrayList<TarotOutputDto>>
            ) {

                Log.d("", "onResponse--------")
                if (response.body() == null){
                    Toast.makeText(localContext, "response null", Toast.LENGTH_SHORT).show()
                    return
                }
                Log.d("", response.body().toString())
                myTarotResults = arrayListOf()

                for (item in response.body()!!){
                    myTarotResults.add(item)
                    Log.d("", "${item.toString()}--------")
                }
//                myTarotResults.add(TarotOutputDto("0", 2, arrayListOf(), "2023년 12월 16일", arrayListOf(), null))
//                myTarotResults.add(TarotOutputDto("0", 3, arrayListOf(), "2023년 12월 15일", arrayListOf(), null))




            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
                Log.d("", "onFailure--------!")
                Log.d("", "${t.cause}--------!")
                Log.d("", "${t.message}--------!")
                Log.d("", "${t.stackTrace}--------!")
            }
        })

}