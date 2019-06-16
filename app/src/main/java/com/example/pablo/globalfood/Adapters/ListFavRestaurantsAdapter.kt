package com.example.pablo.globalfood.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pablo.globalfood.Model.FavRestaurant
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.list_item_recipres.view.*
import java.util.*

class ListFavRestaurantsAdapter (private val context: Context, val dataSource: ArrayList<FavRestaurant>) : BaseAdapter() {

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
        val rowView = inflater.inflate(R.layout.list_item_recipres, parent, false)
        val title = rowView.findViewById(R.id.descrip_breve_listreview) as TextView
        val country = rowView.findViewById(R.id.pais_review) as TextView
        val recRes = rowView.findViewById(R.id.plaRes_recres) as TextView
        val anadirFav = rowView.findViewById(R.id.anadir_fav_recipres) as TextView
        val favRestaurant = getItem(position) as FavRestaurant

        title.text = favRestaurant.title
        country.text = favRestaurant.country
        recRes.text = favRestaurant.resDish
        if(!favRestaurant.esFav){
            anadirFav.text = context.getString(R.string.add_to_favs)
        }else{
            anadirFav.text = context.getString(R.string.delete_from_favs)
        }

        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        var botonPulsadoMenos = false

        rowView.anadir_fav_recipres.setOnClickListener {
            if (favRestaurant.esFav && !botonPulsadoMenos) {
                db.collection("Usuario-Restaurantes")
                        .whereEqualTo("id_usuario", refUserId)
                        .whereEqualTo("titulo", favRestaurant.title)
                        .addSnapshotListener { values, _ ->
                            if (values != null) {
                                for (doc in values) {
                                    if (doc.getString("titulo") != null) {
                                        if (!botonPulsadoMenos) {
                                            db.collection("Usuario-Restaurantes").document(doc.id).update("esFav?", false)
                                                    .addOnSuccessListener {
                                                        favRestaurant.esFav = false
                                                        botonPulsadoMenos = true
                                                        notifyDataSetChanged()
                                                    }
                                        }
                                    }
                                }
                            }
                        }
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