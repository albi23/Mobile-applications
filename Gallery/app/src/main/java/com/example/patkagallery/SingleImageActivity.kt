package com.example.patkagallery

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_single_image.*
import java.io.File

class SingleImageActivity : AppCompatActivity() {

    private val PATH = "storage/emulated/0/Pictures/";
    private var ratingStars = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_image)

        val name = intent.getStringExtra("name")
        ratingBar.progress = intent.getIntExtra("stars",0)
        ratingStars = ratingBar.progress
        descImage.setText("Description: "+intent.getStringExtra("content"))
        var file  = File(PATH+""+name)
        if(name.length > 50) file = File(name)
        imageView.setImageURI(Uri.fromFile(file))
    }


    fun onCllickOK(view :View){

        val intent = Intent()
        var actualProgresRatingBar = -1
        if(ratingStars != ratingBar.progress){
            actualProgresRatingBar = ratingBar.progress
        }
        intent.putExtra("stars",actualProgresRatingBar)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }


}
