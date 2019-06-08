package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pablo.globalfood.Model.Review
import com.example.pablo.globalfood.OnButtonPressedListener

import com.example.pablo.globalfood.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.detail.*
import kotlinx.android.synthetic.main.read_review.*

private const val tituloRecibido = "datosEnviados"
private const val tipoRecibido = "datosEnviados"

class ReadReview : Fragment() {

    private var tituloReadRev: String? = null
    private var tipoReadRev: String? = null
    private lateinit var listener : OnButtonPressedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        datosReadReviewFromDB()
        return inflater.inflate(R.layout.read_review, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tituloReadRev = it.getString(tituloRecibido)
            tipoReadRev = it.getString(tipoRecibido)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tituloReadRev: String, tipoReadRev: String) =
                ReadReview().apply {
                    arguments = Bundle().apply {
                        putString(tituloRecibido, tituloReadRev)
                        putString(tipoRecibido, tipoReadRev)
                    }
                }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        titulo_read_review.text = tituloReadRev

        volver_rreview.setOnClickListener{
            listener.onButtonPressed("Volver")
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
                                if (doc.get("titulo") != null) {
                                    doc.reference.collection("Reviews")
                                            .addSnapshotListener{ reviews, _ ->
                                                if (reviews != null) {
                                                    for (docRev in reviews) {
                                                        if (docRev.get("descripcion") != null) {
                                                             review.text = docRev.getString(("descripcion"))
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
                                if (doc.get("titulo") != null) {
                                    doc.reference.collection("Reviews")
                                            .addSnapshotListener{ reviews, _ ->
                                                if (reviews != null) {
                                                    for (docRev in reviews) {
                                                        if (docRev.get("descripcion") != null) {
                                                            review.text = docRev.getString(("descripcion"))
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
