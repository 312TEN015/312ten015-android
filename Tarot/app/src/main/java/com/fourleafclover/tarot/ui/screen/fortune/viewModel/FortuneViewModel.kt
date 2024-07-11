package com.fourleafclover.tarot.ui.screen.fortune.viewModel

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.SubjectDream
import com.fourleafclover.tarot.SubjectHarmony
import com.fourleafclover.tarot.SubjectJob
import com.fourleafclover.tarot.SubjectLove
import com.fourleafclover.tarot.SubjectStudy
import com.fourleafclover.tarot.SubjectToday
import com.fourleafclover.tarot.data.TarotInputDto
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.data.TarotSubjectData

class FortuneViewModel : ViewModel() {
    /* 선택한 대주제 인덱스 */
    // 0 -> 연애운
    // 1 -> 학업운
    // 2 -> 소망운
    // 3 -> 직업운
    // 4 -> 오늘의 운세
    // 5 -> 궁합 운세
    private var _pickedTopicNumber: Int? = null
    val pickedTopicNumber
        get() = _pickedTopicNumber ?: 0


    private var _pickedTopicSubject: TarotSubjectData? = null
    val pickedTopicSubject: TarotSubjectData
        get() = _pickedTopicSubject ?: TarotSubjectData()

    // ---------------------------------------------------------------------------------------------

    private var firstAnswer = ""
    private var secondAnswer = ""
    private var thirdAnswer = ""

    private var pickedCards = arrayListOf<Int>()

    // ---------------------------------------------------------------------------------------------

    private var tarotResult: TarotOutputDto? = null

    // ---------------------------------------------------------------------------------------------

    fun setPickedTopic(topicNumber: Int) {
        _pickedTopicNumber = topicNumber
        _pickedTopicSubject = when (topicNumber) {
            0 -> SubjectLove
            1 -> SubjectStudy
            2 -> SubjectDream
            3 -> SubjectJob
            4 -> SubjectToday
            5 -> SubjectHarmony
            else -> {
                Log.e(
                    "tarotError",
                    "error getPickedTopic(). pickedTopicNumber: ${com.fourleafclover.tarot.pickedTopicNumber} selectedTarotResult: ${com.fourleafclover.tarot.pickedTopicNumber}"
                )
                TarotSubjectData()
            }
        }
    }

    fun getTarotInputDto() = TarotInputDto(firstAnswer, secondAnswer, thirdAnswer, pickedCards)

    fun addPickedCard(cardNumber: Int) = pickedCards.add(cardNumber)

    fun setTarotResult(result: TarotOutputDto) {
        tarotResult = result
    }

    fun getSubjectImoji(localContext: Context):String {
        val resources: Resources = localContext.resources
        return when(_pickedTopicNumber){
            0 -> resources.getString(R.string.imoji_love)
            1 -> resources.getString(R.string.imoji_study)
            2 -> resources.getString(R.string.imoji_dream)
            3 -> resources.getString(R.string.imoji_job)
            else -> {
                Log.e("tarotError", "error getPath(). pickedTopicNumber: ${com.fourleafclover.tarot.pickedTopicNumber}")
                ""
            }
        }
    }

    fun getCardImageId(localContext: Context, cardNumber: String): Int {
        val resources: Resources = localContext.resources
        return resources.getIdentifier("tarot_$cardNumber", "drawable", localContext.packageName)
    }
}