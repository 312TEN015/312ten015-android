package com.fourleafclover.tarot.ui.screen.harmony

import android.content.Context
import android.util.Log
import androidx.navigation.NavHostController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.chatViewModel
import com.fourleafclover.tarot.data.OverallResultData
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.harmonyViewModel
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.ui.screen.fortune.viewModel.tarotOutputDto
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.Chat
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.ChatType
import com.fourleafclover.tarot.utils.getCertainTarotRequest
import com.fourleafclover.tarot.utils.getMatchResult
import com.fourleafclover.tarot.utils.getSharedTarotRequest
import com.google.gson.Gson
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
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
        Log.d("socket-test", "onCreateComplete " + args[0].toString())

        val roomId = JSONObject(args[0].toString()).getString("roomId")
        harmonyViewModel.roomId.value = roomId
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
        Log.d("socket-test", "onJoinComplete " + args[0].toString())

        val response = JSONObject(args[0].toString())
        val partnerNickname = response.getString("partnerNickname")
        harmonyViewModel.setPartnerNickname(partnerNickname)

        loadingViewModel.updateLoadingState(false)
    }
}

// 상대방이 시작하기 누름 or 카드 선택함
var onNext = Emitter.Listener { args ->
    CoroutineScope(Dispatchers.Main).launch {
        Log.d("socket-test", "onNext " + args[0].toString())


        if (chatViewModel.chatState.value.scenario != chatViewModel.partnerChatState.value.scenario){
            chatViewModel.removeChatListLastItem()
            chatViewModel.addChatItem(
                Chat(
                    type = ChatType.GuidText,
                    text = "상대방이 답변을 선택했습니다.✨️"
                )
            )
            chatViewModel.addNextScenario()
        }
        
        chatViewModel.updatePartnerScenario()
    }
}

var onResult = Emitter.Listener { args ->
    CoroutineScope(Dispatchers.Main).launch {
        Log.d("socket-test", "onResult " + args[0].toString())


        // TODO 받은 궁합 결과 저장
        val response = JSONObject(args[0].toString())
        val gson = Gson()
        tarotOutputDto = gson.fromJson(response.toString(), TarotOutputDto::class.java)


        loadingViewModel.updateLoadingState(false)
    }
}


/* for test ---------------------------------------------------------------------------------------- */

fun onCreateComplete() {
    val testObj = JSONObject()
    testObj.put("roomId", "testRoomId")
    Log.d("socket-test", "onCreateComplete " + testObj.toString())

    val roomId = testObj.getString("roomId")
    harmonyViewModel.roomId.value = roomId
    MyApplication.prefs.saveHarmonyRoomId(roomId)

    val mNow = System.currentTimeMillis()
    val mDate = Date(mNow)
    MyApplication.prefs.saveHarmonyRoomCreatedAt(mDate.toString())

    loadingViewModel.updateLoadingState(false)
}

fun onJoinComplete() {
    val testObj = JSONObject()
    testObj.put("partnerNickname", "상대방닉네임")
    Log.d("socket-test", "onJoinComplete " + testObj.toString())

    val response = testObj
    val partnerNickname = response.getString("partnerNickname")
    harmonyViewModel.setPartnerNickname(partnerNickname)
    chatViewModel.initAllScenario()

    loadingViewModel.updateLoadingState(false)
}

fun onNext(cardNum: Int = 0) {
    val testObj = JSONObject()
    if (cardNum != 0) testObj.put("cardNum", cardNum.toString())
    Log.d("socket-test", "onNext " + testObj.toString())


    if (chatViewModel.chatState.value.scenario != chatViewModel.partnerChatState.value.scenario){
        chatViewModel.updateGuidText("상대방이 답변을 선택했습니다.✨️")
//        chatViewModel.removeChatListLastItem()
//        chatViewModel.addChatItem(
//            Chat(
//                type = ChatType.GuidText,
//                text = "상대방이 답변을 선택했습니다.✨️"
//            )
//        )
        if (testObj.has("cardNum")){
            chatViewModel.updatePartnerCardNumber(testObj.getString("cardNum").toInt())
        }
        chatViewModel.addNextScenario()
    }

    chatViewModel.updatePartnerScenario()


}

fun onResult(localContext: Context,
             navController: NavHostController) {
    val testObj = JSONObject()
    Log.d("socket-test", "onResult " + testObj.toString())
    // TODO 받은 궁합 결과 저장
    getCertainTarotRequest(localContext, navController, "xY_dTJvxSvjbJ_KN2-sTh")
}


