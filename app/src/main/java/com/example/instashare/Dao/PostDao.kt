package com.example.instashare.Dao

import com.example.instashare.models.Post
import com.example.instashare.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("post")
    val auth = Firebase.auth

    fun addPost(text: String){
        val currentUSerId = auth.currentUser!!.uid
        GlobalScope.launch {
            val userDao = UserDao()
            val user = userDao.getUser(currentUSerId).await().toObject(User::class.java)!!

            val currentTime = System.currentTimeMillis()
            val post = Post(text, user, currentTime)
            postCollection.document().set(post)
        }
    }

    fun getPostById(postId: String): Task<DocumentSnapshot> {
        return postCollection.document(postId).get()
    }
    fun updateLike(postId: String){
        GlobalScope.launch {
            val currentUser = Firebase.auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)!!

            val isLiked = post.likedBy.contains(currentUser)

            if(isLiked){
                post.likedBy.remove(currentUser)
            }else{
                post.likedBy.add(currentUser)
            }

            postCollection.document(postId).set(post)
        }
    }
}