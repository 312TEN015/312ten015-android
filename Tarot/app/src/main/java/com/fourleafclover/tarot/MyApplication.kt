package com.fourleafclover.tarot

import android.app.Application
import android.os.Build
import android.util.Log
import com.fourleafclover.tarot.network.TarotService
import com.fourleafclover.tarot.utils.PreferenceUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MyApplication: Application() {


    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var tarotService: TarotService

    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
//        prefs.deleteAllTarotResults()
//        prefs.deleteIsPickCardIndicateComplete()

        // 서버 초기화
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        tarotService = retrofit.create(TarotService::class.java)

//        Log.d("buildConfig", BuildConfig.BUILD_TYPE)



        super.onCreate()
    }
}
