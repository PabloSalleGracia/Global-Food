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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.detail.*


private const val tituloRecibido = "datosRecibidos"
private const val tipoRecibido = "datosRecibidos"

class RecipesDetail : Fragment() {

    private var tituloRecDet: String? = null
    private var tipoRecRes: String? = null
    private lateinit var listener: OnButtonPressedListener
    private lateinit var listenerTitulo : OnTitleSelectedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        datosDetailFromDB()
        return inflater.inflate(R.layout.detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tituloRecDet = it.getString(tituloRecibido)
            tipoRecRes = it.getString(tipoRecibido)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(tituloRecDet: String, tipoRecRes: String) =
                RecipesDetail().apply {
                    arguments = Bundle().apply {
                        putString(tituloRecibido, tituloRecDet)
                        putString(tipoRecibido, tipoRecRes)
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

        if(tipoRecRes == "Plato"){
            db.collection("Recetas")
                    .whereEqualTo("titulo", tituloRecDet)
                    .addSnapshotListener { values, _ ->
                        if (values != null) {
                            for (doc in values) {
                                if (doc.get("tipo") != null) {
                                    titulo_detail_receta.text = tituloRecDet
                                    recipe_description.text = doc.getString("descripcion")

                                }
                            }
                        }
                    }
        }else{
            db.collection("Restaurantes")
                    .whereEqualTo("titulo", tituloRecDet)
                    .addSnapshotListener { values, _ ->
                        if (values != null) {
                            for (doc in values) {
                                if (doc.get("tipo") != null) {
                                    titulo_detail_receta.text = tituloRecDet
                                    recipe_description.text = doc.getString("descripcion")

                                }
                            }
                        }
                    }
        }

    }

}
