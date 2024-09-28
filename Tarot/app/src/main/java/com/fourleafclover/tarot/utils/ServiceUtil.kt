package com.fourleafclover.tarot.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.chatViewModel
import com.fourleafclover.tarot.data.MatchTarotInputDto
import com.fourleafclover.tarot.data.TarotIdsInputDto
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.fortuneViewModel
import com.fourleafclover.tarot.harmonyShareViewModel
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.myTarotViewModel
import com.fourleafclover.tarot.pickTarotViewModel
import com.fourleafclover.tarot.questionInputViewModel
import com.fourleafclover.tarot.resultViewModel
import com.fourleafclover.tarot.sharedTarotResult
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.screen.fortune.viewModel.dummyTarotOutputDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* 타로 결과 여러개 GET */
// 마이 타로 화면
fun getMyTarotList(
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

                if (response.body() == null){
                    Toast.makeText(localContext, "response null", Toast.LENGTH_SHORT).show()
                    return
                }

                myTarotViewModel.setMyTarotResults(response.body()!!)
            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
            }
        })

}


// 공유하기 상세 보기
fun getSharedTarotDetail(
    localContext: Context,
    navController: NavHostController,
    tarotId: String
) {
    
    getCertainTarotDetail(
        localContext,
        navController,
        tarotId, 
        onResponse = {
            sharedTarotResult = it
            if (sharedTarotResult.tarotType == 5) {
                resultViewModel.distinguishCardResult(sharedTarotResult)
                loadingViewModel.changeDestination(ScreenEnum.ShareHarmonyDetailScreen)
            }
            loadingViewModel.endLoading(navController)
        }
    )

}


/* 타로 결과 단일 GET */
fun getCertainTarotDetail(
    localContext: Context,
    navController: NavHostController,
    tarotId: String,
    onResponse: (responseBody: TarotOutputDto) -> Unit = {},
    onFailure: () -> Unit = {}
) {

    MyApplication.tarotService.getMyTarotResult(TarotIdsInputDto(arrayListOf(tarotId)))
        .enqueue(object : Callback<ArrayList<TarotOutputDto>> {
            override fun onResponse(
                call: Call<ArrayList<TarotOutputDto>>,
                response: Response<ArrayList<TarotOutputDto>>
            ) {

                if (response.body() == null || response.body()!![0] == null){
                    Toast.makeText(localContext, "네트워크 상태를 확인 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                    loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                    loadingViewModel.endLoading(navController)
                    return
                }
                
                onResponse(response.body()!![0])
            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
                onFailure()
            }
        })

}


/* 타로 결과 요청 POST */
fun getTarotResult(localContext: Context, reconnectCount: Int = 0) {
    MyApplication.tarotService.postTarotResult(resultViewModel.getTarotInputDto(), getPath())
        .enqueue(object : Callback<TarotOutputDto>{
            override fun onResponse(
                call: Call<TarotOutputDto>,
                response: Response<TarotOutputDto>
            ) {

                if (response.body() == null){
                    Toast.makeText(localContext, "response null", Toast.LENGTH_SHORT).show()
                    return
                }

                resultViewModel.setTarotResult(response.body()!!)
                pickTarotViewModel.initCardDeck()
                questionInputViewModel.initAnswers()

                loadingViewModel.updateLoadingState(false)
            }

            override fun onFailure(call: Call<TarotOutputDto>, t: Throwable) {

                Log.d("api", "reconnectCount: ${reconnectCount}--------!")

                if (reconnectCount == 3) {
                    loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                    loadingViewModel.updateLoadingState(false)
                    Toast.makeText(localContext, "네트워크 상태를 확인 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                }else{
                    getTarotResult(localContext, reconnectCount+1)
                }

            }
        })

}


/** 궁합 결과 요청 POST */
fun getMatchResult(localContext: Context, reconnectCount: Int = 0){

    /* 테스트 */
//    val cardArray = arrayListOf(1,2,3,4,5,6)

    MyApplication.tarotService.getMatchResult(
        MatchTarotInputDto(
            harmonyShareViewModel.getOwnerNickname(),
            harmonyShareViewModel.getInviteeNickname(),
            harmonyShareViewModel.roomId.value,
            setCardArray()
        )
    )
        .enqueue(object : Callback<TarotOutputDto>{
            override fun onResponse(
                call: Call<TarotOutputDto>,
                response: Response<TarotOutputDto>
            ) {

                if (response.body() == null){
                    Log.d("api", "onResponse null")
                    return
                }

                resultViewModel.distinguishCardResult(response.body()!!)
                loadingViewModel.updateLoadingState(false)
            }

            override fun onFailure(call: Call<TarotOutputDto>, t: Throwable) {
                Log.d("api", "reconnectCount: ${reconnectCount}--------!")

                if (reconnectCount == 3) {
                    loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                    loadingViewModel.updateLoadingState(false)
                    harmonyShareViewModel.deleteRoom()
                    Toast.makeText(localContext, "네트워크 상태를 확인 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                }else{
                    getMatchResult(localContext, reconnectCount+1)
                }

            }
        })
}

fun setCardArray(): ArrayList<Int> {
    return if (harmonyShareViewModel.isRoomOwner.value) {
        arrayListOf(
            chatViewModel.chatState.value.pickedCardNumberState.firstCardNumber,
            chatViewModel.chatState.value.pickedCardNumberState.secondCardNumber,
            chatViewModel.chatState.value.pickedCardNumberState.thirdCardNumber,
            chatViewModel.partnerChatState.value.pickedCardNumberState.firstCardNumber,
            chatViewModel.partnerChatState.value.pickedCardNumberState.secondCardNumber,
            chatViewModel.partnerChatState.value.pickedCardNumberState.thirdCardNumber,
        )
    } else {
        arrayListOf(
            chatViewModel.partnerChatState.value.pickedCardNumberState.firstCardNumber,
            chatViewModel.partnerChatState.value.pickedCardNumberState.secondCardNumber,
            chatViewModel.partnerChatState.value.pickedCardNumberState.thirdCardNumber,
            chatViewModel.chatState.value.pickedCardNumberState.firstCardNumber,
            chatViewModel.chatState.value.pickedCardNumberState.secondCardNumber,
            chatViewModel.chatState.value.pickedCardNumberState.thirdCardNumber,
        )
    }
}



fun getPath() : String {
    return when(fortuneViewModel.pickedTopicState.value.topicNumber){
        0 -> "love"
        1 -> "study"
        2 -> "dream"
        3 -> "job"
        4 -> "today"
        else -> {
            Log.e("tarotError", "error getPath(). pickedTopicNumber: ${fortuneViewModel.pickedTopicState.value.topicNumber}")
            ""
        }
    }
}