package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pablo.globalfood.OnButtonPressedListener

import com.example.pablo.globalfood.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.read_review.*

private const val tituloRecibido = "titulo"
private const val tipoRecibido = "tipo"
private const val autorRecibido = "autor"

class ReadReview : Fragment() {

    private var tituloReadRev: String? = null
    private var tipoReadRev: String? = null
    private var autorReadRev: String? = null
    private lateinit var listener : OnButtonPressedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.read_review, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tituloReadRev = it.getString(tituloRecibido)
            tipoReadRev = it.getString(tipoRecibido)
            autorReadRev = it.getString(autorRecibido)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tituloReadRev: String, tipoReadRev: String, autorReadRev: String) =
                ReadReview().apply {
                    arguments = Bundle().apply {
                        putString(tituloRecibido, tituloReadRev)
                        putString(tipoRecibido, tipoReadRev)
                        putString(autorRecibido, autorReadRev)
                    }
                }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        datosReadReviewFromDB()

        titulo_read_review.text = tituloReadRev
        nombre_autor_read_review.text = autorReadRev

        volver_rreview.setOnClickListener{
            listener.onButtonPressed("VolverAtras")
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

    fun datosReadReviewFromDB(){
        val db = FirebaseFirestore.getInstance()

        if(tipoReadRev == "Plato"){
            db.collection("Recetas")
                    .whereEqualTo("titulo", tituloReadRev)
                    .addSnapshotListener { values, _ ->
                        if (values != null) {
                            for (doc in values) {
                                if (doc.getString("titulo") != null) {
                                    doc.reference.collection("Reviews")
                                            .whereEqualTo("autor", autorReadRev)
                                            .addSnapshotListener{ reviews, _ ->
                                                if (reviews != null) {
                                                    for (docRev in reviews) {
                                                        if (docRev.get("descripcion") != null) {
                                                            //titulo_read_review.text = doc.getString("titulo")
                                                            //nombre_autor_read_review.text = docRev.getString("autor")
                                                            descripBreveReadR.text = docRev.getString("descripBreve")
                                                            review.text = docRev.getString("descripcion")
                                                        }
                                                    }
                                                }
                                            }
                                }
                            }
                        }
                    }
        }else{
            db.collection("Restaurantes")
                    .whereEqualTo("titulo", tituloReadRev)
                    .addSnapshotListener { values, _ ->
                        if (values != null) {
                            for (doc in values) {
                                if (doc.getString("titulo") != null) {
                                    doc.reference.collection("Reviews")
                                            .whereEqualTo("autor", autorReadRev)
                                            .addSnapshotListener{ reviews, _ ->
                                                if (reviews != null) {
                                                    for (docRev in reviews) {
                                                        if (docRev.get("descripcion") != null) {
                                                            //titulo_read_review.text = doc.getString("titulo")
                                                            //nombre_autor_read_review.text = docRev.getString("autor")
                                                            descripBreveReadR.text = docRev.getString("descripBreve")
                                                            review.text = docRev.getString("descripcion")
                                                        }
                                                    }
                                                }
                                            }
                                }
                            }
                        }
                    }
        }

    }


}
