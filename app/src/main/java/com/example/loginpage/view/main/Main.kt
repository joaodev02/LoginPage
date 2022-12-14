package com.example.loginpage.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginpage.databinding.ActivityMainBinding
import com.example.loginpage.view.login.LogIn
import com.google.firebase.auth.FirebaseAuth

class Main : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDeslogar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val voltarTelaLogin = Intent(this,LogIn::class.java)
            startActivity(voltarTelaLogin)
            finish()
        }
    }
}