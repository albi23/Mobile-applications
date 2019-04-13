package com.example.todo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter (context: Context, var data: ArrayList<Item>) :
    ArrayAdapter<Item>(context, R.layout.my_layout_item, data) {


    private class ViewHolder(row: View?) {
        var tvTask: TextView? = null
        var tvDate: TextView? = null
        var tvPrior: TextView? = null
        var tvImage: ImageView? = null

        init {
            this.tvTask = row?.findViewById(R.id.tvTask)
            this.tvDate = row?.findViewById(R.id.tvDate)
            this.tvPrior = row?.findViewById(R.id.tvPrior)
            this.tvImage = row?.findViewById(R.id.tvImage)
        }
    }

    private val images: IntArray = intArrayOf(
        R.drawable.ss1,
        R.drawable.ss2,
        R.drawable.ss3,
        R.drawable.ss4
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val viewHolder: ViewHolder
        var view = convertView

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.my_layout_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else {
            view = convertView
            viewHolder = view?.tag as ViewHolder
        }
        viewHolder.tvTask?.text = data[position].task
        viewHolder.tvDate?.text = data[position].date
        viewHolder.tvPrior?.text = ""+data[position].priority
        setImage(viewHolder.tvImage, data[position].image)


        return view as View
    }

    private fun setImage(tvImage: ImageView?, image: String) {
        when (image) {
            "ss1" -> tvImage?.setImageResource(images[0])
            "ss2" -> tvImage?.setImageResource(images[1])
            "ss3" -> tvImage?.setImageResource(images[2])
            "ss4" -> tvImage?.setImageResource(images[3])
        }
    }
}



