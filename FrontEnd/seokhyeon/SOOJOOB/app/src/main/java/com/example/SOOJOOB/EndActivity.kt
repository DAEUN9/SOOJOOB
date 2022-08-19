package com.example.SOOJOOB

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.*
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.SOOJOOB.retrofit.PloggingRequestBody
import com.example.SOOJOOB.retrofit.PloggingWork
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


open class EndActivity : AppCompatActivity() {

    private lateinit var sumDistanceText: TextView
    private lateinit var timeRecordText: TextView
    private lateinit var trashCountText: TextView

    private lateinit var btn_camera: Button
    private lateinit var iv_pre: ImageView
    private lateinit var now :TextView
    private lateinit var imageSaveBtn: Button
    private lateinit var bitmap:Bitmap
    private var imgCount:Int = 0

    private lateinit var nextButton:Button
    private var ploggingImg:String = ""

    private lateinit var getButton: Button
    private lateinit var captureBitmap:Bitmap
    // Noti 객체 생성
    private lateinit var notificationHelper: NotificationHelper

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        val timeRecord = intent.getIntExtra("timeRecord",0)
        timeRecordText = findViewById(R.id.TimeRecord)
        timeRecordText.text = "총 시간 " + "   ${timeRecord / 100}" + "\"" + "${timeRecord % 100}"

        val sumDistance = intent.getDoubleExtra("sumDistance", 0.0)
        sumDistanceText = findViewById(R.id.sumDistance)
        sumDistanceText.text = "이동 거리 " + "    $sumDistance" + "m"


        val trashCount = intent.getIntExtra("trashCount",0)
        trashCountText = findViewById(R.id.trashCount)
        trashCountText.text = "$trashCount" + "곳이 깨끗해졌습니다."

        btn_camera = findViewById(R.id.btn_camera)
        iv_pre = findViewById(R.id.iv_pre)
        now = findViewById(R.id.now)

//        val icon = BitmapFactory.decodeResource(getResources(), R.drawable.boy)
//        iv_pre.setImageBitmap(icon)
        // 이미지 캡쳐 적용 (안됨 ㅠㅠ)
//        val captureImage = intent?.getParcelableExtra<Bitmap>("captureImage")
//        val captureImageTest = intent?.getParcelableExtra<Bitmap>("captureImageTest")
//        println("captureImage : " + captureImage)
//        println("captureImageTest : " + captureImageTest)
//        iv_pre.setImageBitmap(captureImageTest)


        val currentDateTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentDateTime)

        now.text = dateFormat
//        ploggingImg = encodeImage(icon)

        imageSaveBtn = findViewById(R.id.imageSaveBtn)
        imageSaveBtn.setOnClickListener{
            saveImage(bitmap, "plogging_result"+ "${getRandomString(15)}")
            imgCount ++
            ploggingImg = encodeImage(bitmap)
            println(ploggingImg.length)
        }

        val captureImage = intent.getByteArrayExtra("capture")
        val captureBitmap = captureImage?.toBitmap()
        ploggingImg = captureBitmap?.let { encodeImage(it) }.toString()
        iv_pre.setImageBitmap(captureImage?.toBitmap())


        val endIntent = Intent(this, MainActivity::class.java)
        // Retrofit
        nextButton = findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            val ploggingData = PloggingRequestBody(
                sumDistance,
                timeRecord,
                trashCount,
                dateFormat,
                ploggingImg
            )
            var cnt = 0
            val ploggingWork = PloggingWork(ploggingData)
            ploggingWork.work(completion = { badges ->
                if (badges != null) {
                    for (badge in badges) {
                        notificationHelper = NotificationHelper(this)
                        cnt += 1
                        val title: String = "${badge.badgeName}"
                        val message: String = "새로운 배지를 획득하셨어요"

                        // 알림 호출ㅇ
                        showNotification(title, message, cnt)
                    }
                }
            })
            startActivity(endIntent)
        }

//        getButton = findViewById(R.id.getButton)
//        getButton.setOnClickListener {
//            val nextIntent = Intent(this, PloggingDataActivity::class.java)
//            startActivity(nextIntent)
//        }




        // 카메라 권한 가져오기
        if(checkPermission(STORAGE_PERMISSION,FLAG_PERM_STORAGE)){
            setViews()
        }

    }

    // 이미지 파일 중복 방지를 위한 랜덤 스트링 함수
    fun getRandomString(length: Int) : String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }


    //Manifest 에서 설정한 권한을 가지고 온다.
    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)

    //권한 플래그값 정의
    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    //카메라와 갤러리를 호출하는 플래그
    val FLAG_REQ_CAMERA = 101
    val FLAG_REA_STORAGE = 102


    private fun setViews() {
        //카메라 버튼 클릭
        btn_camera.setOnClickListener {
            //카메라 호출 메소드
            openCamera()
        }
    }


    private fun openCamera() {
        //카메라 권한이 있는지 확인
        if(checkPermission(CAMERA_PERMISSION,FLAG_PERM_CAMERA)){
            //권한이 있으면 카메라를 실행시킵니다.
            val intent:Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,FLAG_REQ_CAMERA)
        }
    }

    //권한이 있는지 체크하는 메소드
    fun checkPermission(permissions:Array<out String>,flag:Int):Boolean{
        //안드로이드 버전이 마쉬멜로우 이상일때
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(permission in permissions){
                //만약 권한이 승인되어 있지 않다면 권한승인 요청을 사용에 화면에 호출합니다.
                if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,permissions,flag)
                    return false
                }
            }
        }
        return true
    }

    //checkPermission() 에서 ActivityCompat.requestPermissions 을 호출한 다음 사용자가 권한 허용여부를 선택하면 해당 메소드로 값이 전달 됩니다.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            FLAG_PERM_STORAGE ->{
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        //권한이 승인되지 않았다면 return 을 사용하여 메소드를 종료시켜 줍니다
                        Toast.makeText(this,"저장소 권한을 승인해야지만 앱을 사용할 수 있습니다..",Toast.LENGTH_SHORT).show()
                        finish()
                        return
                    }
                }
                //카메라 호출 메소드
                setViews()
            }
            FLAG_PERM_CAMERA ->{
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"카메라 권한을 승인해야지만 카메라를 사용할 수 있습니다.",Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                openCamera()
            }
        }
    }

    //startActivityForResult 을 사용한 다음 돌아오는 결과값을 해당 메소드로 호출합니다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                FLAG_REQ_CAMERA ->{
                    if(data?.extras?.get("data") != null){
                        //카메라로 방금 촬영한 이미지를 미리 만들어 놓은 이미지뷰로 전달 합니다.
                        bitmap = data?.extras?.get("data") as Bitmap
                        iv_pre.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    open fun saveImage(bitmap: Bitmap?, saveImageName: String): Boolean {
        val saveDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            .toString() + "/directoryName"
        val file = File(saveDir)
        if (!file.exists()) {
            file.mkdir()
        }
        val fileName = "$saveImageName.png"
        val tempFile = File(saveDir, fileName)
        var output: FileOutputStream? = null
        try {
            if (tempFile.createNewFile()) {
                output = FileOutputStream(tempFile)
                // 이미지 줄이기

                val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 200, 200, true)
                newBitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
            } else {
                // 같은 이름의 파일 존재
                Log.d("TEST_LOG", "같은 이름의 파일 존재:$saveImageName")
                return false
            }
        } catch (e: FileNotFoundException) {
            Log.d("TEST_LOG", "파일을 찾을 수 없음")
            return false
        } catch (e: IOException) {
            Log.d("TEST_LOG", "IO 에러")
            e.printStackTrace()
            return false
        } finally {
            if (output != null) {
                try {
                    output.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return true
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun showNotification(title: String, message: String, id: Int) {
        val nb: NotificationCompat.Builder =
            notificationHelper.getChannelNotification(title, message)
        notificationHelper.getManager().notify(id, nb.build())
    }

    fun ByteArray.toBitmap(): Bitmap?{
        val by = Base64.encodeToString(this, Base64.DEFAULT)
        Base64.decode(by, Base64.DEFAULT).apply {
            return BitmapFactory.decodeByteArray(this,0,size)
        }
    }


}