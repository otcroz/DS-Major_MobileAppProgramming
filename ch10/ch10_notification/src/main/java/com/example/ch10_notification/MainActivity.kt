package com.example.ch10_notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput // 직접 import하여 추가
import com.example.ch10_notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder : NotificationCompat.Builder

            //호환성 처리
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val ch_id = "one-channel"
                val channel = NotificationChannel(ch_id, "My Channel One", NotificationManager.IMPORTANCE_DEFAULT)

                //chanel 속성 변경
                channel.description ="My Channel One 소개"
                channel.setShowBadge(true)
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(100, 200, 100, 200)
                // (100, 200) (100, 200) : (진동X 시간, 진동 시간) : msec

                //소리 알림
                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audio_attr = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()

                channel.setSound(uri, audio_attr)

                manager.createNotificationChannel(channel)
                builder = NotificationCompat.Builder(this,ch_id)
            } else{
                builder = NotificationCompat.Builder(this)
            }
            builder.setSmallIcon(R.drawable.small)
            builder.setWhen(System.currentTimeMillis())
            builder.setContentTitle("안녕하세요")
            builder.setContentText("모바일앱프로그래밍 시간입니다.")
            val bigPic = BitmapFactory.decodeResource(resources, R.drawable.big)
            val buildStyle = NotificationCompat.BigPictureStyle()
            buildStyle.bigPicture((bigPic))

            builder.setStyle(buildStyle)

            //인텐트 생성
            val replyIntent = Intent(this, ReplyReceiver::class.java)

            // getBroadcast(context, 적절한 숫자, 등록한 인텐트, 내용 변경 여부)
            val replyPendingIntent = PendingIntent.getBroadcast(this, 30, replyIntent, PendingIntent.FLAG_MUTABLE) // FLAG_MUTABLE로 설정해야한다.
            builder.setContentIntent(replyPendingIntent) //인텐트 등록,
            // 위의 코드를 주석처리하면 알림을 터치하는 것에 대한 인텐트 처리가 발생하지 X

            // 원격으로 답장
            val remoteInput = RemoteInput.Builder("key_text_reply").run{ // RemoteInput은 안드로이드와 안드로이드X에서 제공한다.
                setLabel("답장")
                build()
            }

            // 알림에 대한 Action
            builder.addAction(
                NotificationCompat.Action.Builder(
                    android.R.drawable.stat_notify_more,
                    "Action",
                    replyPendingIntent //replyReceiver가 실행
                ).build() // 액션 생성
            )

            builder.addAction(
               NotificationCompat.Action.Builder(
                   R.drawable.send,
                   "답장",
                   replyPendingIntent
               ).addRemoteInput(remoteInput).build() // 액션 실행 시 원격으로 답장을 함 //@ 현재 주어진 것에서 변경이 일어날 수 있음
            )

            manager.notify(11, builder.build())
        }
    }
}