package com.example.chatbuddy.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbuddy.R
import com.example.chatbuddy.model.Message
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class ChatAdapter(private val messages: List<Message>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    lateinit var currentId:String


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       currentId = FirebaseAuth.getInstance().currentUser?.email?.replace(".","").toString()
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_chats, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        /*holder.messageSenderTextView.text = message.message
        holder.messageReceiverTextView.text = message.message*/

        if (currentId==message.sendBY)
        {
            holder.linearLayoutSender.visibility=View.VISIBLE
            holder.linearLayoutReceiver.visibility=View.GONE
            holder.messageSenderTextView.text=message.message
        }else
        {
            holder.linearLayoutReceiver.visibility=View.VISIBLE
            holder.linearLayoutReceiver.visibility=View.GONE
            holder.messageReceiverTextView.text=message.message
        }

       // holder.timeSenderTextViewGroup.text = formatTimestamp(message.timestamp)
      //  holder.timeReceiverTextView.text = formatTimestamp(message.timestamp)
        // Format the timestamp as needed, e.g., using SimpleDateFormat

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    // Helper function to format the timestamp
    private fun formatTimestamp(timestamp: Timestamp): String {
        // Implement your timestamp formatting logic here
        // For example, you can use SimpleDateFormat to format it as a date and time
        return "Sent at: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp.toDate())}"
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageSenderTextView: TextView = itemView.findViewById(R.id.senderMessageTextView)
        val messageReceiverTextView: TextView = itemView.findViewById(R.id.senderTimeTextView)
        val timeSenderTextViewGroup: TextView = itemView.findViewById(R.id.receiverMessageTextView)
        val timeReceiverTextView: TextView = itemView.findViewById(R.id.receiverTimeTextView)

        val linearLayoutSender:LinearLayout = itemView.findViewById(R.id.senderBubble)
        val linearLayoutReceiver:LinearLayout = itemView.findViewById(R.id.receiverBubble)
    }
}