package com.example.tic_tac_toe

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableRow
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var player1 = ArrayList<Int>();
    var player2 = ArrayList<Int>();
    var boardForBot = ArrayList<Int>();
    var listOfButtons = ArrayList<Button>();
    var activePlayer = 1;
    var winer = -1;
    var mode = "player";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setPlayerOnStart();
        generateListOfButtons();
    }

    fun setPlayerOnStart() {
        activePlayer = Random().nextInt(2);
    }

    fun playerVsPlayerMode(view: View) {
        showBoard()
    }

    fun playerVsBotMode(view: View) {
        showBoard()
        mode = "bot"
        activePlayer = 1;
        generateBotBoard();
    }

    fun generateBotBoard() {
        for (i in 1..25) boardForBot.add(i)
    }

    fun botMove() {

        var idToMove = 0;
        if ((checkPostionOfPlayer(player1, 1, 5, 1))) {
            idToMove = returnIdToMove(1, 5, 1)
        } else if (checkPostionOfPlayer(player1, 6, 10, 1)) {
            idToMove = returnIdToMove(6, 10, 1)
        }
        else if (checkPostionOfPlayer(player1, 11, 15, 1)) {
            idToMove = returnIdToMove(11, 15, 1)
        }
        else if (checkPostionOfPlayer(player1, 16, 20, 1)) {
            idToMove = returnIdToMove(16, 20, 1)
        }
        else if (checkPostionOfPlayer(player1, 21, 25, 1)) {
            idToMove = returnIdToMove(21, 25, 1)
        }
        //kolumns
        else if (checkPostionOfPlayer(player1, 1, 21, 5)) {
            idToMove = returnIdToMove(1, 21, 5)
        }
        else if (checkPostionOfPlayer(player1, 2, 22, 5)) {
            idToMove = returnIdToMove(2, 22, 5)
        }
        else if (checkPostionOfPlayer(player1, 3, 23, 5)) {
            idToMove = returnIdToMove(3, 23, 5)
        }
        else if (checkPostionOfPlayer(player1, 4, 24, 5)) {
            idToMove = returnIdToMove(4, 24, 5)
        }
        else if (checkPostionOfPlayer(player1, 5, 25, 5)) {
            idToMove = returnIdToMove(5, 25, 5)
        }
        //diagonals
        else if (checkPostionOfPlayer(player1, 1, 25, 6)) {
            idToMove = returnIdToMove(1, 25, 6)
        }
        else if (checkPostionOfPlayer(player1, 5, 21, 4)) {
            idToMove = returnIdToMove(5, 21, 4)
        } else {
            idToMove = boardForBot.get(boardForBot.lastIndex)
        }

        generateButtonToMove(idToMove)

    }


    fun returnIdToMove(from: Int, to: Int, step: Int): Int {
        for (i in from..to step step) {
            if (boardForBot.contains(i)) {
                return i
            }
        }
        return 0
    }

    fun generateButtonToMove(idButton: Int) {

        var buttonToMove: Button
        buttonToMove = listOfButtons.get((idButton-1));
        playGame(buttonToMove, idButton)

    }

    fun showBoard() {

        findViewById<Button>(R.id.botMode).visibility = View.GONE;
        findViewById<Button>(R.id.playerMode).visibility = View.GONE;

        findViewById<TableRow>(R.id.tab1).visibility = View.VISIBLE;
        findViewById<TableRow>(R.id.tab2).visibility = View.VISIBLE;
        findViewById<TableRow>(R.id.tab3).visibility = View.VISIBLE;
        findViewById<TableRow>(R.id.tab4).visibility = View.VISIBLE;
        findViewById<TableRow>(R.id.tab5).visibility = View.VISIBLE;
    }

    fun onClick(view: View) {

        var selectedButton = view as Button;
        var celId = 0;

        for((index,value) in listOfButtons.withIndex()){
            if (selectedButton == value){
                celId = index+1;
                break;
            }
        }
        playGame(selectedButton, celId);
    }

    fun playGame(pressdButtton: Button, idButton: Int) {

        boardForBot.remove(idButton)
        if (activePlayer == 1) {
            pressdButtton.text = "X"
            pressdButtton.setBackgroundColor(Color.parseColor("#f368e0"))
            player1.add((idButton))
            activePlayer = 2;
        } else {

            pressdButtton.text = "O"
            pressdButtton.setBackgroundColor(Color.parseColor("#feca57"))
            player2.add((idButton))
            activePlayer = 1
        }

        pressdButtton.isEnabled = false;
        checkBoardPostion(player1, 1)
        checkBoardPostion(player2, 2)

        if(boardForBot.isEmpty()) restartGame()
        if (mode.equals("bot") && activePlayer == 2) {
            botMove()
            activePlayer = 1
        }
    }


    fun checkBoardPostion(playerField: ArrayList<Int>, playerId: Int) {
        //row
        if (checkWiner(playerField, 1, 5, 1)) winer = playerId;
        if (checkWiner(playerField, 6, 10, 1)) winer = playerId;
        if (checkWiner(playerField, 11, 15, 1)) winer = playerId;
        if (checkWiner(playerField, 16, 20, 1)) winer = playerId;
        if (checkWiner(playerField, 21, 25, 1)) winer = playerId;
        //columns
        if (checkWiner(playerField, 1, 21, 5)) winer = playerId;
        if (checkWiner(playerField, 2, 22, 5)) winer = playerId;
        if (checkWiner(playerField, 3, 23, 5)) winer = playerId;
        if (checkWiner(playerField, 4, 24, 5)) winer = playerId;
        if (checkWiner(playerField, 5, 25, 5)) winer = playerId;
        //diagonals
        if (checkWiner(playerField, 1, 25, 6)) winer = playerId;
        if (checkWiner(playerField, 5, 21, 4)) winer = playerId;

        if (winer == 1) {
            Toast.makeText(this, "Player1 win!", Toast.LENGTH_LONG).show()
        } else if (winer == 2) {
            Toast.makeText(this, "Player2 win!", Toast.LENGTH_LONG).show()
        }
    }

    private fun restartGame() {
        player1.clear()
        player2.clear()
        boardForBot.clear()
        generateBotBoard();
        enableButtons();
    }

    fun checkWiner(array: ArrayList<Int>, from: Int, to: Int, step: Int): Boolean {
        for (i in from..to step step) {
            if ((array.contains(i) == false)) return false
        }
        return true
    }

    fun checkPostionOfPlayer(array: ArrayList<Int>, from: Int, to: Int, step: Int): Boolean {

        for (i in from..to step step) {
            if ((array.contains(i))) return false
        }
        return true
    }

    private fun generateListOfButtons(){
        listOfButtons.add(findViewById(R.id.button1))
        listOfButtons.add(findViewById(R.id.button2))
        listOfButtons.add(findViewById(R.id.button3))
        listOfButtons.add(findViewById(R.id.button4))
        listOfButtons.add(findViewById(R.id.button5))
        listOfButtons.add(findViewById(R.id.button6))
        listOfButtons.add(findViewById(R.id.button7))
        listOfButtons.add(findViewById(R.id.button8))
        listOfButtons.add(findViewById(R.id.button9))
        listOfButtons.add(findViewById(R.id.button10))
        listOfButtons.add(findViewById(R.id.button11))
        listOfButtons.add(findViewById(R.id.button12))
        listOfButtons.add(findViewById(R.id.button13))
        listOfButtons.add(findViewById(R.id.button14))
        listOfButtons.add(findViewById(R.id.button15))
        listOfButtons.add(findViewById(R.id.button16))
        listOfButtons.add(findViewById(R.id.button17))
        listOfButtons.add(findViewById(R.id.button18))
        listOfButtons.add(findViewById(R.id.button19))
        listOfButtons.add(findViewById(R.id.button20))
        listOfButtons.add(findViewById(R.id.button21))
        listOfButtons.add(findViewById(R.id.button22))
        listOfButtons.add(findViewById(R.id.button23))
        listOfButtons.add(findViewById(R.id.button24))
        listOfButtons.add(findViewById(R.id.button25))
    }
    private fun enableButtons(){

        for (i in listOfButtons){
            i.isEnabled = true
            i.setBackgroundColor(Color.parseColor("#0c68fc"))
            i.text=""
        }
    }
}
