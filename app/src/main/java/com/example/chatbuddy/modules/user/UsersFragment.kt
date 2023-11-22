package com.example.chatbuddy.modules.user

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.chatbuddy.BaseFragment
import com.example.chatbuddy.R
import com.example.chatbuddy.adapters.UserAdapter
import com.example.chatbuddy.databinding.FragmentUsersBinding
import com.example.chatbuddy.model.User
import com.example.chatbuddy.repositories.UserRepository
import com.example.chatbuddy.utils.AppUtils
import com.example.chatbuddy.utils.OnItemClickListener
import com.example.chatbuddy.utils.loadImageFromUri
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firestore.v1.DocumentTransform.FieldTransform.ServerValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URI
import java.util.UUID

class UsersFragment : BaseFragment(), OnItemClickListener {
    private var binding: FragmentUsersBinding? = null
    lateinit var adapter: UserAdapter
    lateinit var userRepository: UserRepository
    private lateinit var changeImage: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerActivityResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUsersBinding.inflate(layoutInflater)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRepository = UserRepository()

        // To get the initial list of users and listen for updates
        userRepository.getUsers { usersList ->


            adapter = UserAdapter(usersList, this)
            binding?.recyclerview?.adapter = adapter


        }
        initOnClick()


    }

    private fun initOnClick() {
        binding?.userImage?.setOnClickListener {
            changeImage.launch(AppUtils.pickImg)

            if (context?.let { it1 -> AppUtils.pickImg.resolveActivity(it1.packageManager) } != null) {
                changeImage.launch(AppUtils.pickImg)
            } else {
                showToast("Something went wrong")
            }


        }

        profilePicChangeListner()

    }

    private fun profilePicChangeListner() {


        val docRef = sessionManger.getDb().collection("users").document(getUserId())

// Add a SnapshotListener to the document
        val listener = docRef?.addSnapshotListener { documentSnapshot, e ->
            if (e != null) {
                // Handle any errors here
                return@addSnapshotListener
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                // Document data is available in documentSnapshot
                val data = documentSnapshot.data

                val imageUrl = data?.get("profileUrl")

                binding?.userImage?.loadImageFromUri(Uri.parse(imageUrl.toString()))
                // Handle the data as needed
            } else {
                // Document doesn't exist
            }
        }


    }

    override fun onItemClick(userData: User) {

        //sending the user2 data
        val bundle = Bundle()
        bundle.putSerializable("user", userData)
        findNavController().navigate(R.id.action_usersFragment_to_chatsFragment, bundle)
    }


    override fun onPause() {
        super.onPause()

        userRepository.stopListening()
    }


    fun registerActivityResult() {
        changeImage =

            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data = it.data
                    val imgUri = data?.data

                    //now reducing the pic

                    if (imgUri != null) {

                        // Create a CoroutineScope
                        val coroutineScope = CoroutineScope(Dispatchers.Main)

// Use the CoroutineScope to call the resizeImage function
                        coroutineScope.launch {
                            val resizedImageUri = AppUtils.resizeImage(requireContext(), imgUri, 30)

                            // Handle the resized image URI here, for example, display it in an ImageView
                            if (resizedImageUri != null) {
                                showToast("image uploading...")


                                // Replace "images" with your desired storage folder name
                                val imageRef: StorageReference = sessionManger.getStorageRef()
                                    .child("images/${System.currentTimeMillis()}_${UUID.randomUUID()}.jpg")

// Replace "uriToImage" with the actual Uri of the image you want to upload
                                val uploadTask: UploadTask = imageRef.putFile(resizedImageUri)

                                uploadTask.addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                                    // Get the download URL
                                    val downloadUrl: Task<Uri> = imageRef.downloadUrl
                                    downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                                        val downloadURL = uri.toString()

                                        //testing
                                        //showToast("your url is " + downloadURL)

                                        updateProfile(downloadURL)
                                    // Handle the download URL (e.g., save it to a database or display the uploaded image)
                                    })
                                }).addOnFailureListener(

                                    {
                                        showToast("Something went wrong")
                                    }
                                )


                            } else {
                                showToast("error")
                                // Handle any error or show a message to the user
                            }
                        }

                        sessionManger.getDb().collection("users")
                        //get the image uri
                        //selectedImage.setImageURI(imgUri)
                    }
                }

            }
    }

    private fun updateProfile(downloadURL: String) {

        /*

                // Replace "users" with the name of your collection
                val collection = "users"

        // Replace "userid" with the specific document ID you want to update
                val documentId = sessionManger.getUserId()

        // Create a map with the field you want to update
                val data = hashMapOf(
                    "profileUrl" to "new_profile_url"
                )

        // Reference to the document you want to update
                val docRef = documentId?.let { sessionManger.getDb().collection(collection).document(it) }

        // Update the document with the new data
                docRef?.update("profileUrl",downloadURL)
                    ?.addOnSuccessListener {
                        // Update was successful
                        showToast("Succes")
                    }?.addOnFailureListener(
                        {
                            showToast("Something Went Wrong")
                        }
                    )*/


        sessionManger.getDb().collection("users").document(getUserId()).update(

            "profileUrl",downloadURL
        ).addOnSuccessListener{



        }.addOnFailureListener {
            showToast("something went wrong")

        }




    }
}




