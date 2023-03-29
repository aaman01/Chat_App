package com.example.chatapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.RVadapter.MessageAdapter
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.model.Mesaage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Chat_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: MessageAdapter
    private lateinit var messagelist: ArrayList<Mesaage>

    var SenderRoom: String? = null
    var ReceiverRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // recieved value from adapter
        val name = intent.getStringExtra("name")
        val receiverID = intent.getStringExtra("uid")

        val senderID = FirebaseAuth.getInstance().currentUser?.uid

        SenderRoom = receiverID + senderID
        ReceiverRoom = senderID + receiverID

        supportActionBar?.title = name
        messagelist = ArrayList()
        adapter = MessageAdapter(this, messagelist)

        addmessagetoRecycleView(messagelist)

        binding.sendbtn.setOnClickListener {
        addmessagetoDB(senderID)
        }


        binding.chatrv.adapter = adapter
        binding.chatrv.layoutManager=LinearLayoutManager(this)


    }

    private fun addmessagetoRecycleView(messagelist: ArrayList<Mesaage>) {
        FirebaseDatabase.getInstance().getReference().child("chats").child(SenderRoom!!).child("message").addValueEventListener(
            object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                messagelist.clear()
                    for (postsnapshot in snapshot.children){
                        val message=postsnapshot.getValue(Mesaage::class.java)
                        messagelist.add(message!!)

                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
        )
    }

    private fun addmessagetoDB(senderID: String?) {
        //adding the message to database


            val message = binding.messagebox.text.toString().trim()
            val messageobj = Mesaage(message, senderID)
            FirebaseDatabase.getInstance().getReference().child("chats")
                .child(SenderRoom!!).child("message").push().setValue(messageobj)
                .addOnSuccessListener {

                    FirebaseDatabase.getInstance().getReference().child("chats")
                        .child(ReceiverRoom!!).child("message").push().setValue(messageobj)
                }
            binding.messagebox.setText(" ")




    }
}