package com.fourleafclover.tarot.data

/** 유저가 선택한 주제 상태 */
data class PickedTopicState(
    var topicNumber: Int = 0,
    var topicSubjectData: TarotSubjectData = TarotSubjectData()
)


/** 뽑은 카드 상태 */
data class PickedCardNumberState(
    var firstCardNumber: Int = -1,
    var secondCardNumber: Int = -1,
    var thirdCardNumber: Int = -1
)