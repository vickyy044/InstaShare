package com.example.instashare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instashare.Dao.PostDao
import com.example.instashare.Dao.UserDao
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePost : AppCompatActivity() {
    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        postDao = PostDao()
        bt_post.setOnClickListener {
            val input = et_post.text.toString().trim()

            if(input.isNotEmpty()){
                postDao.addPost(input)
                finish()
            }
        }
    }
}