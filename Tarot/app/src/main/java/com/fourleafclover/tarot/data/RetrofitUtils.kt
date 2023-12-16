package com.fourleafclover.tarot.data

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val okHttpClient = OkHttpClient().newBuilder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://52.78.64.39:3000")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val tarotService: TarotService = retrofit.create(TarotService::class.java)

fun getPath() : String {
    return when(pickedTopicNumber){
        0 -> "love"
        1 -> "study"
        2 -> "dream"
        3 -> "job"
        else -> {
            Log.e("tarotError", "error getPath(). pickedTopicNumber: $pickedTopicNumber")
            ""
        }
    }
}