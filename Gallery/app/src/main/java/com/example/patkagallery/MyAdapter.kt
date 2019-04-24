package com.example.patkagallery

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.File

class MyAdapter (context: Context, var data: ArrayList<Foto>) :
    ArrayAdapter<Foto>(context, R.layout.image_layout, data) {

    private val PATH = "storage/emulated/0/Pictures/";

    /** get all elements to fill from xml layout**/
    private class ViewHolder(row: View?) {

        var imageDesc: TextView? = null
        var imageStars: TextView? = null
        var image: ImageView? = null

        init {
            this.image = row?.findViewById(R.id.image)
            this.imageStars = row?.findViewById(R.id.imageStars)
            this.imageDesc = row?.findViewById(R.id.imageDesc)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val viewHolder: ViewHolder
        var view = convertView

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.image_layout, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else {
            view = convertView
            viewHolder = view?.tag as ViewHolder
        }
        viewHolder.imageStars?.text = "Stars : "+data[position].stars.toString()
        viewHolder.imageDesc?.text = data[position].cotent

        var file = File(PATH+""+data[position].name)
      if(data[position].name.length > 50)  { /** Recognize -> img from camera or not **/
          file = File(data[position].name)
      }

        viewHolder.image?.setImageURI(Uri.fromFile(file))


        return view as View
    }

}



