package com.example.w04b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onCLick(view: View) {
        Toast.makeText(this, "Dziala!", Toast.LENGTH_SHORT).show()
        val myintent = Intent(this, Main2Activity::class.java )
        myintent.putExtra("data", editText.text.toString())
        //startActivity(myintent)
        startActivityForResult(myintent, 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            val s = data?.getStringExtra("wynik")
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        }

    }

}
