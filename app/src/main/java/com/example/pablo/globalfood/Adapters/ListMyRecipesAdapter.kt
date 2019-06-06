package com.example.pablo.globalfood.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.pablo.globalfood.Model.MyRecipe
import com.example.pablo.globalfood.R
import kotlinx.android.synthetic.main.list_item_recipres.view.*
import java.util.*

class ListMyRecipesAdapter (private val context: Context, val dataSource: ArrayList<MyRecipe>) : BaseAdapter() {

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
        val rowView = inflater.inflate(R.layout.list_item_recipres, parent, false)

        // Get title element
        val title = rowView.findViewById(R.id.title_list_recipres) as TextView

        // Get
        val country = rowView.findViewById(R.id.pais_review) as TextView

        // Get
        val recRes = rowView.findViewById(R.id.plaRes_recres) as TextView

        // Get
        val anadirFav = rowView.findViewById(R.id.anadir_fav_recipres) as TextView

        //--

        val myRecipe = getItem(position) as MyRecipe
        // 2
        title.text = myRecipe.title
        country.text = myRecipe.country
        recRes.text = myRecipe.resDish
        if(myRecipe.esFav == 0){
            anadirFav.text = "Añadir a favs"
        }else{
            anadirFav.text = "Eliminar de favs"
        }

        // 3
        //Picasso.with(context).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)

        rowView.anadir_fav_recipres.setOnClickListener{
            if(myRecipe.esFav == 0){
                myRecipe.esFav = 1
                anadirFav.text = "Eliminar de favs"
            }else{
                myRecipe.esFav = 0
                anadirFav.text = "Añadir a favs"
            }
        }

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