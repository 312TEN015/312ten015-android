package com.fourleafclover.tarot

import com.fourleafclover.tarot.constant.*
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.data.TarotSubjectData
import com.fourleafclover.tarot.ui.screen.fortune.viewModel.FortuneViewModel
import com.fourleafclover.tarot.ui.screen.fortune.viewModel.PickTarotViewModel
import com.fourleafclover.tarot.ui.screen.fortune.viewModel.QuestionInputViewModel
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.ChatViewModel
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.HarmonyShareViewModel
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.LoadingViewModel
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.ResultViewModel
import com.fourleafclover.tarot.ui.screen.my.viewmodel.MyTarotViewModel
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
        majorQuestion = harmonyMajorQuestion,
        primaryColor = gray_9
)

var sharedTarotResult = TarotOutputDto("0", 0, arrayListOf(), "", arrayListOf(), null)          // 공유하기로 받은 타로 데이터

val entireCards = arrayListOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21)
fun getRandomCards(): List<Int> { return entireCards.toMutableList().shuffled() }

val harmonyShareViewModel = HarmonyShareViewModel()
val loadingViewModel = LoadingViewModel()
val chatViewModel = ChatViewModel()
val fortuneViewModel = FortuneViewModel()
val questionInputViewModel = QuestionInputViewModel()
val pickTarotViewModel = PickTarotViewModel()
val resultViewModel = ResultViewModel()
val myTarotViewModel = MyTarotViewModel()
