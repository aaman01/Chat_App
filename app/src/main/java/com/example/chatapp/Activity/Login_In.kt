package com.example.chatapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivityLoginInBinding
import com.google.firebase.auth.FirebaseAuth

class Login_In : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
  private  lateinit var binding: ActivityLoginInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    supportActionBar?.hide()
        binding= ActivityLoginInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Signuptext.setOnClickListener {
            val intent=Intent(this, Sign_Up::class.java)

            startActivity(intent)
            finish()
            //if back button is pressed  close the app

        }
        mAuth= FirebaseAuth.getInstance()
        binding.loginbtn.setOnClickListener {

             val email=binding.email.text.toString()
            val password=binding.password.text.toString()
            login(email,password)
        }



    }

    private fun login(email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty() && password.length>=6){
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"User doesnot exists",Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this,"Invalid Credentials",Toast.LENGTH_SHORT).show()
        }

    }
}