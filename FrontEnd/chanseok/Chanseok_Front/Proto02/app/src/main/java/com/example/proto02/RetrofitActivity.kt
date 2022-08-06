package com.example.proto02

import android.app.Person
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service = retrofit.create(RetrofitService::class.java)

        // GET 요청
        service.getStudentList().enqueue(object : Callback<ArrayList<PersonFromServer>> {
            override fun onFailure(call: Call<ArrayList<PersonFromServer>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ArrayList<PersonFromServer>>,
                response: Response<ArrayList<PersonFromServer>>
            ) {
                if (response.isSuccessful) {
                    val personList = response.body()
                    Log.d("retrofitt", "res : " + personList?.get(0)?.age)

                    val code = response.code()
                    Log.d("retrofitt","code : " + code)

                    val error = response.errorBody()
                    val header = response.headers()
                    Log.d("retrofitt", "header : " + header)
                }
            }
        })

        // POST 요청
//        val params = HashMap<String, Any>()
//        params.put("name", "박개뚱")
//        params.put("age", "20")
//        params.put("intro", "안녕안녕")
//        service.createStudent(params).enqueue(object : Callback<PersonFromServer> {
//            override fun onFailure(call: Call<PersonFromServer>, t: Throwable) {
//            }
//            override fun onResponse(
//                call: Call<PersonFromServer>,
//                response: Response<PersonFromServer>
//            ) {
//                if (response.isSuccessful) {
//                    val person = response.body()
//                    Log.d("retrofitt", "name : " + person?.name)
//                }
//            }
//        })
        // POST 요청 (2)
        val person = PersonFromServer(name="최개뚱", age=30, intro="얄루얄루")
        service.createStudentEasy(person).enqueue(object : Callback<PersonFromServer> {
            override fun onFailure(call: Call<PersonFromServer>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<PersonFromServer>,
                response: Response<PersonFromServer>
            ) {
                if (response.isSuccessful) {
                    val person = response.body()
                    Log.d("retrofitt", "name : " + person?.name)
                }
            }
        })
    }
}