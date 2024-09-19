package com.fourleafclover.tarot.ui.screen.harmony

import android.util.Log
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.chatViewModel
import com.fourleafclover.tarot.harmonyShareViewModel
import com.fourleafclover.tarot.loadingViewModel
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
        harmonyShareViewModel.roomId.value = roomId
        MyApplication.prefs.saveHarmonyRoomId(roomId)

        val mNow = System.currentTimeMillis()
        val mDate = Date(mNow)
        MyApplication.prefs.saveHarmonyRoomCreatedAt(mDate.toString())

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
    CoroutineScope(Dispatchers.Main).launch {
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

    }
}
