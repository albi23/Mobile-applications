package com.example.hangman

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.util.*

/*
* @Author Albert Piekielny
* */
class MainActivity : AppCompatActivity() {

    private var arrayOfPsswords: Array<String> = emptyArray();
    private var actualImg = 1;
    private var passwordToGuess = "";
    private var passwordOnScreen = "";
    private var usedLetter = "";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arrayOfPsswords = resources.getStringArray(R.array.passwords);
        setPasswordToGuess();
    }

    fun onClick(view: View) {

        val inputLetter = findViewById<EditText>(R.id.inputLetters).text.toString().toUpperCase();
        if (inputLetter.length != 1) {
            Toast.makeText(this, "Enter one letter!", Toast.LENGTH_SHORT).show()
        } else if (!checkInputLetter(inputLetter)) {
            Toast.makeText(this, "Enter letter!", Toast.LENGTH_SHORT).show()
        } else {
            if (isLetterInPassword(inputLetter)) {
                uncoverLetterInPassword(inputLetter);
                clearInputText();
            } else {
                if (addUsedLetter(inputLetter)) {
                    changeImg();
                }
                clearInputText();

            }

        }
    }

    fun clearInputText() {
        findViewById<EditText>(R.id.inputLetters).setText("");
    }

    fun setPasswordToGuess() {

        var randomPassword = Random().nextInt(arrayOfPsswords.size);
        passwordToGuess = arrayOfPsswords.get(randomPassword);

        for (i in passwordToGuess.iterator()) {
            passwordOnScreen += "-";
        }
        setPasswordOnScreen();
        println("passwordToGuess " + passwordToGuess)
    }

    fun checkInputLetter(input: String): Boolean {

        val lettering = "AĄBCĆDEĘFGHIJKLŁMNŃOÓPQRSŚTUVWXYZŻŹ"
        if (lettering.contains(input.toUpperCase())) return true

        return false
    }

    fun changeImg() {

        var uri = "@drawable/s" + actualImg;
        var imageview = findViewById<ImageView>(R.id.hangManImage)
        var res = resources.getDrawable(resources.getIdentifier(uri, null, packageName))
        imageview.setImageDrawable(res)
        actualImg++;
        if (actualImg == 10) {
            uri = "@drawable/s" + actualImg;
            imageview = findViewById<ImageView>(R.id.hangManImage)
            res = resources.getDrawable(resources.getIdentifier(uri, null, packageName))
            imageview.setImageDrawable(res)
            findViewById<TextView>(R.id.PasswordView).text = passwordToGuess.toUpperCase();
            setInvisible();
        }
    }

    fun setInvisible() {
        findViewById<TextView>(R.id.Used).visibility = View.INVISIBLE;
        findViewById<TextView>(R.id.UsedLetters).visibility = View.INVISIBLE;
        findViewById<EditText>(R.id.inputLetters).visibility = View.INVISIBLE;
        findViewById<Button>(R.id.checkButtona).visibility = View.INVISIBLE;
        findViewById<Button>(R.id.restartButton).visibility = View.VISIBLE;
    }

    fun restartGame(view: View) {
        findViewById<Button>(R.id.restartButton).visibility = View.INVISIBLE;
        findViewById<TextView>(R.id.Used).visibility = View.VISIBLE;
        findViewById<TextView>(R.id.UsedLetters).visibility = View.VISIBLE;
        findViewById<EditText>(R.id.inputLetters).visibility = View.VISIBLE;
        findViewById<Button>(R.id.checkButtona).visibility = View.VISIBLE;
        actualImg = 0;
        usedLetter = "";
        passwordOnScreen = "";
        findViewById<TextView>(R.id.UsedLetters).text = "";
        changeImg();
        setPasswordToGuess();
        clearInputText();
    }


    fun isLetterInPassword(letter: String): Boolean {
        if ((passwordToGuess.toUpperCase()).contains(letter.toUpperCase())) return true
        return false
    }

    fun uncoverLetterInPassword(letter: String) {

        for ((index, value) in passwordToGuess.iterator().withIndex()) {
            if (value.toString().toUpperCase() == letter) {
                passwordOnScreen = "" + passwordOnScreen.subSequence(0, index) + value + passwordOnScreen.subSequence(
                    (index + 1),
                    passwordOnScreen.length
                )
            }
        }
        setPasswordOnScreen();
        if (passwordOnScreen.equals(passwordToGuess)) {
            actualImg = 11;
            changeImg();
            setInvisible();
        }
    }

    fun setPasswordOnScreen() {
        findViewById<TextView>(R.id.PasswordView).text = passwordOnScreen.toUpperCase();
    }

    fun addUsedLetter(letter: String): Boolean {
        if (!((usedLetter.toUpperCase()).contains(letter.toUpperCase()))) {
            usedLetter += letter.toUpperCase() + " "
            findViewById<TextView>(R.id.UsedLetters).text = usedLetter;
            return true
        }
        Toast.makeText(this, "You have just used this letter", Toast.LENGTH_SHORT).show()
        return false
    }
}
