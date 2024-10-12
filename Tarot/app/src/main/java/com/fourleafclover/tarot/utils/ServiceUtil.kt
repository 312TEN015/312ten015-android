package com.fourleafclover.tarot.utils

import android.content.Context
import android.util.Log
import androidx.navigation.NavHostController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.data.MatchTarotInputDto
import com.fourleafclover.tarot.data.TarotIdsInputDto
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.fourleafclover.tarot.ui.screen.fortune.viewModel.PickTarotViewModel
import com.fourleafclover.tarot.ui.screen.fortune.viewModel.QuestionInputViewModel
import com.fourleafclover.tarot.ui.screen.harmony.emitResultPrepared
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.ChatViewModel
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.HarmonyShareViewModel
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.LoadingViewModel
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.ResultViewModel
import com.fourleafclover.tarot.ui.screen.my.viewmodel.MyTarotViewModel
import com.fourleafclover.tarot.ui.screen.my.viewmodel.ShareViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var reconnectCount = 0
const val maxReconnectCount = 5


/* 타로 결과 여러개 GET */
// 마이 타로 화면
fun getMyTarotList(
    localContext: Context,
    navController: NavHostController,
    tarotResultArray: ArrayList<String>,
    myTarotViewModel: MyTarotViewModel
) {
    Log.d("", tarotResultArray.joinToString(" "))
    MyApplication.tarotService.getMyTarotResult(TarotIdsInputDto(tarotResultArray))
        .enqueue(object : Callback<ArrayList<TarotOutputDto>> {
            override fun onResponse(
                call: Call<ArrayList<TarotOutputDto>>,
                response: Response<ArrayList<TarotOutputDto>>
            ) {

                if (response.body() == null) {
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
    tarotId: String,
    shareViewModel: ShareViewModel,
    loadingViewModel: LoadingViewModel
) {

    getCertainTarotDetail(
        tarotId,
        loadingViewModel = loadingViewModel,
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
    loadingViewModel: LoadingViewModel,
    onResponse: (responseBody: TarotOutputDto) -> Unit = {},
    onFailure: () -> Unit = {}
) {
    MyApplication.tarotService.getMyTarotResult(TarotIdsInputDto(arrayListOf(tarotId)))
        .enqueue(object : Callback<ArrayList<TarotOutputDto>> {
            override fun onResponse(
                call: Call<ArrayList<TarotOutputDto>>,
                response: Response<ArrayList<TarotOutputDto>>
            ) {

                CoroutineScope(Dispatchers.Main).launch {
                    if (response.body() == null || response.body()!![0] == null) {
                        MyApplication.toastUtil.makeShortToast("네트워크 상태를 확인 후 다시 시도해 주세요.")
                        loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                        loadingViewModel.updateLoadingState(false)
                        return@launch
                    }

                    onResponse(response.body()!![0])
                }
            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
                onFailure()
            }
        })

}


/* 타로 결과 요청 POST */
fun getTarotResult(
    localContext: Context,
    resultViewModel: ResultViewModel,
    pickTarotViewModel: PickTarotViewModel,
    loadingViewModel: LoadingViewModel,
    questionInputViewModel: QuestionInputViewModel,
    topicNumber: Int
) {
    MyApplication.tarotService.postTarotResult(
        resultViewModel.getTarotInputDto(
            pickTarotViewModel,
            questionInputViewModel
        ),
        getPath(topicNumber)
    )
        .enqueue(object : Callback<TarotOutputDto> {
            override fun onResponse(
                call: Call<TarotOutputDto>,
                response: Response<TarotOutputDto>
            ) {

                if (response.body() == null) {
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

                if (reconnectCount == maxReconnectCount) {
                    reconnectCount = 0
                    loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                    loadingViewModel.updateLoadingState(false)
                    MyApplication.toastUtil.makeShortToast("네트워크 상태를 확인 후 다시 시도해 주세요.")
                } else {
                    getTarotResult(
                        localContext,
                        resultViewModel,
                        pickTarotViewModel,
                        loadingViewModel,
                        questionInputViewModel,
                        topicNumber
                    )
                }

            }
        })

}

/** 궁합 결과 요청 POST */
fun getMatchResult(
    harmonyShareViewModel: HarmonyShareViewModel,
    loadingViewModel: LoadingViewModel,
    chatViewModel: ChatViewModel
) {

    CoroutineScope(Dispatchers.IO).launch {
        MyApplication.tarotService.getMatchResult(
            MatchTarotInputDto(
                harmonyShareViewModel.getOwnerNickname(),
                harmonyShareViewModel.getInviteeNickname(),
                harmonyShareViewModel.roomId.value,
                setCardArray(harmonyShareViewModel.isRoomOwner.value, chatViewModel)
            )
        )
            .enqueue(object : Callback<TarotOutputDto> {
                override fun onResponse(
                    call: Call<TarotOutputDto>,
                    response: Response<TarotOutputDto>
                ) {

                    if (response.body() == null) {
                        Log.d("api", "onResponse null")
                    }

                    emitResultPrepared(harmonyShareViewModel, response.body()!!.tarotId)

                }

                override fun onFailure(call: Call<TarotOutputDto>, t: Throwable) {
                    reconnectCount += 1
                    Log.d("api", "reconnectCount: ${reconnectCount}--------!")

                    if (reconnectCount == maxReconnectCount) {
                        reconnectCount = 0

                        emitResultPrepared(harmonyShareViewModel)

                        loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
                        loadingViewModel.updateLoadingState(false)
                        harmonyShareViewModel.deleteRoom()
                        MyApplication.toastUtil.makeShortToast("네트워크 상태를 확인 후 다시 시도해 주세요.")
                    } else {
                        getMatchResult(harmonyShareViewModel, loadingViewModel, chatViewModel)
                    }

                }
            })
    }
}

fun setCardArray(isRoomOwner: Boolean, chatViewModel: ChatViewModel): ArrayList<Int> {
    return if (isRoomOwner) {
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


fun getPath(topicNumber: Int): String {
    return when (topicNumber) {
        0 -> "love"
        1 -> "study"
        2 -> "dream"
        3 -> "job"
        4 -> "today"
        else -> {
            Log.e(
                "tarotError",
                "error getPath(). pickedTopicNumber: ${topicNumber}"
            )
            ""
        }
    }
}