 package com.example.chatapp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.model.User
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

 class Sign_Up : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var Database:DatabaseReference
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
      mAuth= FirebaseAuth.getInstance()
        binding.signupbtn.setOnClickListener {
            val email=binding.email.text.toString().trim() //trim to remove extra space
            val password=binding.password.text.toString().trim()
            val name=binding.name.text.toString().trim()
            Signup(name,email,password)
        }


    }

     private fun Signup(name:String, email: String, password: String) {
     if(email.isNotEmpty() && password.isNotEmpty() && password.length>=6){
          mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task->   //remove task-> you will see it ,
              // but to replace it we are adding task->

          if(task.isSuccessful){
              //add details to DataBase
              adddatatoDB(name,email, mAuth.currentUser?.uid!!)
              //move to home activity
              val intent= Intent(this, MainActivity::class.java)
              startActivity(intent)
              //if back button is pressed  close the app
              finish()

          }else{
              Toast.makeText(this,"Error Occured",Toast.LENGTH_SHORT).show()
          }

          }
     }else{
         Toast.makeText(this,"PLease input valid credentials",Toast.LENGTH_SHORT).show()
     }
     }

     private fun adddatatoDB(name: String, email: String, uid: String) {
        Database=FirebaseDatabase.getInstance().getReference()
         Database.child("User").child(uid).setValue(User(name,email,uid))

     }
 }