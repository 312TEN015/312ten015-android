package com.fourleafclover.tarot

import com.fourleafclover.tarot.constant.*
import com.fourleafclover.tarot.data.CardResultData
import com.fourleafclover.tarot.data.OverallResultData
import com.fourleafclover.tarot.data.TarotInputDto
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.data.TarotSubjectData
import com.fourleafclover.tarot.ui.screen.fortune.viewModel.FortuneViewModel
import com.fourleafclover.tarot.ui.screen.fortune.viewModel.QuestionInputViewModel
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.ChatViewModel
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.HarmonyViewModel
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.LoadingViewModel
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.primaryDream
import com.fourleafclover.tarot.ui.theme.primaryJob
import com.fourleafclover.tarot.ui.theme.primaryLove
import com.fourleafclover.tarot.ui.theme.primaryStudy

val SubjectLove = TarotSubjectData(
        loveFortune,
        loveMajorQuestion,
        arrayListOf(loveSubQuestion1, loveSubQuestion2, loveSubQuestion3),
        arrayListOf(lovePlaceHolder1, lovePlaceHolder2, lovePlaceHolder3),
        primaryLove
)
val SubjectStudy = TarotSubjectData(
        studyFortune,
        studyMajorQuestion,
        arrayListOf(studySubQuestion1, studySubQuestion2, studySubQuestion3),
        arrayListOf(studyPlaceHolder1, studyPlaceHolder2, studyPlaceHolder3),
        primaryStudy
)
val SubjectDream = TarotSubjectData(
        dreamFortune,
        dreamMajorQuestion,
        arrayListOf(dreamSubQuestion1, dreamSubQuestion2, dreamSubQuestion3),
        arrayListOf(dreamPlaceHolder1, dreamPlaceHolder2, dreamPlaceHolder3),
        primaryDream
)
val SubjectJob = TarotSubjectData(
        jobFortune,
        jobMajorQuestion,
        arrayListOf(jobSubQuestion1, jobSubQuestion2, jobSubQuestion3),
        arrayListOf(jobPlaceHolder1, jobPlaceHolder2, jobPlaceHolder3),
        primaryJob
)
val SubjectToday = TarotSubjectData(
        todayFortune,
        todayMajorQuestion,
        arrayListOf(),
        arrayListOf(),
        gray_9
)
val SubjectHarmony = TarotSubjectData(
        majorTopic = harmonyFortune,
        primaryColor = gray_9
)

/* 선택한 대주제 인덱스 */
// 0 -> 연애운
// 1 -> 학업운
// 2 -> 소망운
// 3 -> 직업운
// 4 -> 오늘의 운세
// 5 -> 궁합 운세
var pickedTopicNumber = 0

val tarotInputDto = TarotInputDto("", "", "", arrayListOf(0, 0, 0))     // 타로 결과를 맏기 위해 입력한 데이터



val dummyFullResult = "당신에게는 어려운 상황에서도 포기하지 않고 끝까지 이겨내는 힘이 있습니다. 포기하지 않고 매달 100만원씩 적금을 들고 있는 것이 그 예시겠네요. 하지만, 때로는 지나친 의욕 때문에 무리한 목표를 세우다가 실패로 이어지는 경우도 있지요. \n" +
        " 따라서, 지금까지의 생활 패턴을 유지하면서 안정감을 추구하려는 마음가짐이 중요합니다. 그리고, 다른 사람들을 위해 헌신하거나 배려하는 자세 역시 꼭 필요해요. 자신의 목표만 바라보기보다, 다른 사람에게 양보하는 마음가짐을 가지고 인생의 균형을 지켜가는 것이 중요합니다. \n" +
        " 이러한 부분들이 잘 지켜진다면, 조만간 경제적으로 여유로워질 기회가 찾아올 것입니다. 30살이 되기 전에 1억을 모으는 것도 가능할지도 몰라요!"

val dummySummary = "당신에게는 어려운 상황에서도 포기하지 않고 끝까지 이겨내는 힘이 있습니다."

// 서버에서 받은 새 타로 데이터
var tarotOutputDto = TarotOutputDto(
        "0",
        0,
        arrayListOf(0, 1, 2),
        "2024-01-14T12:38:23.000Z",
        arrayListOf(
                CardResultData(arrayListOf("keyword1", "keyword2", "keyword3", "keyword3"), "어려움이나 역경 등 힘든 상황에서도 포기하지 않고 끝까지 이겨내는 힘이 필요합니다."),
                CardResultData(arrayListOf("keyword4", "keyword5", "keyword6", "keyword3"), "하면서 안정감을 추구하려는 마음가짐이 중요합니다. 그리고, 다른 "),
                CardResultData(arrayListOf("keyword7", "keyword8", "keyword9", "keyword3"), " 않고 매달 100만원씩 적금을 들고 있는 것이 그 예시겠네요. 하지만, 때로는 지")
        ),
        OverallResultData(dummySummary, dummyFullResult)
)

var partnerTarotOutputDto = TarotOutputDto(
        "0",
        0,
        arrayListOf(5, 6, 7),
        "2024-01-14T12:38:23.000Z",
        arrayListOf(
                CardResultData(arrayListOf("keyword1", "keyword2", "keyword3", "keyword3"), "까지 이겨내는 힘이 있습니다. 포기하지 않고 매달 100만원씩"),
                CardResultData(arrayListOf("keyword4", "keyword5", "keyword6", "keyword3"), " 다른 사람에게 양보하는 마음가짐을 가지고 인생의 균형을 지켜가는"),
                CardResultData(arrayListOf("keyword7", "keyword8", "keyword9", "keyword3"), "질 기회가 찾아올 것입니다. 30살이 되기 전에 1억을 모으는 것도 가능할지도 몰라요!")
        ),
        OverallResultData(dummySummary, dummyFullResult)
)



var selectedTarotResult = TarotOutputDto("0", 0, arrayListOf(), "", arrayListOf(), null)        // 리스트에서 선택한 타로 데이터

var sharedTarotResult = TarotOutputDto("0", 0, arrayListOf(), "", arrayListOf(), null)          // 공유하기로 받은 타로 데이터

var myTarotResults = arrayListOf<TarotOutputDto>()

val entireCards = arrayListOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21)
fun getRandomCards(): List<Int> { return entireCards.toMutableList().shuffled() }

val harmonyViewModel = HarmonyViewModel()
val loadingViewModel = LoadingViewModel()
val chatViewModel = ChatViewModel()
val fortuneViewModel = FortuneViewModel()
val questionInputViewModel = QuestionInputViewModel()
