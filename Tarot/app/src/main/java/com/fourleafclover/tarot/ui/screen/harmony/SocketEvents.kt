package com.fourleafclover.tarot.ui.screen.harmony

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.chatViewModel
import com.fourleafclover.tarot.harmonyShareViewModel
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.mainViewModel
import com.fourleafclover.tarot.resultViewModel
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.Scenario
import com.fourleafclover.tarot.utils.getCertainTarotDetail
import com.fourleafclover.tarot.utils.getMatchResult
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.Arrays
import java.util.Date

/*                             <inviter>                      <invitee>
* connect               RoomCreateLoadingScreen               ShareUtil
* emit - create         RoomCreateLoadingScreen
* on - createComplete   RoomCreateLoadingScreen
* emit - join           RoomInviteLoadingScreen         RoomInviteLoadingScreen
* on - joinComplete     RoomInviteLoadingScreen         RoomInviteLoadingScreen
* on - partnerChecked       RoomChatScreen                   RoomChatScreen
* */

// 방 생성 완료
var onCreateComplete = Emitter.Listener { args ->
    CoroutineScope(Dispatchers.Main).launch {
        Log.d("socket-test", "createComplete " + args[0].toString())

        val roomId = JSONObject(args[0].toString()).getString("roomId")
        harmonyShareViewModel.createNewRoom(roomId)

        loadingViewModel.updateLoadingState(false)
    }
}

// 전원 입장 완료
var onJoinComplete = Emitter.Listener { args ->
    CoroutineScope(Dispatchers.Main).launch {
        Log.d("socket-test", "joinComplete " + args[0].toString())

        val response = JSONObject(args[0].toString())
        val nicknameArray: JSONArray = response.getJSONArray("nickname")
        val nicknames: List<String> = mutableListOf<String>().apply {
            for (i in 0 until nicknameArray.length()) {
                add(nicknameArray.getString(i))
            }
        }

        val partnerNickname = nicknames.find { it != harmonyShareViewModel.getUserNickname() }
        harmonyShareViewModel.setPartnerNickname(partnerNickname ?: "null")
        chatViewModel.resetChatData()
        chatViewModel.initOpening()
        loadingViewModel.updateLoadingState(false)
    }
}

// 상대방이 시작하기 누름 or 카드 선택함
var onNext = Emitter.Listener { args ->
    // 상대방 업데이트 한 후에
    chatViewModel.updatePartnerScenario()

    val myScenario = chatViewModel.chatState.value.scenario
    val partnerScenario = chatViewModel.partnerChatState.value.scenario
    Log.d("socket-test", "onNext my: " + myScenario.name)
    Log.d("socket-test", "onNext partner: " + partnerScenario.name)
    Log.d("socket-test", "onNext args: ${Arrays.toString(args)}")

    // 업데이트 한게 나랑 다름 = 내가 뒤처짐
    if (myScenario != partnerScenario){
        if (args.isNotEmpty()){
            val partnerCardNum = JSONObject(args[0].toString()).getString("cardNum")
            chatViewModel.updatePartnerCardNumber(chatViewModel.getBeforeScenario(partnerScenario), partnerCardNum.toInt())
            Log.d("socket-test", "onNext partner card - $partnerCardNum")
        }else{
            Log.d("socket-test", "onNext - partner started")
        }
    }
    // 업데이트 한게 나랑 같음 = 내가 먼저함
    else {
        chatViewModel.updateGuidText("상대방이 답변을 선택했습니다.✨️")
        if (args.isNotEmpty()){
            val partnerCardNum = JSONObject(args[0].toString()).getString("cardNum")
            chatViewModel.updatePartnerCardNumber(chatViewModel.getBeforeScenario(partnerScenario), partnerCardNum.toInt())
            Log.d("socket-test", "onNext partner card - $partnerCardNum")
        }else{
            Log.d("socket-test", "onNext - I started")
        }
        chatViewModel.addNextScenario()
    }

    Log.d("socket-test", "onNext my cards : "
            + chatViewModel.chatState.value.pickedCardNumberState.firstCardNumber + ", "
            + chatViewModel.chatState.value.pickedCardNumberState.secondCardNumber + ", "
            + chatViewModel.chatState.value.pickedCardNumberState.thirdCardNumber)
    Log.d("socket-test", "onNext partner cards : "
            + chatViewModel.partnerChatState.value.pickedCardNumberState.firstCardNumber + ", "
            + chatViewModel.partnerChatState.value.pickedCardNumberState.secondCardNumber + ", "
            + chatViewModel.partnerChatState.value.pickedCardNumberState.thirdCardNumber)

    // 둘다 완료된 경우
    if (chatViewModel.checkIsAllCardPicked()) {
        Log.d("socket-test", "onNext result request send")
        chatViewModel.updatePickedCardNumberState()
        getMatchResult()
    }
}

// 응답 생성 완료
var onResult = Emitter.Listener { args ->
    Log.d("socket-test", "resultPrepared " + args[0].toString())

    if (args.isNotEmpty()){
        val tarotId = JSONObject(args[0].toString()).getString("tarotId")

        // 궁합 결과 가져오기 실패한 경우
        if (tarotId.isNullOrEmpty()) {
            loadingViewModel.changeDestination(ScreenEnum.HomeScreen)
            loadingViewModel.updateLoadingState(false)
            MyApplication.toastUtil.makeShortToast("네트워크 상태를 확인 후 다시 시도해 주세요.")
        }
        // 성공한 경우
        else{
            getCertainTarotDetail(
                tarotId,
                onResponse = {
                    resultViewModel.setIsMatchResultPrepared(true)
                    resultViewModel.distinguishCardResult(it)
                    loadingViewModel.updateLoadingState(false)
                }
            )
        }

        MyApplication.closeSocket()

    }
}
