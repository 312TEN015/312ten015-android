package com.fourleafclover.tarot.data

import android.os.Build
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone


// 타로 고민 입력 후 request
data class TarotInputDto(
    var firstAnswer: String,
    var secondAnswer: String,
    var thirdAnswer: String,
    var cards: ArrayList<Int> = arrayListOf()
)

// 타로 고민 결과 response
data class TarotOutputDto(
    var tarotId: String,
    var tarotType: Int, // 분류
    var cards: ArrayList<Int>,
    @SerializedName("createdAt") var createdAt_: String, // 날짜
    var cardResults: ArrayList<CardResultData>?,    // 카드설명
    var overallResult: OverallResultData?   // 총평
) {
    val createdAt: String
        get() {
            createdAt_ = createdAt_.replace("(Coordinated Universal Time)", "")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val engTimeFormat = DateTimeFormatter.ofPattern("E MMM d u H:m:s 'GMT'Z", Locale.ENGLISH)
                val dateTime = OffsetDateTime.parse(createdAt_, engTimeFormat)
                val koreaTimeFormat = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일", Locale.KOREA)
                return dateTime.format(koreaTimeFormat)
            }else{
                val d = Date(createdAt_)
                val sdf1 = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
                sdf1.timeZone = TimeZone.getTimeZone("GMT")
                return sdf1.format(d)
            }
        }
}

// 뽑은 카드 세장에 대한 키워드, 설명
data class CardResultData(
    val keywords: ArrayList<String>,
    val description: String,
)

// 뽑은 타로 결과에 대한 요약문, 전문
data class OverallResultData(
    val summary: String,
    val full: String
)

data class TarotIdsInputDto(
    val tarotIds : ArrayList<String> = arrayListOf<String>()
)

data class TarotIdsOutputDto(
    val tarotIds : ArrayList<TarotOutputDto> = arrayListOf<TarotOutputDto>()
)