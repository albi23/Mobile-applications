package com.example.matematicaapp

import android.graphics.Color.rgb
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private var counter = 10
    private var randomNumber = 0
    private var range = 50
    private var oldRange = 0
    private var lives = 5
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNumber();
    }

    fun pressedYes(view: View){

        if(randomNumber%3 == 0){
            score++;
            findViewById<TextView>(R.id.score).text = "$score"
            increseProgresBar()
        }else{
            decreaseLives()
        }
        setNumber()
    }

    fun pressedNo(view: View){

        if(randomNumber%3 != 0){
            score++;
            findViewById<TextView>(R.id.score).text = "$score"
            increseProgresBar()
        }else{
            decreaseLives()
        }
        setNumber()
    }

    fun increseProgresBar() {

        Toast.makeText(this, "Good!", Toast.LENGTH_SHORT).show()
        counter += 10
        findViewById<ProgressBar>(R.id.lvlProgres).progress = counter
        if(counter == 100){
            nextLvl()
        }
    }

    fun zeroProgresBar(){
        findViewById<ProgressBar>(R.id.lvlProgres).progress = counter
    }

    fun decreaseLives() {

        Toast.makeText(this, "Bad!", Toast.LENGTH_SHORT).show()
        lives--
        findViewById<RatingBar>(R.id.Lives).numStars = lives
        if(lives == 0){
            setNewGame();
        }

    }

    fun setNewGame(){
        Toast.makeText(this, "You Lost!!", Toast.LENGTH_SHORT).show()
        counter = 10;
        lives = 5;
        findViewById<RatingBar>(R.id.Lives).numStars = lives
        score =0;
        findViewById<TextView>(R.id.score).text = "$score"
        oldRange =0;
        range= 50;
    }

    fun setNumber() {

        val r = Random()
        randomNumber = r.nextInt(range)+oldRange
        findViewById<TextView>(R.id.showNumber).text = "$randomNumber"
    }

    fun nextLvl(){

        Toast.makeText(this, "Next Level!!", Toast.LENGTH_SHORT).show()
        counter = 10
        zeroProgresBar()
        oldRange = range
        range += range
        findViewById<ProgressBar>(R.id.lvlProgres).getProgressDrawable().setColorFilter(rgb(Random().nextInt(256),Random().nextInt(256),Random().nextInt(256)), PorterDuff.Mode.SRC_IN);
    }

//    object CutTimer : CountDownTimer(100000,1000){
//
//        var timer : Long = 0;
//        override fun onFinish() {
//
//        }
//
//        override fun onTick(milisecund : Long) {
//            timer = milisecund/1000;
//        }
//
//    }

}
