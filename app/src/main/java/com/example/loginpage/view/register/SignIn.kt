package com.example.loginpage.view.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage.databinding.ActivitySignInBinding
import com.example.loginpage.view.login.LogIn
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {view ->
            val email = binding.emailRegister.text.toString()
            val password = binding.passwordRegister.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                val snackbar = Snackbar.make(view,"Fill in all fields!",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { register ->
                    if (register.isSuccessful){
                        val snackbar = Snackbar.make(view,"Success in registering user!",Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.BLUE)
                        snackbar.show()
                        binding.emailRegister.setText("")
                        binding.passwordRegister.setText("")
                    }
                }.addOnFailureListener {exception ->

                    val messageError = when(exception){
                        is FirebaseAuthWeakPasswordException -> "Enter a password with at least 6 characters!"
                        is FirebaseAuthInvalidCredentialsException -> "Enter a valid email address!"
                        is FirebaseAuthUserCollisionException -> "This email is already registered!"
                        is FirebaseNetworkException -> "No internet connection!"
                        else -> "Error a register to user!"
                    }
                    val snackbar = Snackbar.make(view,messageError,Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }
        binding.logIn.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()
        }
    }
}