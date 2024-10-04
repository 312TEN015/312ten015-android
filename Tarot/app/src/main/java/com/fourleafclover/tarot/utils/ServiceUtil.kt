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
import com.fourleafclover.tarot.shareViewModel
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var reconnectCount = 0


/* 타로 결과 여러개 GET */
// 마이 타로 화면
fun getMyTarotList(
    localContext: Context,
    navController: NavHostController,
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
                    MyApplication.toastUtil.makeShortToast("response null")
                    return
                }

                myTarotViewModel.setMyTarotResults(response.body()!!)
                navigateInclusive(navController, ScreenEnum.MyTarotScreen.name)

            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
            }
        })

}


// 공유하기 상세 보기
fun getSharedTarotDetail(
    navController: NavHostController,
    tarotId: String
) {
    
    getCertainTarotDetail(
        tarotId, 
        onResponse = {
            shareViewModel.setSharedTarotResult(it)
            if (it.tarotType == 5) {
                shareViewModel.distinguishCardResult(it)
                loadingViewModel.changeDestination(ScreenEnum.ShareHarmonyDetailScreen)
            }
            loadingViewModel.endLoading(navController)
        }
    )

}


/* 타로 결과 단일 GET */
fun getCertainTarotDetail(
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
                    MyApplication.toastUtil.makeShortToast("네트워크 상태를 확인 후 다시 시도해 주세요.")
                    loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                    loadingViewModel.updateLoadingState(false)
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
fun getTarotResult(localContext: Context) {
    MyApplication.tarotService.postTarotResult(resultViewModel.getTarotInputDto(), getPath())
        .enqueue(object : Callback<TarotOutputDto>{
            override fun onResponse(
                call: Call<TarotOutputDto>,
                response: Response<TarotOutputDto>
            ) {

                if (response.body() == null){
                    MyApplication.toastUtil.makeShortToast("response null")
                    return
                }

                resultViewModel.setTarotResult(response.body()!!)
                pickTarotViewModel.initCardDeck()
                questionInputViewModel.initAnswers()

                loadingViewModel.updateLoadingState(false)
            }

            override fun onFailure(call: Call<TarotOutputDto>, t: Throwable) {
                reconnectCount += 1
                Log.d("api", "reconnectCount: ${reconnectCount}--------!")

                if (reconnectCount == 3) {
                    reconnectCount = 0
                    loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                    loadingViewModel.updateLoadingState(false)
                    MyApplication.toastUtil.makeShortToast("네트워크 상태를 확인 후 다시 시도해 주세요.")
                }else{
                    getTarotResult(localContext)
                }

            }
        })

}

/** 궁합 결과 요청 POST */
fun getMatchResult(){

    /* 테스트 */
//    val cardArray = arrayListOf(1,2,3,4,5,6)

    CoroutineScope(Dispatchers.IO).launch {
        MyApplication.tarotService.getMatchResult(
            MatchTarotInputDto(
                harmonyShareViewModel.getOwnerNickname(),
                harmonyShareViewModel.getInviteeNickname(),
                harmonyShareViewModel.roomId.value,
                setCardArray()
            )
        )
            .enqueue(object : Callback<TarotOutputDto> {
                override fun onResponse(
                    call: Call<TarotOutputDto>,
                    response: Response<TarotOutputDto>
                ) {

                    if (response.body() == null) {
                        Log.d("api", "onResponse null")
                        return
                    }


                    val jsonObject = JSONObject()
                    jsonObject.put("roomId", harmonyShareViewModel.roomId.value)
                    jsonObject.put("tarotId", response.body()!!.tarotId)
                    MyApplication.socket.emit("resultReceived", jsonObject)
                    Log.d("socket-test", "emit resultReceived")

                }

                override fun onFailure(call: Call<TarotOutputDto>, t: Throwable) {
                    reconnectCount += 1
                    Log.d("api", "reconnectCount: ${reconnectCount}--------!")

                    if (reconnectCount == 3) {
                        reconnectCount = 0
                        loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                        loadingViewModel.updateLoadingState(false)
                        harmonyShareViewModel.deleteRoom()
                        MyApplication.closeSocket()
                        MyApplication.toastUtil.makeShortToast("네트워크 상태를 확인 후 다시 시도해 주세요.")
                    } else {
                        getMatchResult()
                    }

                }
            })
    }
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