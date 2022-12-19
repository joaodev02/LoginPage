package com.example.loginpage.view.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage.databinding.ActivitySignInBinding
import com.example.loginpage.view.login.LogIn
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val idItem = System.currentTimeMillis().toInt()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {view ->

            val name = binding.nameRegister.text.toString()
            val tel = binding.telRegister.text.toString()
            val email = binding.emailRegister.text.toString()
            val password = binding.passwordRegister.text.toString()
            val confirmPassword = binding.passwordConfirmRegister.text.toString()


            val userMap = hashMapOf(
                "nome" to name,
                "email" to email,
                "celular" to tel
            )
            db.collection("Users").document(idItem.toString())
                .set(userMap).addOnCompleteListener {
                    Log.d("db", "Sucesso ao salvar usuário!")
                }.addOnFailureListener {

                }
            if (name.isEmpty() ||email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                val snackbar = Snackbar.make(view,"Preencha todos os campos!",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else if (password != confirmPassword){
                val snackbar = Snackbar.make(view,"Suas senhas devem ser iguais!",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { register ->
                    if (register.isSuccessful){
                        val snackbar = Snackbar.make(view,"Sucesso ao registrar usuario!",Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.BLUE)
                        snackbar.show()
                        binding.emailRegister.setText("")
                        binding.passwordRegister.setText("")
                        binding.nameRegister.setText("")
                        binding.passwordConfirmRegister.setText("")
                        binding.telRegister.setText("")
                    }
                }.addOnFailureListener {exception ->

                    val messageError = when(exception){
                        is FirebaseAuthWeakPasswordException -> "Sua senha deve conter no minimo 6 caracteres!"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email valido!"
                        is FirebaseAuthUserCollisionException -> "Email ja registrado, faca o login!"
                        is FirebaseNetworkException -> "Sem conexao com a internet!"
                        else -> "Erro ao tentar cadastrar usuário!"
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