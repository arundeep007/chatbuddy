package com.example.chatbuddy.utils
import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView

class AppUtils {

    companion object{
        fun showToast(s: String, context: Context?) {

            Toast.makeText(context,s,Toast.LENGTH_SHORT).show()

        }

    }

}

fun ShapeableImageView.loadImageFromUri(uri: Uri) {
    Glide.with(this)
        .load(uri)
        .apply(RequestOptions().centerCrop()) // You can customize RequestOptions as needed
        .into(this)
}