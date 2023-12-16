package com.fourleafclover.tarot.data


// 타로 고민 입력 후 request
data class TarotInputDto(
    var firstAnswer: String,
    var secondAnswer: String,
    var thirdAnswer: String,
    var cards: ArrayList<Int> = arrayListOf()
)

// 타로 고민 결과 response
data class TarotOutputDto(
    var id: String,
    var cardResults: ArrayList<CardResultData>?,    // 카드설명
    var overallResult: OverallResultData?   // 총평
)

// 뽑은 카드 세장에 대한 키워드, 설명
data class CardResultData(
    val keywords: ArrayList<String>,
    val description: String,
)

// 뽑은 타로 결과에 대한 요약문, 전문
data class OverallResultData(
    val summery: String,
    val full: String
)