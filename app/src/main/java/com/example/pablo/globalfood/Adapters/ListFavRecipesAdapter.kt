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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.list_item_recipres.view.*
import java.util.*


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
        val rowView = inflater.inflate(R.layout.list_item_recipres, parent, false)
        val title = rowView.findViewById(R.id.descrip_breve_listreview) as TextView
        val country = rowView.findViewById(R.id.pais_review) as TextView
        val recRes = rowView.findViewById(R.id.plaRes_recres) as TextView
        val anadirFav = rowView.findViewById(R.id.anadir_fav_recipres) as TextView
        val favRecipe = getItem(position) as FavRecipe

        title.text = favRecipe.title
        country.text = favRecipe.country
        recRes.text = favRecipe.resDish
        if(!favRecipe.esFav){
            anadirFav.text = context.getString(R.string.add_to_favs)
        }else{
            anadirFav.text = context.getString(R.string.delete_from_favs)
        }

        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        var botonPulsadoMenos = false

        rowView.anadir_fav_recipres.setOnClickListener{
            if(favRecipe.esFav && !botonPulsadoMenos){
                db.collection("Usuario-Recetas")
                        .whereEqualTo("id_usuario", refUserId)
                        .whereEqualTo("titulo", favRecipe.title)
                        .addSnapshotListener { values, _ ->
                            if (values != null) {
                                for (doc in values) {
                                    if (doc.getString("titulo") != null) {
                                        if(!botonPulsadoMenos){
                                            db.collection("Usuario-Recetas").document(doc.id).update("esFav?", false)
                                                    .addOnSuccessListener {
                                                        //despues de hacer el update a false snapshot se llama porque ha recibido cambio
                                                        //e intenta hacer otro update a false pero como ahi ya no cambia solo hace 2 iteraciones
                                                        //mirar de solucionarlo y que solo haga 1
                                                        favRecipe.esFav = false
                                                        botonPulsadoMenos = true
                                                        notifyDataSetChanged()
                                                        /*db.collection("Recetas").whereEqualTo("titulo", favRecipe.title)
                                                                .get()
                                                                .addOnSuccessListener { anafav ->
                                                                    for(documen in anafav){
                                                                        db.collection("Recetas").document(documen.id).update("numFavs", documen.data["numFavs"] as Long - 1)
                                                                    }
                                                                }*/

                                                        /*db.collection("Recetas").whereEqualTo("titulo", favRecipe.title)
                                                                .addSnapshotListener{receta, _ ->
                                                                    if(receta != null){
                                                                        for(docRec in receta){
                                                                            if(docRec.getString("titulo") != null){
                                                                                db.collection("Recetas").document(docRec.id).update("numFavs", docRec.getLong("numFavs")!!+1)
                                                                            }
                                                                        }
                                                                    }

                                                                }*/
                                                    }
                                        }
                                    }
                                }
                            }
                        }
            }
        }
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

}