package com.fourleafclover.tarot.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.chatViewModel
import com.fourleafclover.tarot.data.MatchCards
import com.fourleafclover.tarot.data.TarotIdsInputDto
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.fortuneViewModel
import com.fourleafclover.tarot.harmonyViewModel
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.myTarotViewModel
import com.fourleafclover.tarot.pickTarotViewModel
import com.fourleafclover.tarot.questionInputViewModel
import com.fourleafclover.tarot.resultViewModel
import com.fourleafclover.tarot.sharedTarotResult
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.screen.fortune.viewModel.tarotOutputDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* 타로 결과 여러개 GET */
fun getTarotRequest(
    localContext: Context,
    tarotResultArray: ArrayList<String>
) {
    Log.d("", tarotResultArray.joinToString(" "))
    MyApplication.tarotService.getMyTarotResult(TarotIdsInputDto(tarotResultArray))
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

                myTarotViewModel.setMyTarotResults(response.body()!!)
            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
                Log.d("", "onFailure--------!")
                Log.d("", "${t.cause}--------!")
                Log.d("", "${t.message}--------!")
                Log.d("", "${t.stackTrace}--------!")
            }
        })

}


/* 타로 결과 단일 GET */
fun getSharedTarotRequest(
    localContext: Context,
    navController: NavHostController,
    tarotId: String
) {

    MyApplication.tarotService.getMyTarotResult(TarotIdsInputDto(arrayListOf(tarotId)))
        .enqueue(object : Callback<ArrayList<TarotOutputDto>> {
            override fun onResponse(
                call: Call<ArrayList<TarotOutputDto>>,
                response: Response<ArrayList<TarotOutputDto>>
            ) {

                Log.d("", "onResponse--------")
                if (response.body() == null || response.body()!![0] == null){
                    Toast.makeText(localContext, "네트워크 상태를 확인 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                    loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                    loadingViewModel.endLoading(navController)
                    return
                }

                Log.d("", response.body()!!.toString())

                sharedTarotResult = response.body()!![0]
                if (sharedTarotResult.tarotType == 5) {
                    resultViewModel.tmpDistinguishCardResult(sharedTarotResult)
                    loadingViewModel.changeDestination(ScreenEnum.ShareHarmonyDetailScreen)
                }
                loadingViewModel.endLoading(navController)
            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
                Log.d("", "onFailure--------!")
                Log.d("", "${t.cause}--------!")
                Log.d("", "${t.message}--------!")
                Log.d("", "${t.stackTrace}--------!")
            }
        })

}

/* 타로 결과 단일 GET */
fun getCertainTarotRequest(
    localContext: Context,
    navController: NavHostController,
    tarotId: String
) {

    MyApplication.tarotService.getMyTarotResult(TarotIdsInputDto(arrayListOf(tarotId)))
        .enqueue(object : Callback<ArrayList<TarotOutputDto>> {
            override fun onResponse(
                call: Call<ArrayList<TarotOutputDto>>,
                response: Response<ArrayList<TarotOutputDto>>
            ) {

                Log.d("", "onResponse--------")
                if (response.body() == null || response.body()!![0] == null){
                    Toast.makeText(localContext, "네트워크 상태를 확인 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                    loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                    loadingViewModel.endLoading(navController)
                    return
                }

                Log.d("", response.body()!!.toString())

                tarotOutputDto = response.body()!![0]
                resultViewModel.distinguishCardResult(tarotOutputDto)
                loadingViewModel.endLoading(navController)
            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
                Log.d("", "onFailure--------!")
                Log.d("", "${t.cause}--------!")
                Log.d("", "${t.message}--------!")
                Log.d("", "${t.stackTrace}--------!")
            }
        })

}


/* 타로 결과 요청 POST */
fun sendRequest(localContext: Context, navController: NavHostController) {
    Log.d("", fortuneViewModel.getTarotInputDto().toString())
    MyApplication.tarotService.postTarotResult(fortuneViewModel.getTarotInputDto(), getPath())
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

                fortuneViewModel.setTarotResult(response.body()!!)
                pickTarotViewModel.initCardDeck()
                questionInputViewModel.initAnswers()
                Log.d("", "${fortuneViewModel.tarotResult}--------")

                loadingViewModel.updateLoadingState(false)
            }

            override fun onFailure(call: Call<TarotOutputDto>, t: Throwable) {
                Log.d("", "onFailure--------!")
                Log.d("", "${t.cause}--------!")
                Log.d("", "${t.message}--------!")
                Log.d("", "${t.stackTrace}--------!")

                loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                loadingViewModel.updateLoadingState(false)
                Toast.makeText(localContext, "네트워크 상태를 확인 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()

            }
        })

}



fun getMatchResult(reconnectCount: Int = 0){
//    val cardArray = arrayListOf(
//        chatViewModel.chatState.value.pickedCardNumberState.firstCardNumber,
//        chatViewModel.chatState.value.pickedCardNumberState.secondCardNumber,
//        chatViewModel.chatState.value.pickedCardNumberState.thirdCardNumber,
//        chatViewModel.partnerChatState.value.pickedCardNumberState.firstCardNumber,
//        chatViewModel.partnerChatState.value.pickedCardNumberState.secondCardNumber,
//        chatViewModel.partnerChatState.value.pickedCardNumberState.thirdCardNumber,
//    )

    val cardArray = arrayListOf(1,2,3,4,5,6)

    MyApplication.tarotService.getMatchResult(MatchCards(cardArray))
        .enqueue(object : Callback<TarotOutputDto>{
            override fun onResponse(
                call: Call<TarotOutputDto>,
                response: Response<TarotOutputDto>
            ) {

                Log.d("api", "onResponse--------")
                if (response.body() == null){
                    Log.d("api", "onResponse null")
                    return
                }

                tarotOutputDto = response.body()!!
                resultViewModel.tmpDistinguishCardResult(tarotOutputDto)
                Log.d("api", "onResponse $tarotOutputDto")
                loadingViewModel.updateLoadingState(false)
            }

            override fun onFailure(call: Call<TarotOutputDto>, t: Throwable) {
                Log.d("api", "onFailure--------!")
                Log.d("api", "${t.cause}--------!")
                Log.d("api", "${t.message}--------!")
                Log.d("api", "reconnectCount: ${reconnectCount}--------!")

                if (reconnectCount == 3) {
                    loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                    loadingViewModel.updateLoadingState(false)
                }else{
                    getMatchResult(reconnectCount+1)
                }

            }
        })
}



fun getPath() : String {
    return when(fortuneViewModel.pickedTopicNumber){
        0 -> "love"
        1 -> "study"
        2 -> "dream"
        3 -> "job"
        4 -> "today"
        else -> {
            Log.e("tarotError", "error getPath(). pickedTopicNumber: ${fortuneViewModel.pickedTopicNumber}")
            ""
        }
    }
}