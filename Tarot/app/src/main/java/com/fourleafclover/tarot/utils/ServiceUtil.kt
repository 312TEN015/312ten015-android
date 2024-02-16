package com.fourleafclover.tarot.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.data.TarotIdsInputDto
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.myTarotResults
import com.fourleafclover.tarot.sharedTarotResult
import com.fourleafclover.tarot.tarotInputDto
import com.fourleafclover.tarot.tarotOutputDto
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* 타로 결과 여러개 GET */
fun getTarotRequest(
    localContext: Context,
    tarotResultArray: ArrayList<String>
) {
    Log.d("", tarotResultArray.joinToString(" "))
    MyApplication.tarotService.getMyTarotResult(TarotIdsInputDto(tarotResultArray))
        .enqueue(object : Callback<ArrayList<TarotOutputDto>> {
            override fun onResponse(
                call: Call<ArrayList<TarotOutputDto>>,
                response: Response<ArrayList<TarotOutputDto>>
            ) {

                Log.d("", "onResponse--------")
                if (response.body() == null){
                    Toast.makeText(localContext, "response null", Toast.LENGTH_SHORT).show()
                    return
                }
//                Log.d("", response.body().toString())
                myTarotResults = arrayListOf()

                for (item in response.body()!!){
                    myTarotResults.add(item)
                    Log.d("", "${item.toString()}--------")
                }
            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
                Log.d("", "onFailure--------!")
                Log.d("", "${t.cause}--------!")
                Log.d("", "${t.message}--------!")
                Log.d("", "${t.stackTrace}--------!")
            }
        })

}


/* 타로 결과 단일 GET */
fun getSharedTarotRequest(
    localContext: Context,
    navController: NavHostController,
    tarotId: String
) {

    MyApplication.tarotService.getMyTarotResult(TarotIdsInputDto(arrayListOf(tarotId)))
        .enqueue(object : Callback<ArrayList<TarotOutputDto>> {
            override fun onResponse(
                call: Call<ArrayList<TarotOutputDto>>,
                response: Response<ArrayList<TarotOutputDto>>
            ) {

                Log.d("", "onResponse--------")
                if (response.body() == null){
                    Toast.makeText(localContext, "response null", Toast.LENGTH_SHORT).show()
                    return
                }

                Log.d("", response.body()!!.toString())
                Log.d("", response.body()!!.toString())

                sharedTarotResult = response.body()!![0]
                navigateInclusive(navController, ScreenEnum.ShareDetailScreen.name)
            }

            override fun onFailure(call: Call<ArrayList<TarotOutputDto>>, t: Throwable) {
                Log.d("", "onFailure--------!")
                Log.d("", "${t.cause}--------!")
                Log.d("", "${t.message}--------!")
                Log.d("", "${t.stackTrace}--------!")
            }
        })

}


/* 타로 결과 요청 POST */
fun sendRequest(localContext: Context, navController: NavHostController) {

    MyApplication.tarotService.postTarotResult(tarotInputDto, getPath())
        .enqueue(object : Callback<TarotOutputDto>{
            override fun onResponse(
                call: Call<TarotOutputDto>,
                response: Response<TarotOutputDto>
            ) {

                Log.d("", "onResponse--------")
                if (response.body() == null){
                    Toast.makeText(localContext, "response null", Toast.LENGTH_SHORT).show()
                    return
                }

                tarotOutputDto = response.body()!!

                Log.d("", "${tarotOutputDto.cardResults}--------")
                Log.d("", "${tarotOutputDto.overallResult}--------")


                navigateInclusive(navController, ScreenEnum.ResultScreen.name)
            }

            override fun onFailure(call: Call<TarotOutputDto>, t: Throwable) {
                Log.d("", "onFailure--------!")
                Log.d("", "${t.cause}--------!")
                Log.d("", "${t.message}--------!")
                Log.d("", "${t.stackTrace}--------!")
            }
        })

}