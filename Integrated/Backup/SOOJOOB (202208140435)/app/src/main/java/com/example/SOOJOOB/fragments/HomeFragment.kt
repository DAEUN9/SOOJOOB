package com.example.SOOJOOB.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SOOJOOB.LoginActivity
import com.example.SOOJOOB.MapsActivity
import com.example.SOOJOOB.R
import com.example.SOOJOOB.RecyclerAdapter
import com.example.SOOJOOB.databinding.FragmentHomeBinding
import com.example.SOOJOOB.retrofit.*
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    private var fBinding : FragmentHomeBinding? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private lateinit var recyler_view: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        fBinding = binding


        binding.logout.setOnClickListener {
            LogoutWork().logout()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.nextMaps.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        val userWork = UserWork()
        userWork.work(completion = { status, username, trash, exp ->
            if (status in 200..300) {
                binding.homeUsername.text = username
                binding.homeTrash.text = trash.toString()
                binding.homeExp.text = exp.toString().substring(0, 4)
                binding.homeProgress.progress = exp!!.toInt()
            }
        })

        return fBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fBinding = null
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recyler_view = itemView.findViewById(R.id.recyler_view)

        work()
    }

    fun work(){
        val service = RetrofitAPI.ploggingService

        // gson.toJson 을 이용해서 Json 으로 변경


//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.

        service.getPlogging()
            .enqueue(object : retrofit2.Callback<PloggingGetResponseBody> {
                override fun onResponse(
                    call: Call<PloggingGetResponseBody>,
                    response: Response<PloggingGetResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()?.result?.reversed()
                        result?.let {
                            it?.let { it1 -> setAdapter(it1) }
                        }
//                        Log.d("플로깅 get 성공", "${result}")
//                        Log.d("타입 확인","${result?.result?.get(2)?.dateTime}")
//                        getText.text = "플로깅 날짜 : " + result?.result?.get(2)?.dateTime + "\n플로깅 시간 :  " + result?.result?.get(2)?.timeRecord + "\n쓰레기 수 :" + result?.result?.get(2)?.trashCount + "\n 플로깅 거리 : "  + result?.result?.get(2)?.distance
//                        date.text = result?.result?.get(2)?.dateTime
//                        distance.text = result?.result?.get(2)?.distance.toString()
//                        recTime.text = result?.result?.get(2)?.timeRecord.toString()
//                        trashCnt.text = result?.result?.get(2)?.trashCount.toString()
////                        println("이미지 타입" + result?.result?.get(2)?.ploggingImg?.javaClass)
//                        imageString = result?.result?.get(2)?.ploggingImg as String
//                        image.setImageBitmap(imageString.toBitmap())
//                        res = result?.result


                    }
                }

                override fun onFailure(call: Call<PloggingGetResponseBody>, t: Throwable) {
                    Log.d("플로깅  get 실패", t.message.toString())
                }
            })
    }

    private fun setAdapter(PloggingList : List<PloggingResult>){
        val mAdapter = RecyclerAdapter(PloggingList)
        recyler_view.adapter = mAdapter
        recyler_view.layoutManager = LinearLayoutManager(activity)

        recyler_view.setHasFixedSize(false)
    }


}