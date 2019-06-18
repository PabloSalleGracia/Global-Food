package com.example.pablo.globalfood.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pablo.globalfood.Model.MyRecipe
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.list_item_recipres.view.*

class ListMyRecipesAdapter (private val context: Context, val dataSource: ArrayList<MyRecipe>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var buscadoraux:ArrayList<MyRecipe>
    private var buscador:ArrayList<MyRecipe>

    init{
        buscadoraux = ArrayList()
        buscadoraux.addAll(dataSource)

        buscador = dataSource
    }


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



        val rowView = inflater.inflate(R.layout.list_item_recipres, parent, false)
        val title = rowView.findViewById(R.id.descrip_breve_listreview) as TextView
        val country = rowView.findViewById(R.id.pais_review) as TextView
        val recRes = rowView.findViewById(R.id.plaRes_recres) as TextView
        val anadirFav = rowView.findViewById(R.id.anadir_fav_recipres) as TextView

        val myRecipe = getItem(position) as MyRecipe
        title.text = myRecipe.title
        country.text = myRecipe.country
        recRes.text = myRecipe.resDish
        if(!myRecipe.esFav){
            anadirFav.text = context.getString(R.string.add_to_favs)
        }else{
            anadirFav.text = context.getString(R.string.delete_from_favs)
        }

        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        var botonPulsadoMas = false
        var botonPulsadoMenos = false

        rowView.anadir_fav_recipres.setOnClickListener{
            if(myRecipe.resDish == "Plato") {
                if (myRecipe.esFav && !botonPulsadoMenos) {
                    db.collection("Usuario-Recetas")
                            .whereEqualTo("id_usuario", refUserId)
                            .whereEqualTo("titulo", myRecipe.title)
                            .addSnapshotListener { values, _ ->
                                if (values != null) {
                                    for (doc in values) {
                                        if (doc.getString("titulo") != null) {
                                            if (!botonPulsadoMenos) {
                                                db.collection("Usuario-Recetas").document(doc.id).update("esFav?", false)
                                                        .addOnSuccessListener {
                                                            //despues de hacer el update a false snapshot se llama porque ha recibido cambio
                                                            //e intenta hacer otro update a false pero como ahi ya no cambia solo hace 2 iteraciones
                                                            //mirar de solucionarlo y que solo haga 1
                                                            myRecipe.esFav = false
                                                            botonPulsadoMenos = true
                                                            anadirFav.text = context.getString(R.string.add_to_favs)
                                                            notifyDataSetChanged()

                                                            /*db.collection("Recetas").whereEqualTo("titulo", myRecipe.title)
                                                                .get()
                                                                .addOnSuccessListener { anafav ->
                                                                    for(documen in anafav){
                                                                        db.collection("Recetas").document(documen.id).update("numFavs", documen.data["numFavs"] as Long - 1)
                                                                    }
                                                                }*/
                                                            /*db.collection("Recetas").whereEqualTo("titulo", myRecipe.title)
                                                                .addSnapshotListener{receta, _ ->
                                                                    if(receta != null){
                                                                        for(docRec in receta){
                                                                            if(docRec.getString("titulo") != null){
                                                                                db.collection("Recetas").document(docRec.id).update("numFavs", docRec.getLong("numFavs")!!-1)
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
                } else if (!myRecipe.esFav && !botonPulsadoMas) {
                    db.collection("Usuario-Recetas")
                            .whereEqualTo("id_usuario", refUserId)
                            .whereEqualTo("titulo", myRecipe.title)
                            .addSnapshotListener { values, _ ->
                                if (values != null) {
                                    for (doc in values) {
                                        if (doc.getString("titulo") != null) {
                                            if (!botonPulsadoMas) {
                                                db.collection("Usuario-Recetas").document(doc.id).update("esFav?", true)
                                                        .addOnSuccessListener {
                                                            myRecipe.esFav = true
                                                            botonPulsadoMas = true
                                                            anadirFav.text = context.getString(R.string.delete_from_favs)
                                                            notifyDataSetChanged()

                                                            /*db.collection("Recetas").whereEqualTo("titulo", myRecipe.title)
                                                                .get()
                                                                .addOnSuccessListener { anafav ->
                                                                    for(documen in anafav){
                                                                        db.collection("Recetas").document(documen.id).update("numFavs", documen.data["numFavs"] as Long + 1)
                                                                    }
                                                                }*/
                                                            /*db.collection("Recetas").whereEqualTo("titulo", myRecipe.title)
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
            }else{
                if (myRecipe.esFav && !botonPulsadoMenos) {
                    db.collection("Usuario-Restaurantes")
                            .whereEqualTo("id_usuario", refUserId)
                            .whereEqualTo("titulo", myRecipe.title)
                            .addSnapshotListener { values, _ ->
                                if (values != null) {
                                    for (doc in values) {
                                        if (doc.getString("titulo") != null) {
                                            if (!botonPulsadoMenos) {
                                                db.collection("Usuario-Restaurantes").document(doc.id).update("esFav?", false)
                                                        .addOnSuccessListener {
                                                            myRecipe.esFav = false
                                                            botonPulsadoMenos = true
                                                            anadirFav.text = context.getString(R.string.add_to_favs)
                                                            notifyDataSetChanged()
                                                        }
                                            }
                                        }
                                    }
                                }
                            }
                } else if (!myRecipe.esFav && !botonPulsadoMas) {
                    db.collection("Usuario-Restaurantes")
                            .whereEqualTo("id_usuario", refUserId)
                            .whereEqualTo("titulo", myRecipe.title)
                            .addSnapshotListener { values, _ ->
                                if (values != null) {
                                    for (doc in values) {
                                        if (doc.getString("titulo") != null) {
                                            if (!botonPulsadoMas) {
                                                db.collection("Usuario-Restaurantes").document(doc.id).update("esFav?", true)
                                                        .addOnSuccessListener {
                                                            myRecipe.esFav = true
                                                            botonPulsadoMas = true
                                                            anadirFav.text = context.getString(R.string.delete_from_favs)
                                                            notifyDataSetChanged()
                                                        }
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
        buscador.clear()
        if (text.isEmpty()) {
            buscador.addAll(buscadoraux)
        } else {
            for (item in buscadoraux) {
                if (item.title.toLowerCase().contains(text) || item.country.toLowerCase().contains(text) || item.resDish.toLowerCase().contains(text)) {
                    buscador.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }
}