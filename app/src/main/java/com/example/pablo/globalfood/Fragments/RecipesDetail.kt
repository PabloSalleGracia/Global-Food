package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.OnTitleSelectedListener

import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.detail_recipes.*
import java.util.HashMap


private const val tituloRecibido = "datosRecibidos"

class RecipesDetail : Fragment() {

    private var tituloRecDet: String? = null
    private lateinit var listener: OnButtonPressedListener
    private lateinit var listenerTitulo : OnTitleSelectedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        datosDetailFromDB()
        return inflater.inflate(R.layout.detail_recipes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tituloRecDet = it.getString(tituloRecibido)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(tituloRecDet: String) =
                RecipesDetail().apply {
                    arguments = Bundle().apply {
                        putString(tituloRecibido, tituloRecDet)
                    }
                }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        //titulo_detail_receta.text = tituloRecDet

        ver_reviews_detrec.setOnClickListener{
            listenerTitulo.onTitleSelected(titulo_detail_receta.text.toString())
            listener.onButtonPressed(ver_reviews_detrec.tag.toString())
        }

        volver_detrec.setOnClickListener{
            listener.onButtonPressed("VolverAtras")
        }


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
        listenerTitulo = activity as OnTitleSelectedListener
    }

    fun datosDetailFromDB(){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        db.collection("Recetas")
                .whereEqualTo("titulo", tituloRecDet)
                .addSnapshotListener { values, _ ->
                    if (values != null) {
                        for (doc in values) {
                            if (doc.get("tipo") != null) {

                                val refRecetaId = db.document("/Recetas/${doc.id}")


                                //num favs
                                //si es fav
                                titulo_detail_receta.text = tituloRecDet
                                recipe_description.text = doc.getString("descripcion")

                                /*val fieldsAndValuesDB = HashMap<String, Any>()
                                fieldsAndValuesDB.put("esFav?", false)
                                fieldsAndValuesDB.put("id_receta", refRecetaId)
                                fieldsAndValuesDB.put("id_usuario", refUserId)
                                fieldsAndValuesDB.put("tipo", doc.getString("tipo")!!)
                                fieldsAndValuesDB.put("titulo", doc.getString("titulo")!!)
                                fieldsAndValuesDB.put("pais", doc.getString("pais")!!)*/


                            }
                        }
                    }
                }
    }

}
