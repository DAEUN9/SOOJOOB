package com.example.SOOJOOB.retrofit

import android.util.Log
import retrofit2.Call
import retrofit2.Response

class BadgeWork() {
    fun getMyBadge(userId: String?, completion: (ArrayList<Badge>?) -> Unit) {
        val service = RetrofitAPI.badgeService



        // RequestBody 생성
//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.
        if (userId != null) {
            service.getUserBadge(userId)
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
    }

    fun getNoBadge(userId: String?, completion: (ArrayList<Badge>?) -> Unit) {
        val service = RetrofitAPI.badgeService


        // RequestBody 생성
//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.
        if (userId != null) {
            service.getNoBadges(userId)
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
    }

}
