package com.example.chatapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.chatapp.R
import com.example.chatapp.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var rv:RecyclerView
    private lateinit var userlist:ArrayList<User>
    private lateinit var adapter: com.example.chatapp.RVadapter.Adapter
    private lateinit var mAuth: FirebaseAuth
 private lateinit var mdef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      mAuth= FirebaseAuth.getInstance()
      mdef=FirebaseDatabase.getInstance().getReference()
        userlist=ArrayList()
        rv=findViewById(R.id.rv)
        adapter=com.example.chatapp.RVadapter.Adapter(this,userlist)
        rv.layoutManager=LinearLayoutManager(this)
        rv.adapter=adapter

        mdef.child("User").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()// remove redundancy as list will be called again and again
                for (postsnapshot in  snapshot.children){
                    val currentuser=postsnapshot.getValue(User::class.java)//get value
                    if(mAuth.currentUser?.uid != currentuser?.uid){
                         userlist.add(currentuser!!)
                    }
                    adapter.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if (item.itemId==R.id.logout){
             //logic for logout
             mAuth.signOut()
             val intent=Intent(this@MainActivity,Login_In::class.java)

             startActivity(intent)
             finish()


             return true
         }
return true
    }
}