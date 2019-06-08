package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pablo.globalfood.OnButtonPressedListener

import com.example.pablo.globalfood.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.write_review.*


private const val tituloRecibido = "datosEnviados"
private const val tipoRecibido = "datosEnviados"

class WriteReview : Fragment() {

    private var tituloWriteRev: String? = null
    private var tipoWriteRev: String? = null
    private lateinit var listener: OnButtonPressedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.write_review, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tituloWriteRev = it.getString(tituloRecibido)
            tipoWriteRev = it.getString(tipoRecibido)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(tituloWriteRev: String, tipoWriteRev: String) =
                WriteReview().apply {
                    arguments = Bundle().apply {
                        putString(tituloRecibido, tituloWriteRev)
                        putString(tituloRecibido, tipoWriteRev)
                    }
                }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //tituloRR.text = param1
        titulo_write_review.text = tituloWriteRev

        volver_wreview.setOnClickListener{
            listener.onButtonPressed("Volver")
        }

    }

    fun escribirReview(){
        val db = FirebaseFirestore.getInstance()

        val data = "hola"

        db.collection("Reviews").add(data)
                .addOnSuccessListener {
                    documentReference ->  Log.d("WriteReview", "bla bla falla? bla bla" + documentReference.id)
                }
                .addOnFailureListener{

                }
    }

}
