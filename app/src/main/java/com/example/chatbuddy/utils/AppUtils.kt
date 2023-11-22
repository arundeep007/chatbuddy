package com.example.chatbuddy.utils
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AppUtils {

    companion object{
        val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        fun showToast(s: String, context: Context?) {

            Toast.makeText(context,s,Toast.LENGTH_SHORT).show()

        }


        @Throws(IOException::class)
        fun resizeImage(context: Context, inputImageUri: Uri, quality: Int): Uri? {
            val inputBitmap = decodeUriToBitmap(context, inputImageUri)
            if (inputBitmap == null) {
                return null
            }

            val resizedImageUri = saveBitmapToFile(context, inputBitmap, quality)
            inputBitmap.recycle() // Release the original bitmap
            return resizedImageUri
        }

        private fun decodeUriToBitmap(context: Context, imageUri: Uri): Bitmap? {
            return try {
                BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        private fun saveBitmapToFile(context: Context, bitmap: Bitmap, quality: Int): Uri? {
            return try {
                val directory = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "ResizedImages"
                )
                directory.mkdirs()

                val fileName = "resized_image_${System.currentTimeMillis()}.jpg"
                val file = File(directory, fileName)
                val fileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
                Uri.fromFile(file)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }


    }

}

fun ShapeableImageView.loadImageFromUri(uri: Uri) {

    if (uri!=null)
    {
        Glide.with(this)
            .load(uri)
            .apply(RequestOptions().centerCrop()) // You can customize RequestOptions as needed
            .into(this)
    }

}