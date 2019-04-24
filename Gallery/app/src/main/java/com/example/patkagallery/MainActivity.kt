package com.example.patkagallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private var listOfFoto = ArrayList<Foto>()
    private lateinit var fotoadapter: MyAdapter
    private var choosedImg = 0;
    private var currentPath: String? = null
    private val TAKE_PICTURE = 1
    private val ZOOM_PICTURE = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view -> dispatchCameraIntent() }
        listview.setOnItemClickListener { _, _, position, _ -> onClickListElement(position) }

        fotoadapter = MyAdapter(this, listOfFoto)

        listOfFoto.add(Foto("foto1.jpg", "Jakiś typek", 1))
        listOfFoto.add(Foto("foto2.jpg", "Para młoda", 5))
        listOfFoto.add(Foto("foto3.jpg", "Dziewczyna", 3))
        listOfFoto.add(Foto("foto4.jpeg", "Shakira ", 4))
        listview.adapter = fotoadapter
        fotoadapter.notifyDataSetChanged()

    }

    /**Check intent response and catch them **/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {

            listOfFoto.add(Foto(currentPath!!, "Nowe zdjęcie", 1))
            fotoadapter.notifyDataSetChanged()

        }

        if (requestCode == ZOOM_PICTURE && resultCode == Activity.RESULT_OK) {

            val stars = data!!.getIntExtra("stars", -1)
            if (stars != -1) {
                listOfFoto.get(choosedImg).stars = stars
            }
            listOfFoto.sortByDescending { it.stars }
            fotoadapter.notifyDataSetChanged()
        }

    }

    /** Run new inntent on click on item list**/
    fun onClickListElement(postion: Int) {

        choosedImg = postion;
        val myintent = Intent(this, SingleImageActivity::class.java)
        myintent.putExtra("name", listOfFoto.get(postion).name)
        myintent.putExtra("content", listOfFoto.get(postion).cotent)
        myintent.putExtra("stars", listOfFoto.get(postion).stars)
        startActivityForResult(myintent, ZOOM_PICTURE)
    }

    /** Create new image**/
    fun createImage(): File {

        val timeStamp = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Date())
        val imageName = "JPEG " + timeStamp + ""
        var storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var image = File.createTempFile(imageName, ".jpg", storageDir)
        currentPath = image.absolutePath
        return image
    }

    /** Run camera intent **/
    fun dispatchCameraIntent() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            var photofile: File? = null
            try {
                photofile = createImage()

            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (photofile != null) {

                var photoToUri = FileProvider.getUriForFile(this, "com.example.patkagallery.fileprovider", photofile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoToUri)
                startActivityForResult(intent, TAKE_PICTURE)
            }

        }
    }

}
