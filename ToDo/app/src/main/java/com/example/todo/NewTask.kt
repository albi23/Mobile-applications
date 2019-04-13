package com.example.todo

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import kotlinx.android.synthetic.main.new_tasks.*
import java.text.SimpleDateFormat
import java.util.*

class NewTask : AppCompatActivity() {

   private var choosedImage = "";
   private lateinit var seekBar : SeekBar;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_tasks)
        seekBar = findViewById(R.id.editedSeekBar); // make seekbar object
        seekBar.setOnSeekBarChangeListener(  object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                when(i){

                    1-> editedSeekBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                    2-> editedSeekBar.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                    3-> editedSeekBar.getProgressDrawable().setColorFilter(Color.parseColor("#ED752F"), PorterDuff.Mode.SRC_IN);
                    4-> editedSeekBar.getProgressDrawable().setColorFilter(Color.parseColor("#E34505"), PorterDuff.Mode.SRC_IN);
                    5-> editedSeekBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }


    fun onClickButtonImage(view: View){
        when(view){
            findViewById<ImageButton>(R.id.imageButton2) -> choosedImage = "ss1"
            findViewById<ImageButton>(R.id.imageButton4) -> choosedImage = "ss2"
            findViewById<ImageButton>(R.id.imageButton5) -> choosedImage = "ss3"
            findViewById<ImageButton>(R.id.imageButton3) -> choosedImage = "ss4"
        }
    }




    fun onFinishClick(view: View){

        var inputUserTask : String = editedTask.text.toString();
        var inputUserTime : String = editedTime.text.toString();
        var priority : Int = editedSeekBar.progress

        if(inputUserTask == "") inputUserTask = "empty"
        if(inputUserTime == "") inputUserTime = ""+SimpleDateFormat("dd-MM-yyyy").format(Date())
        if(choosedImage == "" ) choosedImage = "ss1"

        val myintent = Intent()


        myintent.putExtra("task",inputUserTask)
        myintent.putExtra("time",inputUserTime)
        myintent.putExtra("priority",priority.toString())
        myintent.putExtra("img",choosedImage)
        setResult(Activity.RESULT_OK, myintent)
        finish()

    }

}