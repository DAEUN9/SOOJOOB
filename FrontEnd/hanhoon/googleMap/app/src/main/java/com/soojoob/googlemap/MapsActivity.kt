package com.soojoob.googlemap

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient


class MapsActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnPolylineClickListener,
    GoogleMap.OnPolygonClickListener {

    private var permissionDenied = false
    // 마커, 카메라 지정을 위한 구글 맵 객체
    private lateinit var mMap: GoogleMap

    // PlacesClient 객체(인터페이스는 기기의 현재 위치와 위치 근처의 장소를 검색)를 만듭니다.
    private lateinit var placesClient : PlacesClient
    // LocationServices 인터페이스는 Android 위치 서비스의 기본 진입점입니다.
    // 위치 요청 메소드 담고 있는 객체
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 콘텐츠 뷰 설정하기
        setContentView(R.layout.activity_maps)

        // 화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // 세로 모드로 화면 고정
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Places 객체(Android용 Places SDK의 클라이언트를 만들고 관리)를 초기화합니다.
        Places.initialize(applicationContext, getString(R.string.maps_api_key))
        // PlacesClient 객체(인터페이스는 기기의 현재 위치와 위치 근처의 장소를 검색)를 만듭니다.
        placesClient = Places.createClient(this)
        // LocationServices 인터페이스는 Android 위치 서비스의 기본 진입점입니다.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // 활동의 onCreate() 메서드에서 FragmentManager.findFragmentById()를 호출하여 지도 프래그먼트의 핸들을 가져옵니다.
        // 프래그먼트 매니저로부터 SupportMapFragment프래그먼트를 얻는다. 이 프래그먼트는 지도를 준비하는 기능이 있다.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        // 그런 다음 getMapAsync()를 사용하여 지도 콜백에 등록합니다
        // 지도가 준비되면 알림을 받는다. (아마, get함수에서 다른 함수를 호출해서 처리하는 듯)
        mapFragment.getMapAsync(this)
    }


    // OnMapReadyCallback 인터페이스를 구현하고, GoogleMap 객체를 사용할 수 있을 때
    // 지도를 설정하도록 onMapReady() 메서드를 재정의
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        // 위치 추가
        val iwc = LatLng(36.1019170, 128.4198704)
        val samsung_gumi = LatLng(36.1072574, 128.4151203)
        // 마커 추가
        googleMap.addMarker(MarkerOptions().position(samsung_gumi).title("Marker in Samsung_Gumi"))
        // 확대/축소 : 1-세계, 5-대륙, 10-도시, 15-거리, 20-건물
//        // 영역 내에 카메라 중심 맞추기
//        val gumi_example = LatLngBounds(
//                iwc,
//                LatLng(36.1040515, 128.4202060)
//        )
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gumi_example.center, 10f))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(samsung_gumi))
        // 삼성 구미를 중심으로 거리수준의 배율로 카메라 이동
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(samsung_gumi, 15f))

        // 폴리라인 추가
        // 다중선을 추가하여 지도에 선 그리기 (addPolyline)
        val polyline1 = googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .width(25f)
                .color(Color.RED)
                .add(
                    iwc,
                    LatLng(36.1044596, 128.4170358),
                    samsung_gumi,
                    LatLng(36.1077373, 128.4177588),
                    LatLng(36.1044062, 128.4211384),
                    LatLng(36.1040515, 128.4202060)))

        val polyline2 = googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    iwc,
                    LatLng(-34.000, 145.000),
                    LatLng(-34.000, 147.000),
                    LatLng(-33.000, 150.000),
                    LatLng(-32.000, 149.000),
                    LatLng(-32.000, 147.000)))

        // 다중선과 함께 데이터 객체를 저장한다.
        polyline1.tag = "A"
        polyline2.tag = "B"

        // 사용자가 다중선을 클릭할 때마다 실선과 점선 간의 패턴을 변경함
        googleMap.setOnPolylineClickListener(this)
        // 지도에 영역을 나타내는 다각형
        googleMap.setOnPolygonClickListener(this)

        // 지도 유형 설정하기
//        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
//        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
//        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
//        googleMap.mapType = GoogleMap.MAP_TYPE_NONE

        // ui 세팅
        val uisettings = googleMap.uiSettings
        // 확대/축소 컨트롤 바
        uisettings.setZoomControlsEnabled(true)
        // 나침반 표시
        uisettings.setCompassEnabled(true)
        // 지도 툴바 삭제 (마커 누르면 우측밑에 필요 없는거)
        uisettings.setMapToolbarEnabled(false)
        // 기울이기 동작 제거 (두 손가락 대고 위아래 움직이기)
        uisettings.setTiltGesturesEnabled(false)
        // 회전 동작 제거 (두 손가락 대고 회전 하기)
//        uisettings.setRotateGesturesEnabled(false)

        // 현재위치(내위치)로 이동 버튼 활성화
        googleMap.isMyLocationEnabled = true
        // 사용자가 내 위치 버튼을 클릭하면 앱이 GoogleMap.OnMyLocationButtonClickListener에서 onMyLocationButtonClick() 콜백을 수신
        googleMap.setOnMyLocationButtonClickListener(this)
        // 사용자가 내 위치의 파란색 점을 클릭하면 앱이 GoogleMap.OnMyLocationClickListener에서 onMyLocationClick() 콜백을 수신
        googleMap.setOnMyLocationClickListener(this)
        // 내 위치 레이어와 내 위치 버튼을 사용하면 지도에 사용자의 현재 위치를 표시할 수 있습니다.
        // 지도에 내 위치 레이어를 사용 설정하려면 googleMap.isMyLocationEnabled()를 호출
//        enableMyLocation()
    }

    // 사용자가 다중선을 클릭할 때마다 실선과 점선 간의 패턴을 변경함
    private val PATTERN_GAP_LENGTH_PX = 20
    private val DOT: PatternItem = Dot()
    private val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())

    // Create a stroke pattern of a gap followed by a dot.
    private val PATTERN_POLYLINE_DOTTED = listOf(GAP, DOT)
    override fun onPolylineClick(polyline: Polyline) {
        // Flip from solid stroke to dotted stroke pattern.
        if (polyline.pattern == null || !polyline.pattern!!.contains(DOT)) {
            polyline.pattern = PATTERN_POLYLINE_DOTTED
            polyline.color = Color.GREEN
        } else {
            // The default pattern is a solid stroke.
            polyline.pattern = null
            polyline.color = Color.BLUE
        }
        Toast.makeText(this, "Route type " + polyline.tag.toString(),
            Toast.LENGTH_SHORT).show()    }

    override fun onPolygonClick(p0: Polygon) {
        TODO("Not yet implemented")
    }

    // 자기 위치 표시
    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {

        // 권한 체크
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            return
        }

        // 권한 메시지 상자 표시
        // 2. If if a permission rationale dialog should be shown
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
//            PermissionUtils.RationaleDialog.newInstance(
//                LOCATION_PERMISSION_REQUEST_CODE, true
//            ).show(supportFragmentManager, "dialog")
            return
        }

        // 3. Otherwise, request permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
            return
        }

//        if (isPermissionGranted(
//                permissions,
//                grantResults,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) || isPermissionGranted(
//                permissions,
//                grantResults,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            )
//        ) {
//            // Enable the my location layer if the permission has been granted.
//            enableMyLocation()
//        } else {
//            // Permission was denied. Display an error message
//            // Display the missing permission error dialog when the fragments resume.
//            permissionDenied = true
//        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
        // 사용자에게 gps키라고 알리기
        Toast.makeText(this,"이 앱은 GPS(위치)를 켜야 이용 가능합니다!", Toast.LENGTH_SHORT).show()
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private fun showMissingPermissionError() {
//        newInstance(true).show(supportFragmentManager, "dialog")
    }

    companion object {
        /**
         * Request code for location permission request.
         *
         * @see .onRequestPermissionsResult
         */
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}