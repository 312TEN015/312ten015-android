package com.fourleafclover.tarot.ui.screen

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import com.fourleafclover.tarot.sharedTarotResult
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.component.setStatusbarColor
import com.fourleafclover.tarot.ui.navigation.FinishOnBackPressed
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.fourleafclover.tarot.ui.navigation.navigateSaveState
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.white
import com.google.firebase.Firebase
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.dynamicLinks
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Preview
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    val localContext = LocalContext.current
    val activity = localContext.findActivity()

    if (activity != null && activity.intent != null) {

        val intent = activity.intent
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(activity) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                Log.w("DynamicLink", "getDynamicLink:onSuccess")
                // Get deep link from result (may be null if no link is found)
                if (pendingDynamicLinkData != null) {
                    val deepLink = pendingDynamicLinkData.link
                    val deppLinkString = deepLink.toString()
                    val sharedTarotId = Uri.parse(deppLinkString).getQueryParameter("tarotId")
                    Log.d("DynamicLink", deppLinkString)
                    Log.d("DynamicLink", sharedTarotId!!)

                    activity.intent = null
                    getSharedTarotRequest(localContext, navController, sharedTarotId)

                }


            }
            .addOnFailureListener(activity) { e ->
                Log.w(
                    "DynamicLink",
                    "getDynamicLink:onFailure",
                    e
                )
            }
    }

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
                Column(modifier = Modifier.padding(end = 4.dp).weight(1f)) {
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

                Column(modifier = Modifier.padding(start = 4.dp).weight(1f)) {
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
    Log.d("", tarotResultArray.joinToString(" "))
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
//                Log.d("", response.body().toString())
                myTarotResults = arrayListOf()

                for (item in response.body()!!){
                    myTarotResults.add(item)
                    Log.d("", "${item.toString()}--------")
                }
            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
                Log.d("", "onFailure--------!")
                Log.d("", "${t.cause}--------!")
                Log.d("", "${t.message}--------!")
                Log.d("", "${t.stackTrace}--------!")
            }
        })

}

fun getSharedTarotRequest(
    localContext: Context,
    navController: NavHostController,
    tarotId: String
) {

    tarotService.getMyTarotResult(TarotIdsInputDto(arrayListOf(tarotId)))
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

                Log.d("", response.body()!!.toString())
                Log.d("", response.body()!!.toString())

                sharedTarotResult = response.body()!![0]
                navigateInclusive(navController, ScreenEnum.ShareDetailScreen.name)
            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
                Log.d("", "onFailure--------!")
                Log.d("", "${t.cause}--------!")
                Log.d("", "${t.message}--------!")
                Log.d("", "${t.stackTrace}--------!")
            }
        })

}