package com.example.ch10_notification

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class ReplyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        //TODO("ReplyReceiver.onReceive() is not implemented")

        //사용자의 input을 받는다. MainActivity에 써놓은 key_text_reply를 통해 해당되는 사용자의 입력을 공유
        val replyTxt = RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply") //사용자에게 text를 받음
        Log.d("mobileApp", "$replyTxt");

        //알림을 부르면 상태 창의 알림이 없어진다. => mamager.cancel을 했기 때문
        //브로드캐스트 리시버의 경우 context를 앞에 붙인다.
        val manager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(11) // MainActivity에서 작성했던 임의의 숫자와 같은 숫자를 입력한다.

    }
}