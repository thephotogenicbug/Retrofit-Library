package com.naveen.retrofitlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.naveen.retrofitlibrary.databinding.ActivityMainBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val baseURL = "https://jsonplaceholder.typicode.com/"

    lateinit var mainBinding: ActivityMainBinding

    var postsList = ArrayList<Posts>()

    lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        showPosts()
    }

    fun showPosts(){
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI : RetrofitAPI = retrofit.create(RetrofitAPI::class.java)

        val call : Call<List<Posts>> = retrofitAPI.getAllPosts()

        call.enqueue(object : Callback<List<Posts>>{
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
               if(response.isSuccessful){

                   mainBinding.progressBar.isVisible = false
                   mainBinding.recyclerView.isVisible = true

                   postsList = response.body() as ArrayList<Posts>

                   adapter = PostsAdapter(postsList)

                   mainBinding.recyclerView.adapter = adapter
               }

            }

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                Toast.makeText(applicationContext,
                 t.localizedMessage,
                    Toast.LENGTH_LONG).show()
            }

        })
    }
}