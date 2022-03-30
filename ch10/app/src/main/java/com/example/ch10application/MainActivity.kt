package com.example.ch10application

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ch10application.databinding.ActivityMainBinding
import com.example.ch10application.databinding.DialogInputBinding

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            //Toast.makeText(this, "첫 번째 버튼의 토스트입니다.", Toast.LENGTH_LONG).show()
            val toast = Toast.makeText(this, "첫 번째 버튼의 토스트입니다.", Toast.LENGTH_LONG)
            // 토스트에 대한 설정
            toast.setText("수정된 토스트입니다.")
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.TOP, 20, 20)
            toast.addCallback(
                @RequiresApi(Build.VERSION_CODES.R)
                object: Toast.Callback(){ // 버전 호환성
                    override fun onToastHidden() {
                        super.onToastHidden()
                        Log.d("mobileApp","토스트가 사라집니다.")
                    }

                    override fun onToastShown() {
                        super.onToastShown()
                        Log.d("mobileApp","토스트가 나타납니다.")
                    }
                }
            )
            toast.show()
        }

        binding.button2.setOnClickListener {
            DatePickerDialog(this,
                object:DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                        //TODO("Not yet implemented")
                        Log.d("mobileApp", "$p1 년, ${p2 + 1} 월, $p3 일")
                    }
                },2022,3,30).show()
        }
        
        binding.button3.setOnClickListener {
            TimePickerDialog(this,
                object:TimePickerDialog.OnTimeSetListener{
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                        Log.d("mobileApp", "$p1 시 $p2 분")
                    }
                 }
                ,13,0,true).show()
        }

        val eventHandler = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1 == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileApp", "positive button")
                } else if(p1 == DialogInterface.BUTTON_NEGATIVE)
                    Log.d("mobileApp", "negative button")
            }
        }

        binding.button4.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("알림창 테스트")
                setIcon(android.R.drawable.ic_dialog_info)
                setMessage("정말 종료하시겠습니까?")
                setPositiveButton("YES", eventHandler )
                setNegativeButton("NO", eventHandler)
                setNeutralButton("MORE", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(false) // 메시지 값 출력
        }
        val items = arrayOf<String>("사과", "딸기", "수박" ,"토마토")
        binding.button5.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("아이템 목록 선택")
                setIcon(android.R.drawable.ic_dialog_info)
                setItems(items, object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Log.d("mobileApp", "${items[p1]}")
                    }
                })
                setPositiveButton("닫기", null)
                show()
            }
        }
        binding.button6.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("멀티 아이템 목록 선택")
                setIcon(android.R.drawable.ic_dialog_info)

                setMultiChoiceItems(items, booleanArrayOf(false, true, false, false),
                    object : DialogInterface.OnMultiChoiceClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
                            //TODO("Not yet implemented")
                            Log.d("mobileApp", "${items[p1]} ${if(p2) "선택" else "해제"}")
                        }
                    }
                    )

                setPositiveButton("닫기", null)
                show()
            }.setCanceledOnTouchOutside(false)
        }

        binding.button7.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("싱글 아이템 목록 선택")
                setIcon(android.R.drawable.ic_dialog_info)

                setSingleChoiceItems(items, 1,
                    object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            //TODO("Not yet implemented")
                            Log.d("mobileApp", "${items[p1]}")
                        }
                    })

                setPositiveButton("닫기", null)
                show()
            }
        }

        val dialogBinding = DialogInputBinding.inflate(layoutInflater)
        val alert = AlertDialog.Builder(this)
            .setTitle("입력")
            .setView(dialogBinding.root)
            .setPositiveButton("닫기", null)
            .create()
        binding.button8.setOnClickListener {
            AlertDialog.Builder(this).run {
               alert.show()
            }
        }

    }
}