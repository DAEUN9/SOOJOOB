package com.example.SOOJOOB.fragments

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SOOJOOB.*
import com.example.SOOJOOB.databinding.FragmentHomeBinding
import com.example.SOOJOOB.model.ModelWeather
import com.example.SOOJOOB.retrofit.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.gun0912.tedpermission.provider.TedPermissionProvider
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

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

//        binding.btnWeather.setOnClickListener{
//            val intent = Intent(activity, WeatherActivity::class.java)
//            startActivity(intent)
//        }

        val userWork = UserWork()
        userWork.work(completion = { status, username, trash, exp ->
            if (status in 200..300) {
                binding.homeUsername.text = username
                binding.homeTrash.text = trash.toString()
                binding.homeExp.text = exp.toString().substring(0, 4)
                binding.homeProgress.progress = exp!!.toInt()
            }
        })

        // 위치
        requestLocation(completion = {
                temp, sky ->
            println("temp :" + temp)
            println("sky :" + sky)
            binding.todayWhere.text = Constants.ADDRESS
            binding.todayTemp.text = temp
            binding.todayWeather.text = sky
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



    // <새로고침> 버튼 누를 때 위치 정보 & 날씨 정보 다시 가져오기
//    btnRefresh.setOnClickListener {
//        requestLocation()
//    }
}

fun setWeather(
    nx: Int = 84,
    ny: Int = 96,

    completion: (Temp: String, Sky: String) -> Unit
) {
    // 준비 단계 : base_date(발표 일자), base_time(발표 시각)
    // 현재 날짜, 시간 정보 가져오기
//    val nx = 1
//    val ny = 1
    val cal = Calendar.getInstance()
    var base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time) // 현재 날짜
    val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 시각
    val timeM = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 분
    // API 가져오기 적당하게 변환
    val base_time = Common().getBaseTime(timeH, timeM)
    // 현재 시각이 00시이고 45분 이하여서 baseTime이 2330이면 어제 정보 받아오기
    if (timeH == "00" && base_time == "2330") {
        cal.add(Calendar.DATE, -1).toString()
        base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
    }

    // 날씨 정보 가져오기
    // (한 페이지 결과 수 = 60, 페이지 번호 = 1, 응답 자료 형식-"JSON", 발표 날싸, 발표 시각, 예보지점 좌표)
    val call = ApiObject.retrofitService.GetWeather(60, 1, "JSON", base_date, base_time, nx=84, ny=96)

    // 비동기적으로 실행하기
    call.enqueue(object : retrofit2.Callback<WEATHER> {
        // 응답 성공 시
        override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
            if (response.isSuccessful) {
                // 날씨 정보 가져오기
                println(response.body())
                val it: List<ITEM> = response.body()!!.response.body.items.item

                // 현재 시각부터 1시간 뒤의 날씨 6개를 담을 배열
                val weatherArr = arrayOf(ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather())
                // 배열 채우기
                var index = 0
                val totalCount = response.body()!!.response.body.totalCount - 1
                for (i in 0..totalCount) {
                    index %= 6
                    when(it[i].category) {
                        "PTY" -> weatherArr[index].rainType = it[i].fcstValue     // 강수 형태
                        "REH" -> weatherArr[index].humidity = it[i].fcstValue     // 습도
                        "SKY" -> weatherArr[index].sky = it[i].fcstValue          // 하늘 상태
                        "T1H" -> weatherArr[index].temp = it[i].fcstValue         // 기온
                        else -> continue
                    }
                    index++
                }

                // 각 날짜 배열 시간 설정
                for (i in 0..5) weatherArr[i].fcstTime = it[i].fcstTime

                val weatherarr = weatherArr[0]
                println(weatherarr.fcstTime)
                val sky = weatherarr.sky
                val temp = weatherarr.temp
                val rain = weatherarr.rainType


                val Temp = temp
                val Sky = getSky(sky, rain)

                completion(Temp, Sky)

                // 리사이클러 뷰에 데이터 연결
//                    weatherRecyclerView.adapter = WeatherAdapter(weatherArr)
                // 토스트 띄우기
//                    Toast.makeText(applicationContext, it[0].fcstDate + ", " + it[0].fcstTime + "의 날씨 정보입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 응답 실패 시
        override fun onFailure(call: Call<WEATHER>, t: Throwable) {
            Log.d("api fail", t.message.toString())
        }
    })
}

// 내 현재 위치의 위경도를 격자 좌표로 변환하여 해당 위치의 날씨정보 설정하기
private fun requestLocation(completion: (Temp: String, Sky: String) -> Unit):Unit {
    val locationClient = LocationServices.getFusedLocationProviderClient(
        TedPermissionProvider.context
    )

    try {
        // 나의 현재 위치 요청
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 60 * 10000    // 요청 간격(10초)
        }
        val locationCallback = object : LocationCallback() {
            // 요청 결과
            override fun onLocationResult(p0: LocationResult):Unit {

                p0.let {
                    for (location in it.locations) {
                        // 현재 위치의 위경도를 격자 좌표로 변환

                        val curPoint = Common().dfs_xy_conv(location.latitude, location.longitude)

                        // 오늘 날짜 텍스트뷰 설정
//                            tvDate.text = SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time) + " 날씨"
                        // nx, ny지점의 날씨 가져와서 설정하기
                        setWeather(curPoint!!.x, curPoint!!.y

                        ) { temp, sky ->
                            completion(temp, sky)

                        }
                    }
                }
            }
        }

        Looper.myLooper()?.let{
            locationClient.requestLocationUpdates(locationRequest, locationCallback,
                it)
        }
        // 내 위치 실시간으로 감지
        // locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    } catch (e : SecurityException) {
        e.printStackTrace()
    }
}




// 강수 형태
fun getRainType(rainType : String) : String {
    return when(rainType) {
        "0" -> "없음"
        "1" -> "비"
        "2" -> "비/눈"
        "3" -> "눈"
        "4" -> "소나기"
        "5" -> "빗방울"
        "6" -> "빗방울/눈날림"
        "7" -> "눈날림"
        else -> "오류 rainType : " + rainType
    }
}

// 하늘 상태
fun getSky(sky : String, rain : String) : String {
    return when(sky) {
        "1" -> "맑음"
        "3" -> "구름 많음"
        "4" -> "흐림"
        else -> getRainType(rain)
    }
}

