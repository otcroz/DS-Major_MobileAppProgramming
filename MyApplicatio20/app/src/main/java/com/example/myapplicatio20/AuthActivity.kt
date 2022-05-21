package com.example.myapplicatio20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.myapplicatio20.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    lateinit var binding:ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_auth)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeVisibility(intent.getStringExtra("data").toString()) // MainActivity에서 데이터 값을 받아옴

        binding.goSignInBtn.setOnClickListener {
            changeVisibility("signin") // 화면을 바꿈
        }

        binding.signBtn.setOnClickListener {
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()
            MyApplication.auth.createUserWithEmailAndPassword(email, password) // 회원가입
            // 회원가입이 잘 되고 있나 확인
                .addOnCompleteListener(this){ task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if(task.isSuccessful){ // 회원가입 성공
                        //이메일 검증
                        MyApplication.auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener{ sendTask ->
                                if(sendTask.isSuccessful){ // 메일발송 성공
                                    Toast.makeText(baseContext, "회원가입 성공!! 메일을 확인해주세요.", Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout") // 아직은 회원가입이 진행된 것, 로그인이 된 것은 아니다.
                                } else{ // 메일발송 실패
                                    Toast.makeText(baseContext, "메일발송 실패", Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout") // 로그인 상태는 아님
                                }
                            }
                        
                    }
                    else{ // 회원가입 실패
                        Toast.makeText(baseContext, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        changeVisibility("logout") // 로그인 상태는 아님
                    }
                }

        }

        binding.loginBtn.setOnClickListener {
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()

            MyApplication.auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){ task ->
                    binding.authPasswordEditView.text.clear()
                    binding.authEmailEditView.text.clear()
                    if(task.isSuccessful){
                        //인증 확인
                        if(MyApplication.checkAuth()){
                            MyApplication.email = email
                            finish() // MainActivity 로 돌아감
                        } else{
                            Toast.makeText(baseContext, "이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()
                        }

                    }
                    else{ // 로그인 실패
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.logoutBtn.setOnClickListener {
            MyApplication.auth.signOut()
            MyApplication.email = null
            finish() // MainActivity 로 돌아감
        }
    }

    fun changeVisibility(mode:String){
        if(mode.equals("login")){
            binding.run{
                authMainTextView.text = "정말 로그아웃하시겠습니까?"
                authMainTextView.visibility = View.VISIBLE
                logoutBtn.visibility = View.VISIBLE
                goSignInBtn.visibility = View.GONE
                authEmailEditView.visibility = View.GONE
                authPasswordEditView.visibility = View.GONE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.GONE
            }
        } else if(mode.equals("logout")){
            binding.run{
                authMainTextView.text = "로그인하거나 회원가입해주세요."
                authMainTextView.visibility = View.VISIBLE
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.VISIBLE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.VISIBLE
            } // 로그아웃 상태에서 로그인 또는 회원가입
        } else if(mode.equals("signin")){
            binding.run{
                authMainTextView.text = "로그인하거나 회원가입해주세요."
                authMainTextView.visibility = View.GONE
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.GONE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.VISIBLE
                loginBtn.visibility = View.GONE
            } // 로그아웃 상태에서 로그인 또는 회원가입
        }
    }
}