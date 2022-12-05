package com.example.loginpage.view.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.loginpage.R
import com.example.loginpage.databinding.ActivityLogInBinding
import com.example.loginpage.view.main.Main
import com.example.loginpage.view.register.SignIn
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.ktx.Firebase

class LogIn : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnter.setOnClickListener {view ->

            val email = binding.emailEnter.text.toString()
            val password = binding.passwordEnter.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                val snackbar = Snackbar.make(view,"Fill in all fields!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { authentication ->
                    if (authentication.isSuccessful){
                        navegationToMain()
                    }
                }.addOnFailureListener {
                    val snackbar = Snackbar.make(view,"Error when trying to login, please enter valid information!", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }

    private fun navegationToMain(){
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
        finish()
}

   override fun onStart() {
        super.onStart()
        val userAtual = FirebaseAuth.getInstance().currentUser
        if (userAtual != null){
            navegationToMain()
        }
    }
}