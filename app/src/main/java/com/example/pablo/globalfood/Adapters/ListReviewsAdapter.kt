package com.example.pablo.globalfood.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pablo.globalfood.Model.Review
import com.example.pablo.globalfood.R
import java.util.*

class ListReviewsAdapter (private val context: Context, val dataSource: ArrayList<Review>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    @SuppressLint("ViewHolder")
    /*
     private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }
    */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.list_item_reviews, parent, false)
        val nombreAutor = rowView.findViewById(R.id.nombre_autor) as TextView
        val descripBreve = rowView.findViewById(R.id.descrip_breve_listreview) as TextView
        val review = getItem(position) as Review

        nombreAutor.text = review.nombreAutor
        descripBreve.text = review.descripBreve

        notifyDataSetChanged()
        return rowView
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}