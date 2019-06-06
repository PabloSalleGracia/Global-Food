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
import java.util.ArrayList

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
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_reviews, parent, false)

        // Get title element

        val id = rowView.findViewById(R.id.id_review) as TextView

        val title = rowView.findViewById(R.id.title_list_recipres) as TextView

        val nombreAutor = rowView.findViewById(R.id.nombre_autor) as TextView

        // Get
        val country = rowView.findViewById(R.id.pais_review) as TextView

        // Get
        val recRes = rowView.findViewById(R.id.plaRes_review) as TextView

        //--

        val review = getItem(position) as Review
        // 2
        id.text = review.idReview.toString()
        title.text = review.title
        nombreAutor.text = review.nombreAutor
        country.text = review.country
        recRes.text = review.resDish


        // 3
        //Picasso.with(context).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)


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