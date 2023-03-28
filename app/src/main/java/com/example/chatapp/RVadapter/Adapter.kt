package com.example.chatapp.RVadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.R
import com.example.chatapp.User

class Adapter(
    val context:Context,
    val userlist:ArrayList<User>
)
    :androidx.recyclerview.widget.RecyclerView.Adapter<Adapter.viewholder>(){


    class viewholder(itemView: View):ViewHolder(itemView){
    val viewtext=itemView.findViewById<TextView>(R.id.viewtext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
       val view=LayoutInflater.from(context).inflate(R.layout.item_view,parent,false)
       return viewholder(view)
    }

    override fun getItemCount(): Int {
       return userlist.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
      val currname=userlist[position].name.toString()
        holder.viewtext.text=currname
    }
}

