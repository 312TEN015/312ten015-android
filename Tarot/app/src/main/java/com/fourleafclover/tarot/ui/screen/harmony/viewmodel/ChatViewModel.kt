package com.fourleafclover.tarot.ui.screen.harmony.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourleafclover.tarot.data.PickedCardNumberState
import com.fourleafclover.tarot.harmonyShareViewModel
import com.fourleafclover.tarot.pickTarotViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

val scenarioSequence = arrayListOf(
    Scenario.Opening,
    Scenario.FirstCard,
    Scenario.SecondCard,
    Scenario.ThirdCard,
    Scenario.Complete,
    Scenario.End
)

data class Chat(
    val type: ChatType,
    var text: String = "",
    val drawable: Int = 0,
    val code: String = "",
    var isShown: Boolean = false    // 애니메이션 재생 여부 판단을 위해
)

enum class ChatType {
    PartnerChatText,
    PartnerChatImage,
    PartnerChatButton,
    MyChatText,
    MyChatImage,
    GuidText,
    Button,
    PickCard,
}

enum class Scenario {
    Opening,
    FirstCard,
    SecondCard,
    ThirdCard,
    Complete,
    End
}

enum class CardDeckStatus {
    Gathered,
    Spread
}

data class ChatState(
    var scenario: Scenario = Scenario.Opening,
    var cardDeckStatus: CardDeckStatus = CardDeckStatus.Gathered,
    var pickedCardNumberState: PickedCardNumberState = PickedCardNumberState()
)

class ChatViewModel : ViewModel() {
    private var _chatState = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState.asStateFlow()

    private val _partnerChatState = MutableStateFlow(ChatState())
    val partnerChatState: StateFlow<ChatState> = _partnerChatState.asStateFlow()

    private val _pickSequence = MutableStateFlow(1)
    val pickSequence: StateFlow<Int> = _pickSequence.asStateFlow()

    private val chatList = mutableStateListOf<Chat>()

    private lateinit var opening: List<Chat>
    private lateinit var firstCard: List<Chat>
    private lateinit var firstCardSelected: List<Chat>
    private lateinit var secondCard: List<Chat>
    private lateinit var secondCardSelected: List<Chat>
    private lateinit var thirdCard: List<Chat>
    private lateinit var complete: List<Chat>

    fun clear() = viewModelScope.launch { onCleared() }

    fun resetChatData() {
        initChatStates()
        chatList.clear()
        opening = listOf()
        firstCard = listOf()
        firstCardSelected = listOf()
        secondCard = listOf()
        secondCardSelected = listOf()
        thirdCard = listOf()
        complete = listOf()
    }

    fun initAllScenario() {
        initOpening()
        initFirst()
        initSecond()
        initThird()
        initComplete()
    }

    fun initChatStates(){
        _chatState.value = ChatState()
        _partnerChatState.value = ChatState()
    }

    fun initOpening(){
        opening = listOf(
            Chat(ChatType.PartnerChatText, "${harmonyShareViewModel.getUserNickname()}님, 그 사람과의 운명이 궁금하시군요!", code = "opening_1"),
            Chat(ChatType.PartnerChatText, "지금부터 타로카드를 통해 서로의 운명 궁합을 봐드릴게요!\n궁합 보실 준비가 되셨다면 [시작하기]를 눌러주세요!", code = "opening_2"),
            Chat(ChatType.Button, "시작하기", code = "opening_3")
        )
        chatList.addAll(opening)
    }

    private fun initFirst(){
        firstCard = listOf(
            Chat(ChatType.PartnerChatText, "두분 모두 궁합 볼 준비가 되셨군요! 이제부터 차례대로 총 세장의 카드를 선택하실 수 있어요.", code = "firstCard_1"),
            Chat(ChatType.PartnerChatText, "서로 선택한 카드를 기반으로, 두분의 궁합 운명을 해석해드릴게요.\uD83D\uDD2E", code = "firstCard_2"),
            Chat(ChatType.PartnerChatText, "우선 상대방을 떠올리며 첫번째 카드를 골라주세요.", code = "firstCard_3"),
            Chat(ChatType.PickCard, code = "fristCard_4"),
        )
    }

    private fun initSecond(){
        secondCard = listOf(
            Chat(ChatType.PartnerChatText, "${harmonyShareViewModel.getPartnerNickname()}님은 이 카드를 선택하셨어요.", code = "secondCard_2"),
            Chat(type = ChatType.PartnerChatImage, drawable = _partnerChatState.value.pickedCardNumberState.firstCardNumber, code = "secondCard_2"),
            Chat(ChatType.PartnerChatText, "이제 두번째 카드를 골라봐요!", code = "secondCard_3"),
            Chat(ChatType.PickCard, code = "secondCard_4"),
        )
    }

    private fun initThird(){
        thirdCard = listOf(
            Chat(ChatType.PartnerChatText, "${harmonyShareViewModel.getPartnerNickname()}님은 이 카드를 선택하셨어요.", code = "thirdCard_2"),
            Chat(type = ChatType.PartnerChatImage, drawable = _partnerChatState.value.pickedCardNumberState.secondCardNumber, code = "secondCard_2"),
            Chat(ChatType.PartnerChatText, "이제 세번째 카드를 골라봐요!", code = "thirdCard_3"),
            Chat(ChatType.PickCard, code = "thirdCard_4"),
        )
    }

    fun initComplete(){
        complete = listOf(
            Chat(ChatType.PartnerChatButton, "짝짝짝\uD83D\uDC4F\n모든 카드 선택이 완료되었습니다! ${harmonyShareViewModel.getUserNickname()}님과 ${harmonyShareViewModel.getPartnerNickname()}님의 궁합은...", code = "complete_1")
        )
    }

    fun removeChatListLastItem() = chatList.removeLast()

    fun updateGuidText(text: String) {
        chatList.last().text = text
    }

    fun getChatListSize(): Int = chatList.size

    fun getChatItem(idx: Int): Chat = chatList[idx]

    fun addChatItem(chatItem: Chat) = chatList.add(chatItem)

    fun updatePickSequence() {
        _pickSequence.value += 1
    }

    fun getNextScenario(nowScenario: Scenario) = scenarioSequence[scenarioSequence.indexOf(nowScenario) + 1]

    fun getBeforeScenario(nowScenario: Scenario) = scenarioSequence[scenarioSequence.indexOf(nowScenario) - 1]

    fun updateScenario(){
        _chatState.value.scenario = getNextScenario(_chatState.value.scenario)
    }

    fun updatePartnerScenario(){
        _partnerChatState.value.scenario = getNextScenario(_partnerChatState.value.scenario)
    }

    fun moveToNextScenario() {
        updateScenario()    // 다음 시나리오로 업데이트
        addNextScenario()   // 업데이트 된 시나리오 진행
    }

    fun addNextScenario() {
        when (_chatState.value.scenario) {
            Scenario.FirstCard -> {
                initFirst()
                chatList.addAll(firstCard)
            }
            Scenario.SecondCard -> {
                initSecond()
                chatList.addAll(secondCard)
            }
            Scenario.ThirdCard -> {
                initThird()
                chatList.addAll(thirdCard)
            }
            Scenario.Complete -> {
                initComplete()
                chatList.addAll(complete)
            }
            else -> {}
        }
    }

    fun getSec(chat: Chat): Int {
        return when (_chatState.value.scenario) {
            Scenario.Opening -> opening.indexOf(chat)
            Scenario.FirstCard -> firstCard.indexOf(chat)
            Scenario.SecondCard -> secondCard.indexOf(chat)
            Scenario.ThirdCard -> thirdCard.indexOf(chat)
            Scenario.Complete -> complete.indexOf(chat)
            else -> 0
        }
    }

    fun updateCardDeckStatus(newStatus: CardDeckStatus) {
        _chatState.value = ChatState(
            _chatState.value.scenario,
            newStatus,
            _chatState.value.pickedCardNumberState
        )
    }

    fun updatePickedCardNumberState(){
        _chatState.value.pickedCardNumberState.firstCardNumber = pickTarotViewModel.pickedCardNumberState.value.firstCardNumber
        _chatState.value.pickedCardNumberState.secondCardNumber = pickTarotViewModel.pickedCardNumberState.value.secondCardNumber
        _chatState.value.pickedCardNumberState.thirdCardNumber = pickTarotViewModel.pickedCardNumberState.value.thirdCardNumber
    }

    fun updatePartnerCardNumber(partnerScenario: Scenario, cardNumber: Int) {
        when (partnerScenario) {
            Scenario.FirstCard -> {
                _partnerChatState.value.pickedCardNumberState.firstCardNumber = cardNumber
            }
            Scenario.SecondCard -> {
                _partnerChatState.value.pickedCardNumberState.secondCardNumber = cardNumber
            }
            Scenario.ThirdCard -> {
                _partnerChatState.value.pickedCardNumberState.thirdCardNumber = cardNumber
            }
            else -> {}
        }
    }

    private val adjectives = arrayListOf(
        "의미심장한",
        "멋진",
        "신비로운",
        "비밀스러운",
        "상징적인",
        "심오한",
        "미묘한",
        "몽환적인",
        "수수께끼같은",
        "독특한"
    )

    fun getAdjectives(): String {
        return adjectives[(0 until adjectives.size).random()]
    }

    fun checkIsAllCardPicked() : Boolean {
        return (pickTarotViewModel.pickedCardNumberState.value.firstCardNumber != -1
                && pickTarotViewModel.pickedCardNumberState.value.secondCardNumber != -1
                && pickTarotViewModel.pickedCardNumberState.value.thirdCardNumber != -1
                && partnerChatState.value.pickedCardNumberState.firstCardNumber != -1
                && partnerChatState.value.pickedCardNumberState.firstCardNumber != -1
                && partnerChatState.value.pickedCardNumberState.firstCardNumber != -1
                )
    }
}