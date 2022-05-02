package com.example.ch16

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.ch16.databinding.ActivityMainBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var filePath:String // 파일의 절대 주소 : 나중에 정의
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 주소록 앱을 호출하고 처리해주는 라우처
        val requestContractLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { // 주소록 앱에서 전달받은 값
                if (it.resultCode == RESULT_OK) {
                    Log.d("mobileApp", "${it.data?.data}")
                    val cursor = contentResolver.query(
                        it!!.data!!.data!!,
                        arrayOf<String>(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, // 이름
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        ),
                        null, null, null
                    ) // 가지고 올 내용
                    if (cursor!!.moveToFirst()) { // 여러 개 중 첫 번째 결과를 처리
                        val name = cursor?.getString(0) // 이름에 대한 값
                        val phone = cursor?.getString(1) // number에 대한 값
                        binding.addrTV.text = "이름: $name, 전화번호: $phone" // 출력
                    }
                }
            }

        //주소록
        binding.addBtn.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            requestContractLauncher.launch(intent) // 주소록에 대한 앱 호출
        }

        // 갤러리 앱을 호출하고 처리해주는 라우처
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            try {
                val calRatio = calculateInSampleSize( // 비율
                    it.data!!.data!!, 150, 150
                )

                val option = BitmapFactory.Options()
                option.inSampleSize = 4
                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option) // 이미지를 받음
                inputStream!!.close()
                inputStream = null // 자원을 유지하지 않는다.

                bitmap?.let {
                    binding.userIDImg.setImageBitmap(bitmap)
                } ?: let {
                    Log.d("mobileApp", "bitmap null")
                } //null을 가져온 경우
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //갤러리
        binding.galleryBtn.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI) // 이미지를 가져옴
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent) // 인텐트를 전달
        }

        val requestCameraThumnailLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            val bitmap = it?.data?.extras?.get("data") as Bitmap // 비트맵을 가져오기
            binding.userIDImg.setImageBitmap(bitmap) // 이미지 비트맵을 넣기
        }
        binding.cameraBtn1.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            requestCameraThumnailLauncher.launch(intent)
        }

        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            val calRatio = calculateInSampleSize(Uri.fromFile(File(filePath)), 100, 150) // 크기를 위한 비율값 가져오기
            val option = BitmapFactory.Options()
            option.inSampleSize = calRatio
            val bitmap = BitmapFactory.decodeFile(filePath, option) // filepath를 통해 데이터를 가져옴
            bitmap?.let{
                binding.userIDImg.setImageBitmap(bitmap)
            } ?: let{
                Log.d("mobileApp", "bitmap null")
            }

        }
        val timeS:String = SimpleDateFormat("yyyymmdd_hHHmmss").format(Date()) // java.util.Date
        val storeDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile("JPEG_${timeS}_", ".jpg", storeDir) // 저장 형식
        filePath = file.absolutePath // 파일의 절대주소
        val photoURI:Uri = FileProvider.getUriForFile(
            this,
            "com.example.ch16.fileprovider",
            file
        )
        binding.cameraBtn2.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // 촬영한 사진을 저장하기 위해
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI) // URI를 이용하여 카메라를 촬영
            requestCameraFileLauncher.launch(intent)
        }

        //지도맵 연동하기
        binding.mapBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.562952, 126.9779451")) // 지도맵을 불러냄, 위치에 대한 정보
            startActivity(intent)
        }
        
        //전화앱 연동하기
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){

        }
        binding.callBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel: 02-911"))
            val status = ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") // 권한에 대한 확인
            if(status == PackageManager.PERMISSION_GRANTED){ // 권한을 허용했을 때
                startActivity(intent)
            } else{
                requestPermissionLauncher.launch("android.permission.CALL_PHONE") // 사용자에게 권한 설정을 묻는다.
            }

        }
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}