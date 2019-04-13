package com.example.w04b

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main2.*


class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val extra = intent.getStringExtra("data")
        textView5.text = extra

    }

    fun onClick(view: View) {
        val myintent = Intent()
        myintent.putExtra("wynik", "wszystko ok")
        setResult(Activity.RESULT_OK, myintent)
        finish()
    }
}
