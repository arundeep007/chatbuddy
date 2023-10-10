package com.example.chatbuddy.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbuddy.R
import com.example.chatbuddy.utils.OnItemClickListener
import com.google.android.material.imageview.ShapeableImageView

class UserAdapter(
    private val dataList: List<com.example.chatbuddy.model.User>,
    private val onClick: OnItemClickListener
) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_users, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]

        // Bind data to the views
        // setting image after adding feature by using glide
        holder.username.text = data.username


        //after adding feature of online offline status
        // holder.userStatus.text = data.status
        // Setting the  item click listener
        holder.itemView.setOnClickListener {

            onClick.onItemClick(data)

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage: ShapeableImageView = itemView.findViewById(R.id.userImage)
        val username: TextView = itemView.findViewById(R.id.username)
        val userStatus: TextView = itemView.findViewById(R.id.userStatus)
    }
}