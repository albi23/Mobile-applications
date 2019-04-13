package com.example.todo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog_layout.view.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {


    private var listOfTasks = ArrayList<Item>()
    private lateinit var taskAdapter: MyAdapter
    private var choosedImg = ""
    private var firstClickOnSort = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view -> requestNewTask() }
        listview.setOnItemLongClickListener { _, _, position, _ ->
            showConfirmation(position)
            true
        }


        taskAdapter = MyAdapter(this, listOfTasks);

        listOfTasks.add(Item("A trip with friends", 4, "ss2", "13-05-2019"))
        listOfTasks.add(Item("The alcohol festival", 5, "ss2", "17-05-2019"))
        listOfTasks.add(Item("Extra work", 2, "ss4", "18-05-2019"))
        listOfTasks.add(Item("Design a house", 2, "ss1", "25-05-2019"))

        listview.adapter = taskAdapter
        taskAdapter.notifyDataSetChanged();

    }

    /**Setting menu in window*/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Handler with options in menu list
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_sorting -> {
                return if (firstClickOnSort) {
                    sortByPriority(true);firstClickOnSort = false; true
                } else {
                    sortByPriority(false); firstClickOnSort = true ; true
                }
            }
            R.id.sorting_date ->{
                return if (firstClickOnSort) {
                    sortByDate(true);firstClickOnSort = false; true
                } else {
                    sortByDate(false); firstClickOnSort = true ; true
                }
            }
            R.id.sorting_img -> {
                return if (firstClickOnSort) {
                    sortByImg(true);firstClickOnSort = false; true
                } else {
                    sortByImg(false); firstClickOnSort = true ; true
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Run new intent with window new task
     */
    private fun requestNewTask() {
        val myintent = Intent(this, NewTask::class.java)
        startActivityForResult(myintent, 123)

    }

    /**Getting data from input user and create new task*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            addTask(
                data!!.getStringExtra("task"),
                data!!.getStringExtra("priority").toInt(),
                data.getStringExtra("img"),
                data.getStringExtra("time")
            )
        }

    }

    /**showing window to edit/remove*/
    private fun showConfirmation(index: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("What do you want to do with this item?")
        builder.setPositiveButton("Delete") { _, _ ->
            deleteItem(index)
            Toast.makeText(applicationContext, "Task deleted!", Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("Edit") { _, _ ->
            showDialog(
                listOfTasks[index].task,
                listOfTasks[index].image,
                listOfTasks[index].priority,
                listOfTasks[index].date,
                index
            )
        }
        builder.show()
    }


    @SuppressLint("InflateParams", "SetTextI18n")
    private fun showDialog(task: String, image: String, prior: Int, date: String, idx: Int) {


        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle(task)
        val windowView = inflater.inflate(R.layout.dialog_layout, null)
        builder.setView(windowView)

        windowView.editedTime.setText(date)
        windowView.editedTask.setText(task)
        windowView.editedSeekBar.progress = prior;


        builder.setPositiveButton("OK") { _, _ ->
            if (idx != -1) {
                var img = choosedImg;
                if (choosedImg == "") img = image
                listOfTasks[idx] = Item(
                    windowView.editedTask.text.toString(),
                    windowView.editedSeekBar.progress,
                    img,
                    windowView.editedTime.text.toString()
                )
                taskAdapter.notifyDataSetChanged()
                choosedImg = ""
            }
        }
        builder.show()
    }

    fun setChangedImage(view: View) {

        when (view) {
            view.image1 -> choosedImg = "ss1";
            view.image2 -> choosedImg = "ss3"
            view.image3 -> choosedImg = "ss4"
            view.image4 -> choosedImg = "ss2"
        }

    }

    private fun deleteItem(index: Int) {

        listOfTasks.removeAt(index)
        taskAdapter.notifyDataSetChanged()
    }

    private fun addTask(task: String, priority: Int, image: String, date: String) {
        val listItem = Item(task, priority, image, date)
        listOfTasks.add(listItem)
        taskAdapter.notifyDataSetChanged()
    }

    private fun sortByPriority(desc: Boolean) {
        if (desc) {
            listOfTasks.sortByDescending { it.priority }
        } else {
            listOfTasks.sortBy { it.priority }
        }
        taskAdapter.notifyDataSetChanged()

    }


    private fun sortByDate(desc: Boolean) {
        if (desc) {
            listOfTasks.sortByDescending { it.date }
        } else {
            listOfTasks.sortBy { it.date }
        }
        taskAdapter.notifyDataSetChanged()
    }

    private fun sortByImg(desc: Boolean) {
        if (desc) {
            listOfTasks.sortByDescending { it.image }
        } else {
            listOfTasks.sortBy { it.image }
        }
        taskAdapter.notifyDataSetChanged()

    }

}
