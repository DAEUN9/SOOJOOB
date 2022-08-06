package com.example.polylinetest01


import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
//import org.jetbrains.anko.alert
//import org.jetbrains.anko.noButton
//import org.jetbrains.anko.yesButton
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.SystemClock
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import java.util.*
import com.example.polylinetest01.MapsActivity.StartLocationCallBack as StartLocationCallBack

//import kotlinx.android.synthetic.main.activity_maps.*
//import org.jetbrains.anko.toast



class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var sumTime = ""
    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var index :Int = 1
    private lateinit var secText: TextView
    private lateinit var milliText: TextView
    private lateinit var startBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var recordBtn: Button
    private lateinit var end_button: Button


    private val polyLineOptions=PolylineOptions().width(5f).color(Color.RED) // 이동경로를 그릴 선
    private lateinit var mMap: GoogleMap // 마커, 카메라 지정을 위한 구글 맵 객체
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient // 위치 요청 메소드 담고 있는 객체
    private lateinit var locationRequest:LocationRequest // 위치 요청할 때 넘겨주는 데이터에 관한 객체
    private lateinit var locationCallback: MyLocationCallBack // 위치 확인되고 호출되는 객체
    private var locationCallback2 = StartLocationCallBack()


    // 위치 정보를 얻기 위한 각종 초기화
    private fun locationInit(){
        // FusedLocationProviderClient 객체 생성
        // 이 객체의 메소드를 통해 위치 정보를 요청할 수 있음
        fusedLocationProviderClient=FusedLocationProviderClient(this)
        // 위치 갱신되면 호출되는 콜백 생성
        locationCallback=MyLocationCallBack()
        // (정보 요청할 때 넘겨줄 데이터)에 관한 객체 생성
        locationRequest=LocationRequest()
        locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY // 가장 정확한 위치를 요청한다,
//        locationRequest.interval=10000 // 위치를 갱신하는데 필요한 시간 <밀리초 기준>
//        locationRequest.fastestInterval=5000 // 다른 앱에서 위치를 갱신했을 때 그 정보를 가져오는 시간 <밀리초 기준>
    }
    // 위치 정보를 찾고 나서 인스턴스화되는 클래스
    inner class MyLocationCallBack:LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            // lastLocation프로퍼티가 가리키는 객체 주소를 받는다.
            // 그 객체는 현재 경도와 위도를 프로퍼티로 갖는다.
            // 그러나 gps가 꺼져 있거나 위치를 찾을 수 없을 때는 lastLocation은 null을 가진다.
            val location = locationResult?.lastLocation
//            //  gps가 켜져 있고 위치 정보를 찾을 수 있을 때 다음 함수를 호출한다. <?. : 안전한 호출>
            location?.run{
//                // 현재 경도와 위도를 LatLng메소드로 설정한다.
                val latLng=LatLng(latitude,longitude)
//                // 카메라를 이동한다.(이동할 위치,줌 수치)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20f))
//                // 마커를 추가한다.
                mMap.addMarker(MarkerOptions().position(latLng).title("Changed Location"))
                // polyLine에 좌표 추가
//                polyLineOptions.add(latLng)
//                // 선 그리기
//                mMap.addPolyline(polyLineOptions)
            }
        }
    }

    // 위치 정보를 얻기 위한 각종 초기화
    private fun locationstart(){
        // FusedLocationProviderClient 객체 생성
        // 이 객체의 메소드를 통해 위치 정보를 요청할 수 있음
        fusedLocationProviderClient=FusedLocationProviderClient(this)
        // 위치 갱신되면 호출되는 콜백 생성
        locationCallback2 = StartLocationCallBack()
        // (정보 요청할 때 넘겨줄 데이터)에 관한 객체 생성
        locationRequest=LocationRequest()
        locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY // 가장 정확한 위치를 요청한다,
        locationRequest.interval=10000 // 위치를 갱신하는데 필요한 시간 <밀리초 기준>
        locationRequest.fastestInterval=5000 // 다른 앱에서 위치를 갱신했을 때 그 정보를 가져오는 시간 <밀리초 기준>
    }

    // 위치 정보를 찾고 나서 인스턴스화되는 클래스
    inner class StartLocationCallBack:LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            // lastLocation프로퍼티가 가리키는 객체 주소를 받는다.
            // 그 객체는 현재 경도와 위도를 프로퍼티로 갖는다.
            // 그러나 gps가 꺼져 있거나 위치를 찾을 수 없을 때는 lastLocation은 null을 가진다.
            val location = locationResult?.lastLocation
            //  gps가 켜져 있고 위치 정보를 찾을 수 있을 때 다음 함수를 호출한다. <?. : 안전한 호출>
            location?.run{
                // 현재 경도와 위도를 LatLng메소드로 설정한다.
                val latLng=LatLng(latitude,longitude)
                // 카메라를 이동한다.(이동할 위치,줌 수치)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20f))
                // 마커를 추가한다.
//                mMap.addMarker(MarkerOptions().position(latLng).title("Changed Location"))
                // polyLine에 좌표 추가
                polyLineOptions.add(latLng)
                // 선 그리기
                mMap.addPolyline(polyLineOptions)
            }
        }
    }





    // 이 메소드부터 프로그램 시작
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // 화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        // 세로 모드로 화면 고정
        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // 위치 정보를 얻기 위한 각종 초기화
        locationInit()
//        locationstart()
        // 프래그먼트 매니저로부터 SupportMapFragment프래그먼트를 얻는다. 이 프래그먼트는 지도를 준비하는 기능이 있다.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        // 지도가 준비되면 알림을 받는다. (아마, get함수에서 다른 함수를 호출해서 처리하는 듯)
        mapFragment.getMapAsync(this)

        secText = findViewById(R.id.secText)
        milliText = findViewById(R.id.milliText)
        startBtn = findViewById(R.id.startBtn)
//        resetBtn = findViewById(R.id.resetBtn)
//        recordBtn = findViewById(R.id.recordBtn)
//        lap_Layout = findViewById(R.id.lap_Layout)

        //버튼 클릭 리스너
        startBtn.setOnClickListener {
//            isRunning = !isRunning
//            if (isRunning) start() else pause()
            start()
        }
//        resetBtn.setOnClickListener {
//            reset()
//        }
//        recordBtn.setOnClickListener {
//            if(time!=0) lapTime()
//        }

        end_button = findViewById(R.id.end_button)

        val endIntent = Intent(this, EndActivity::class.java) // 인텐트를 생성

        end_button.setOnClickListener { // 버튼 클릭시 할 행동
            pause()
            endIntent.putExtra("timeRecord",time)
            startActivity(endIntent)  // 화면 전환하기
            finish()
        }
    }



    // 프로그램이 켜졌을 때만 위치 정보를 요청한다
    override fun onResume(){
        super.onResume()
        // 사용자에게 gps키라고 알리기
        Toast.makeText(this,"이 앱은 GPS(위치)를 켜야 이용 가능합니다!", Toast.LENGTH_SHORT).show()
        // '앱이 gps사용'에 대한 권한이 있는지 체크
//        // 거부됐으면 showPermissionInfoDialog(알림)메소드를 호출, 승인됐으면 addLocationListener(위치 요청)메소드를 호출
        permissionCheck(cancel={showPermissionInfoDialog()},
            ok={addLocationListener()})
        permissionCheck(cancel={showPermissionInfoDialog()},
            ok={addLocationListener2()})
    }
    // 프로그램이 중단되면 위치 요청을 삭제한다
    override fun onPause(){
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        fusedLocationProviderClient.removeLocationUpdates(locationCallback2)

    }
    // 위치 요청 메소드
    @SuppressLint("MissingPermission")
    private fun addLocationListener(){
        // 위치 정보 요청
        // (정보 요청할 때 넘겨줄 데이터)에 관한 객체, 위치 갱신되면 호출되는 콜백, 특정 스레드 지정(별 일 없으니 null)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null)

    }
    @SuppressLint("MissingPermission")
    private fun addLocationListener2(){
        // 위치 정보 요청
        // (정보 요청할 때 넘겨줄 데이터)에 관한 객체, 위치 갱신되면 호출되는 콜백, 특정 스레드 지정(별 일 없으니 null)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback2,null)

    }


    override fun onMapReady(googleMap: GoogleMap) {
//         googleMap객체 생성
//         googleMap을 이용하여 마커나 카메라,선 등을 조정하는 것이다
        mMap = googleMap
//        // 서울을 기준으로 위도,경도 지정
//        val GUMI = LatLng(36.107060,128.418309)
////        // MarkerOptions에 위치 정보를 넣을 수 있음
//        val markerOptions = MarkerOptions()
//        markerOptions.position(GUMI)
//        markerOptions.title("구미")
//        markerOptions.snippet("SSAFY교육장")
////        // 마커 추가
////        mMap.addMarker(markerOptions)
////        // 카메라 구미로 이동
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(GUMI))
////        // 10만큼 줌인 하기
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(20f))
//        // P.s. onMapReady가 호출된 후에 위치 수정이 가능하다!!
//        polyLineOptions.add(GUMI)
    }




    // 권환 관련 메서드
    private val gps_request_code=1000 // gps에 관한 권한 요청 코드(번호)
    private fun permissionCheck(cancel:()->Unit,ok:()->Unit){
        // 앱에 GPS이용하는 권한이 없을 때
        // <앱을 처음 실행하거나, 사용자가 이전에 권한 허용을 안 했을 때 성립>
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){  // <PERMISSION_DENIED가 반환됨>
            // 이전에 사용자가 앱 권한을 허용하지 않았을 때 -> 왜 허용해야되는지 알람을 띄움
            // shouldShowRequestPermissionRationale메소드는 이전에 사용자가 앱 권한을 허용하지 않았을 때 ture를 반환함
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                cancel()
            }
            // 앱 처음 실행했을 때
            else
            // 권한 요청 알림
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),gps_request_code)        }
        // 앱에 권한이 허용됨
        else
            ok()
    }
    // 사용자가 권한 요청<허용,비허용>한 후에 이 메소드가 호출됨
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            // (gps 사용에 대한 사용자의 요청)일 때
            gps_request_code->{
                // 요청이 허용일 때
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    addLocationListener()
                // 요청이 비허용일 때
//                else{
//                    toast("권한 거부 됨")
//                    finish() }
            }
        }
    }
    // 사용자가 이전에 권한을 거부했을 때 호출된다.
    private fun showPermissionInfoDialog(){
//        alert("지도 정보를 얻으려면 위치 권한이 필수로 필요합니다",""){
//            yesButton{
//                // 권한 요청
                ActivityCompat.requestPermissions(this@MapsActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),gps_request_code)
//            }
//            noButton { toast("권한 거부 됨")
//                finish() }
//        }.show()
    }



    private fun start() {
        addLocationListener2()
        locationstart()
        startBtn.text ="중지"
        timerTask = kotlin.concurrent.timer(period = 10) { //반복주기는 peroid 프로퍼티로 설정, 단위는 1000분의 1초 (period = 1000, 1초)
            time++ // period=10으로 0.01초마다 time를 1씩 증가하게 됩니다
            val sec = time / 100 // time/100, 나눗셈의 몫 (초 부분)
            val milli = time % 100 // time%100, 나눗셈의 나머지 (밀리초 부분)

            sumTime = "$sec" + "\"" + "$milli"
            // UI조작을 위한 메서드
            runOnUiThread {
                secText.text = "$sec" + "\""
                milliText.text = "$milli"

            }
        }
    }

    private fun pause() {
        startBtn.text ="재실행"
        timerTask?.cancel();
    }

    private fun reset() {
        timerTask?.cancel() // timerTask가 null이 아니라면 cancel() 호출

        time = 0 // 시간저장 변수 초기화
        isRunning = false // 현재 진행중인지 판별하기 위한 Boolean변수 false 세팅
        secText.text = "0" // 시간(초) 초기화
        milliText.text = "00" // 시간(밀리초) 초기화

        startBtn.text ="시작"
//        lap_Layout.removeAllViews() // Layout에 추가한 기록View 모두 삭제
        index = 1
    }

    private fun lapTime() {
        val lapTime = time // 함수 호출 시 시간(time) 저장

        // apply() 스코프 함수로, TextView를 생성과 동시에 초기화
        val textView = TextView(this).apply {
            setTextSize(20f) // fontSize 20 설정
        }
        textView.text = "${lapTime / 100}.${lapTime % 100}" // 출력할 시간 설정

//        lap_Layout.addView(textView,0) // layout에 추가, (View, index) 추가할 위치(0 최상단 의미)
        index++ // 추가된 View의 개수를 저장하는 index 변수
    }

}