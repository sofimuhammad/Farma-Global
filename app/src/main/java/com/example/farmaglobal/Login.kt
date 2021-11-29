package com.example.farmaglobal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.farmaglobal.Network.ApiClient
import com.example.farmaglobal.Network.LoginResponse
import com.example.farmaglobal.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pref = Preferences(this@Login)
        // Check is login or not
        if (pref.isLogin) {
            Handler().postDelayed({
                val intent = Intent(this@Login, MainActivity::class.java)
                startActivity(intent)
            }, 600)
        }


        binding.btnLogin.setOnClickListener {
            val body = hashMapOf(
                "username" to binding.EtUsername.text.toString(),
                "password" to binding.EtPassword.text.toString()
            )

            //method respon API
            ApiClient.api.login(body).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        pref.token = response.body()?.response?.token ?: ""
                        pref.isLogin = true

                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                }

            })
        }
    }
}