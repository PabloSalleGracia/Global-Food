package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pablo.globalfood.Model.Review
import com.example.pablo.globalfood.OnButtonPressedListener

import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.write_review.*


private const val tituloRecibido = "titulo"
private const val tipoRecibido = "tipo"

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
                        putString(tipoRecibido, tipoWriteRev)
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
            listener.onButtonPressed("VolverAtras")
        }

        boton_escribir_review.setOnClickListener{
            escribirReview()
        }

    }

    fun escribirReview(){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid

        if(nombre_autor_write_review.text.isEmpty() || descripBreveWrite.text.isEmpty() || review.text.isEmpty()){
            nombre_autor_write_review.error = "Introduce tu nombre para subir tu review"
            descripBreveWrite.error = "Introduce una descripcíon breve para subir tu review"
            review.error = "Este campo no puede estar vacío, introduce tu opinión"
        }else {

            if (tipoWriteRev == "Plato") {
                db.collection("Recetas")
                        .whereEqualTo("titulo", tituloWriteRev)
                        .addSnapshotListener { values, _ ->
                            if (values != null) {
                                for (doc in values) {
                                    if (doc.getString("titulo") != null) {
                                        val fieldsAndValuesDB = HashMap<String, Any>()
                                        fieldsAndValuesDB.put("titulo", doc.getString("titulo")!!)
                                        fieldsAndValuesDB.put("autor", nombre_autor_write_review.text.toString())
                                        fieldsAndValuesDB.put("descripBreve", descripBreveWrite.text.toString())
                                        fieldsAndValuesDB.put("descripcion", review.text.toString())
                                        fieldsAndValuesDB.put("pais", doc.getString("pais")!!)
                                        fieldsAndValuesDB.put("creador", user)


                                        db.collection("Recetas").document(doc.id).collection("Reviews").add(fieldsAndValuesDB)
                                                .addOnSuccessListener {
                                                    Toast.makeText(this.context, ("Se ha subido tu review correctamente"), Toast.LENGTH_LONG).show()
                                                    listener.onButtonPressed("COMENTAR")

                                                }
                                    }
                                }
                            }
                        }
            } else {
                db.collection("Restaurantes")
                        .whereEqualTo("titulo", tituloWriteRev)
                        .addSnapshotListener { values, _ ->
                            if (values != null) {
                                for (doc in values) {
                                    if (doc.getString("titulo") != null) {
                                        val fieldsAndValuesDB = HashMap<String, Any>()
                                        fieldsAndValuesDB.put("titulo", doc.getString("titulo")!!)
                                        fieldsAndValuesDB.put("autor", nombre_autor_write_review.text.toString())
                                        fieldsAndValuesDB.put("descripBreve", descripBreveWrite.text.toString())
                                        fieldsAndValuesDB.put("descripcion", review.text.toString())
                                        fieldsAndValuesDB.put("pais", doc.getString("pais")!!)
                                        fieldsAndValuesDB.put("creador", user)


                                        db.collection("Restaurantes").document(doc.id).collection("Reviews").add(fieldsAndValuesDB)
                                                .addOnSuccessListener {
                                                    Toast.makeText(this.context, ("Se ha subido tu review correctamente"), Toast.LENGTH_LONG).show()
                                                    listener.onButtonPressed("COMENTAR")

                                                }
                                    }
                                }
                            }
                        }
            }
        }
    }

}
