package com.example.memes_up

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        loadMeme()
//    }
//
//    private fun loadMeme() {
//
//// Instantiate the RequestQueue.
//        val queue = Volley.newRequestQueue(this)
//        val url = "https://www.google.com"
//
//// Request a string response from the provided URL.
//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            { response ->
//                Toast.makeText(this,"Hello Volley", Toast.LENGTH_LONG).show()
//
//            },
//            { })
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest)
//
//    }
//
//    fun nextMeme(view: View) {}
//    fun shareMeme(view: View) {}
//}







class MainActivity : AppCompatActivity() {

    var currentImageUrl: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    private fun loadMeme() {

        var progressBar = findViewById<ProgressBar>(R.id.progressBar)

        progressBar.visibility = View.VISIBLE

        val memeImageView = findViewById<ImageView>(R.id.memeImageView)

// Instantiate the RequestQueue.

        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val JsonObjectRequest1 = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
//                Toast.makeText(this,"Hello Volley", Toast.LENGTH_LONG).show()
                val currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                }).into(memeImageView)
            },
            { })

// Add the request to the RequestQueue.
//        queue.add(JsonObjectRequest1) Before SingleTon is here
        MySingleton.getInstance(this).addToRequestQueue(JsonObjectRequest1)

    }

    fun nextMeme(view: View) {
        loadMeme()
    }
    fun shareMeme(view: View) {
        val intent =Intent(Intent.ACTION_SEND)
        intent.type ="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Don't check the meme I got from reddit   $currentImageUrl")
        val chooser = Intent.createChooser(intent,"Share this MEME using <>")
        startActivity(chooser)
    }
}