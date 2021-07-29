package com.example.instashare.Dao

import com.example.instashare.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {
    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")

     fun addUSer(user : User){
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                userCollection.document(user.userId).set(it)
            }

        }
    }
}