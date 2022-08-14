package com.example.SOOJOOB

import android.annotation.SuppressLint
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SOOJOOB.adapter.WeatherAdapter
import com.example.SOOJOOB.databinding.ActivityWeatherBinding
import com.example.SOOJOOB.model.ModelWeather
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    lateinit var weatherRecyclerView : RecyclerView
    lateinit var tvDate : TextView
   // lateinit var binding: ActivityWeatherBinding
    lateinit var base_date : String  // 발표 일자
    lateinit var base_time : String  // 발표 시각

    private var curPoint : Point? = null    // 현재 위치의 격자 좌표를 저장할 포인트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityWeatherBinding.inflate(layoutInflater)
//       setContentView(binding.root)
        setContentView(R.layout.activity_weather)

        tvDate = findViewById(R.id.tvDate)                                // 오늘 날짜 텍스트뷰
//        weatherRecyclerView = findViewById(R.id.weatherRecyclerView)  // 날씨 리사이클러 뷰
        val btnRefresh = findViewById<Button>(R.id.btnRefresh)                          // 새로고침 버튼

        // 리사이클러 뷰 매니저 설정
//        weatherRecyclerView.layoutManager = LinearLayoutManager(this@WeatherActivity)
        // 내 위치 위경도 가져와서 날씨 정보 설정하기
        requestLocation()

        // <새로고침> 버튼 누를 때 위치 정보 & 날씨 정보 다시 가져오기
        btnRefresh.setOnClickListener {
            requestLocation()
        }
    }



    // 날씨 가져와서 설정하기
    private fun setWeather(nx : Int, ny : Int) {
        // 준비 단계 : base_date(발표 일자), base_time(발표 시각)
        // 현재 날짜, 시간 정보 가져오기
        val nx = 1
        val ny = 1
        val cal = Calendar.getInstance()
        base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time) // 현재 날짜
        val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 시각
        val timeM = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 분
        // API 가져오기 적당하게 변환
        base_time = Common().getBaseTime(timeH, timeM)
        // 현재 시각이 00시이고 45분 이하여서 baseTime이 2330이면 어제 정보 받아오기
        if (timeH == "00" && base_time == "2330") {
            cal.add(Calendar.DATE, -1).toString()
            base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        }

        // 날씨 정보 가져오기
        // (한 페이지 결과 수 = 60, 페이지 번호 = 1, 응답 자료 형식-"JSON", 발표 날싸, 발표 시각, 예보지점 좌표)
        val call = ApiObject.retrofitService.GetWeather(60, 1, "JSON", base_date, base_time, nx, ny)

        // 비동기적으로 실행하기
        call.enqueue(object : retrofit2.Callback<WEATHER> {
            // 응답 성공 시
            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
                if (response.isSuccessful) {
                    // 날씨 정보 가져오기
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
                    val sky = weatherarr.sky
                    val temp = weatherarr.temp
                    val rain = weatherarr.rainType

                    val t = findViewById<TextView>(R.id.today_temp)
                    val s = findViewById<TextView>(R.id.today_weather)

                    t.text = temp
                    s.text = getSky(sky, rain)

                    // 리사이클러 뷰에 데이터 연결
//                    weatherRecyclerView.adapter = WeatherAdapter(weatherArr)
                    // 토스트 띄우기
//                    Toast.makeText(applicationContext, it[0].fcstDate + ", " + it[0].fcstTime + "의 날씨 정보입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            // 응답 실패 시
            override fun onFailure(call: Call<WEATHER>, t: Throwable) {
                val tvError = findViewById<TextView>(R.id.tvError)
                tvError.text = "api fail : " +  t.message.toString() + "\n 다시 시도해주세요."
                tvError.visibility = View.VISIBLE
                Log.d("api fail", t.message.toString())
            }
        })
    }

    // 내 현재 위치의 위경도를 격자 좌표로 변환하여 해당 위치의 날씨정보 설정하기
    private fun requestLocation() {
        val locationClient = LocationServices.getFusedLocationProviderClient(this@WeatherActivity)

        try {
            // 나의 현재 위치 요청
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 60 * 1000    // 요청 간격(1초)
            }
            val locationCallback = object : LocationCallback() {
                // 요청 결과
                override fun onLocationResult(p0: LocationResult) {
                    p0.let {
                        for (location in it.locations) {
                            // 현재 위치의 위경도를 격자 좌표로 변환
                            curPoint = Common().dfs_xy_conv(location.latitude, location.longitude)

                            // 오늘 날짜 텍스트뷰 설정
//                            tvDate.text = SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time) + " 날씨"
                            // nx, ny지점의 날씨 가져와서 설정하기
                            setWeather(curPoint!!.x, curPoint!!.y)
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
}