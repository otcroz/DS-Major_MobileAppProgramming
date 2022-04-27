package com.example.ch16

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ch16.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
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

        // 사용자에 대한 연락처의 정보를 가져옴

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