package com.fourleafclover.tarot.data


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TarotService {
    @POST("tarot/{path}")
    fun postTarotResult(@Body tarotInputDto: TarotInputDto, @Path("path") path: String): Call<TarotOutputDto>

    @POST("tarot/my")
    fun getMyTarotResult(@Body tarotIds: TarotIdsInputDto): Call<ArrayList<TarotOutputDto>>
}