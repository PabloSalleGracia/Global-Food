package com.example.pablo.globalfood.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pablo.globalfood.Model.FavRecipe
import com.example.pablo.globalfood.R
import kotlinx.android.synthetic.main.list_item_recipres.view.*
import java.util.*
import android.R.attr.name
import android.text.method.TextKeyListener.clear



class ListFavRecipesAdapter(private val context: Context, val dataSource: ArrayList<FavRecipe>) : BaseAdapter() {

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
        val title = rowView.findViewById(R.id.descrip_breve_listreview) as TextView

        // Get
        val country = rowView.findViewById(R.id.pais_review) as TextView

        // Get
        val recRes = rowView.findViewById(R.id.plaRes_recres) as TextView

        // Get
        val anadirFav = rowView.findViewById(R.id.anadir_fav_recipres) as TextView

        //--

        // 1
        val favRecipe = getItem(position) as FavRecipe
        // 2
        title.text = favRecipe.title
        country.text = favRecipe.country
        recRes.text = favRecipe.resDish
        if(!favRecipe.esFav){
            anadirFav.text = "Añadir a favs"
        }else{
            anadirFav.text = "Eliminar de favs"
        }

        rowView.anadir_fav_recipres.setOnClickListener{
            if(!favRecipe.esFav){
                favRecipe.esFav = true
                anadirFav.text = "Eliminar de favs"
            }else{
                favRecipe.esFav = false
                anadirFav.text = "Añadir a favs"
            }
        }
        // 3
        //Picasso.with(context).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)

        //borra los datos y la fila seleccionada cuando le das al boton anadir
        /*rowView.anadir_fav_recipres.setOnClickListener{
            dataSource.removeAt(position)
            notifyDataSetChanged()
        }*/


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

    /*fun filter(text: String) {
        var text = text
        items.clear()
        if (text.isEmpty()) {
            items.addAll(itemsCopy)
        } else {
            text = text.toLowerCase()
            for (item in itemsCopy) {
                if (item.name.toLowerCase().contains(text) || item.phone.toLowerCase().contains(text)) {
                    items.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }*/

}