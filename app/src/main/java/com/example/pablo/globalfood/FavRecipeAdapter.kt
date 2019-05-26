package com.example.pablo.globalfood

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class FavRecipeAdapter(private val context: Context, private val dataSource: ArrayList<FavRecipe>) : BaseAdapter() {

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
        val rowView = inflater.inflate(R.layout.list_item_fav_recipe, parent, false)

        // Get title element
        val titleTextView = rowView.findViewById(R.id.titleFavRec) as TextView

        // Get subtitle element
        val subtitleTextView = rowView.findViewById(R.id.descriptionFavRec) as TextView

        //--

        // 1
        val favRecipe = getItem(position) as FavRecipe
        // 2
        titleTextView.text = favRecipe.title
        subtitleTextView.text = favRecipe.description

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