package com.example.SOOJOOB

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.SOOJOOB.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity() {

    private lateinit var fBinding : ActivityMainBinding

    /**위치 권한 */
    // 현재 위치
    private lateinit var latLng : LatLng
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient // 위치 요청 메소드 담고 있는 객체
    private lateinit var locationRequest: LocationRequest // 위치 요청할 때 넘겨주는 데이터에 관한 객체
    private lateinit var locationCallback: MainActivity.MyLocationCallBack // 위치 확인되고 호출되는 객체
    /**위치 권한 */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(fBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment

        val navController = navHostFragment.navController

        // 위치 정보를 얻기 위한 각종 초기화
        locationInit()

        NavigationUI.setupWithNavController(fBinding.myBottomNav, navController)

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
    }

    // 프로그램이 중단되면 위치 요청을 삭제한다
    override fun onPause(){
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    // 위치 요청 메소드
    @SuppressLint("MissingPermission")
    private fun addLocationListener(){
        // 위치 정보 요청
        // (정보 요청할 때 넘겨줄 데이터)에 관한 객체, 위치 갱신되면 호출되는 콜백, 특정 스레드 지정(별 일 없으니 null)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, null)

    }

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
    }

    // 위치 정보를 찾고 나서 인스턴스화되는 클래스
    @SuppressLint("MissingPermission")
    inner class MyLocationCallBack: LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            // lastLocation프로퍼티가 가리키는 객체 주소를 받는다.
            // 그 객체는 현재 경도와 위도를 프로퍼티로 갖는다.
            // 그러나 gps가 꺼져 있거나 위치를 찾을 수 없을 때는 lastLocation은 null을 가진다.
            val location = locationResult?.lastLocation
//            //  gps가 켜져 있고 위치 정보를 찾을 수 있을 때 다음 함수를 호출한다. <?. : 안전한 호출>
            location?.run{
                // 위치값 생성
                latLng=LatLng(latitude,longitude)
                println("latitude " + latitude)
                println("longitude " + longitude)
                println("latLng " + latLng)
            }
        }
    }

    // 위치 관련 메서드
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
                if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                ;
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
        ActivityCompat.requestPermissions(this@MainActivity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),gps_request_code)
//            }
//            noButton { toast("권한 거부 됨")
//                finish() }
//        }.show()
    }
}