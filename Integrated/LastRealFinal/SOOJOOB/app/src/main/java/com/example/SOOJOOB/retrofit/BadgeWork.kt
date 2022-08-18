package com.example.SOOJOOB.retrofit

import android.util.Log
import retrofit2.Call
import retrofit2.Response

class BadgeWork() {
    fun getMyBadge(completion: (ArrayList<Badge>?) -> Unit) {
        val service = RetrofitAPI.badgeService



        // RequestBody 생성
//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.

        service.getUserBadge()
            .enqueue(object : retrofit2.Callback<BadgesResponseBody> {
                override fun onResponse(
                    call: Call<BadgesResponseBody>,
                    response: Response<BadgesResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("뱃지 불러오기 성공", "$result")
                        var badges = result?.data
                        completion(badges as ArrayList<Badge>?)

                    }
                }

                override fun onFailure(call: Call<BadgesResponseBody>, t: Throwable) {
                    Log.d("뱃지 불러오기 실패", t.message.toString())
                }
            })


    }

    fun getNoBadge(completion: (ArrayList<Badge>?) -> Unit) {
        val service = RetrofitAPI.badgeService


        // RequestBody 생성
//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.

        service.getNoBadges()
            .enqueue(object : retrofit2.Callback<BadgesResponseBody> {
                override fun onResponse(
                    call: Call<BadgesResponseBody>,
                    response: Response<BadgesResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("미보유 뱃지 불러오기 성공", "$result")
                        var badges = result?.data
                        completion(badges as ArrayList<Badge>?)

                    }
                }

                override fun onFailure(call: Call<BadgesResponseBody>, t: Throwable) {
                    Log.d("미보유 뱃지 불러오기 실패", t.message.toString())
                }
            })


    }

    fun getRank(completion: (ArrayList<RankData>?) -> Unit) {
        val service = RetrofitAPI.badgeService


        // RequestBody 생성
//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.

        service.getUserRank()
            .enqueue(object : retrofit2.Callback<RankResponseBody> {
                override fun onResponse(
                    call: Call<RankResponseBody>,
                    response: Response<RankResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("유저 순위 불러오기 성공", "$result")
                        var ranks = result?.data
                        completion(ranks as ArrayList<RankData>?)

                    }
                }

                override fun onFailure(call: Call<RankResponseBody>, t: Throwable) {
                    Log.d("유저 순위 불러오기 실패", t.message.toString())
                }
            })


    }

}
