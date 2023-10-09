package com.example.chatbuddy.utils
import android.content.Context
import android.widget.Toast

class AppUtils {

    companion object{
        fun showToast(s: String, context: Context?) {

            Toast.makeText(context,s,Toast.LENGTH_SHORT).show()

        }


    }


}