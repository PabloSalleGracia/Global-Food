package com.example.pablo.globalfood.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.pablo.globalfood.Model.MyRecipe
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.list_item_recipres.view.*
import java.util.*
import kotlin.collections.ArrayList

class ListMyRecipesAdapter (private val context: Context, val dataSource: ArrayList<MyRecipe>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var buscador:ArrayList<MyRecipe>

    @SuppressLint("ViewHolder")
    /*
private List<NombresAnimales> nombreListaAnimales = null;     private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }
    */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        buscador = ArrayList()
        buscador.addAll(dataSource)
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

        val myRecipe = getItem(position) as MyRecipe
        // 2
        title.text = myRecipe.title
        country.text = myRecipe.country
        recRes.text = myRecipe.resDish
        if(!myRecipe.esFav){
            anadirFav.text = "Añadir a favs"
        }else{
            anadirFav.text = "Eliminar de favs"
        }


        // 3
        //Picasso.with(context).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)

        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        var botonPulsadoMas = false
        var botonPulsadoMenos = false

        rowView.anadir_fav_recipres.setOnClickListener{
            if(myRecipe.esFav && !botonPulsadoMenos){
                db.collection("Usuario-Recetas")
                        .whereEqualTo("id_usuario", refUserId)
                        .whereEqualTo("titulo", myRecipe.title)
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
                                                        myRecipe.esFav = false
                                                        botonPulsadoMenos = true
                                                        anadirFav.text = "Añadir a favs"
                                                        notifyDataSetChanged()
                                                    }
                                        }
                                    }
                                }
                            }
                        }
            }else if(!myRecipe.esFav && !botonPulsadoMas) {
                db.collection("Usuario-Recetas")
                        .whereEqualTo("id_usuario", refUserId)
                        .whereEqualTo("titulo", myRecipe.title)
                        .addSnapshotListener { values, _ ->
                            if (values != null) {
                                for (doc in values) {
                                    if (doc.getString("titulo") != null) {
                                        if(!botonPulsadoMas){
                                            db.collection("Usuario-Recetas").document(doc.id).update("esFav?", true)
                                                    .addOnSuccessListener {
                                                        myRecipe.esFav = true
                                                        botonPulsadoMas = true
                                                        anadirFav.text = "Eliminar de favs"
                                                        notifyDataSetChanged()
                                                    }
                                        }
                                    }
                                }
                            }
                        }
            }
        }

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

    fun filter(text: String) {
        text.toLowerCase()
        dataSource.clear()
        if (text.isEmpty()) {
            dataSource.addAll(buscador)
        } else {
            for (item in buscador) {
                if (item.title.toLowerCase().contains(text) || item.country.toLowerCase().contains(text) || item.resDish.toLowerCase().contains(text)) {
                    dataSource.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }
}