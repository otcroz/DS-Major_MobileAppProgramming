package com.example.myapplicatio20

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplicatio20.databinding.ActivityAddBinding
import java.io.File
import java.util.*

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    lateinit var filePath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode === android.app.Activity.RESULT_OK){
            Glide
                .with(applicationContext)
                .load(it.data?.data) // 이미지 위치에 대한 정보를 가져온다.
                .apply(RequestOptions().override(250,200))
                .centerCrop()
                .into(binding.addImageView) // 이미지를 넣을 위치
            val cursor = contentResolver.query(it.data?.data as Uri,
            arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null);
            cursor?.moveToFirst().let { // 사용자가 선택한 이미지에 대한 (0번째 앞의 데이터?) 파일 경로를 가져와 string으로 변환
                filePath = cursor?.getString(0) as String
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === R.id.menu_add_gallery){
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            requestLauncher.launch(intent)
        }
        else if(item.itemId === R.id.menu_add_save){
            if(binding.addImageView.drawable !== null && binding.addEditView.text.isNotEmpty()){
                saveStore()

            } else{
                Toast.makeText(this, "데이터가 모두 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveStore(){ // id, 입력된 내용, 입력 시간 정보
        val data = mapOf(
            "email" to MyApplication.email, // read, write 권한을 가지도록 하기위해 설정
            "content" to binding.addEditView.text.toString(),
            "date" to dateToString(Date())

        )

        MyApplication.db.collection("news") // 이미지, 글에 대한 정보가 이 테이블에 저장
            .add(data)
            .addOnSuccessListener{
                uploadImage(it.id)
            }
            .addOnFailureListener{
                Log.d("mobileApp", "data save error")
            }
    }

    private fun uploadImage(docId: String){
        val storage = MyApplication.storage
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${docId}.jpg") // 스토리지에 저장

        //원본 이미지를 스토리지에 업로드
        val file= Uri.fromFile(File(filePath))
        imageRef.putFile(file)
            .addOnSuccessListener {
                Toast.makeText(this, "save ok..", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Log.d("mobileApp", "file save error")
            }
    }
}