package com.example.SOOJOOB.fragments


import com.example.SOOJOOB.databinding.FragmentRecordBinding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SOOJOOB.*
import com.example.SOOJOOB.RecyclerAdapter
import com.example.SOOJOOB.retrofit.PloggingGetResponseBody
import com.example.SOOJOOB.retrofit.PloggingResult
import com.example.SOOJOOB.retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Response

class RecordFragment : Fragment() {

    private lateinit var fBinding : FragmentRecordBinding
    private lateinit var trashSortBtn:Button
    private lateinit var dateSortBtn:Button

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val binding = FragmentRecordBinding.inflate(inflater, container, false)
//
//        fBinding = binding
//        recyler_view = binding.recylerView
//        println(binding.recylerView)
//
//
//        return fBinding?.root
//    }
//
//    override fun onDestroyView() {
////        fBinding = null
//        super.onDestroyView()
//    }

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
                        val result = response.body()
                        result?.let {
                            it.result?.let { it1 -> setAdapter(it1) }
                        }

                        val dateSort = response.body()?.result
                        trashSortBtn.setOnClickListener {
                            val trashSort = response.body()?.result?.sortedWith(compareBy<PloggingResult>{it.trashCount})?.reversed()
                            trashSort?.let {
                                it.let{it1 -> setAdapter(it1)}
                            }
                        }
                        dateSortBtn.setOnClickListener {
                            val dateSort = response.body()?.result?.reversed()
                            dateSort?.let {
                                it.let{it1 -> setAdapter(it1)}
                            }
                        }



                    }
                }

                override fun onFailure(call: Call<PloggingGetResponseBody>, t: Throwable) {
                    Log.d("플로깅  get 실패", t.message.toString())
                }
            })
    }

//
//    private fun setAdapter(PloggingList : List<Result>){
//        val mAdapter = RecyclerAdapter(PloggingList)
//        recyler_view.adapter = mAdapter
////        recyler_view.layoutManager = LayoutManager(this,2, GridLayoutManager.VERTICAL,
////            false)
//
//        recyler_view.setHasFixedSize(false)
//    }


    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private lateinit var recyler_view: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_record, container, false)
        return view
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recyler_view = itemView.findViewById(R.id.recyler_view)
        trashSortBtn = itemView.findViewById(R.id.trashSort)
        dateSortBtn = itemView.findViewById(R.id.dateSort)

        work()
    }


    private fun setAdapter(PloggingList : List<PloggingResult>){
        val mAdapter = RecyclerAdapter(PloggingList)
        recyler_view.adapter = mAdapter
        recyler_view.layoutManager = LinearLayoutManager(activity)

        recyler_view.setHasFixedSize(false)
    }

}
