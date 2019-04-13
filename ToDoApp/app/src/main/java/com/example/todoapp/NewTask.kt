package com.example.todoapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class New : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_tasks)



    }

//    fun onClick(view: View) {
//        val myintent = Intent()
//        myintent.putExtra("wynik", "wszystko ok")
//        setResult(Activity.RESULT_OK, myintent)
//        finish()
//    }
}