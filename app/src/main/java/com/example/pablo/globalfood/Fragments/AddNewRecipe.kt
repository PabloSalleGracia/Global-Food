package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import com.example.pablo.globalfood.OnButtonPressedListener

import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.add_new_recipe.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.HashMap


class AddNewRecipe : Fragment() {

    private lateinit var listener : OnButtonPressedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_new_recipe, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        volverAddRec.setOnClickListener{
            listener.onButtonPressed(("VOLVER"))
        }

        subirAddRec.setOnClickListener{
            uploadNewRecipe()
        }


    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

    fun uploadNewRecipe(){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid

        if(nombreReceta.text.isEmpty() || paisReceta.text.isEmpty() || descripReceta.text.isEmpty()){
            nombreReceta.error = "Algunos de los campos esta vacio, no se ha podido subir la receta por ese motivo"
        }else{
            val fieldsAndValuesDB = HashMap<String, Any>()
            fieldsAndValuesDB.put("numFavs", 0)
            fieldsAndValuesDB.put("tipo", "Plato")
            fieldsAndValuesDB.put("titulo", nombreReceta.text.toString())
            fieldsAndValuesDB.put("pais", paisReceta.text.toString())
            fieldsAndValuesDB.put("descripcion", descripReceta.text.toString())
            fieldsAndValuesDB.put("creador", user)


            db.collection("Recetas").add(fieldsAndValuesDB).addOnSuccessListener {

            db.collection("Recetas")
                    .whereEqualTo("titulo", nombreReceta.text.toString())
                .addSnapshotListener { values, _ ->
                    if (values != null) {
                        for (doc in values) {
                            if (doc.get("titulo") != null) {
                                db.collection("Usuarios")
                                        .addSnapshotListener{ values2, _->
                                            if (values2 != null){
                                                for (docUsers in values2){
                                                    if(docUsers.getBoolean("activo?")== true){
                                                        val refRecetaId = db.document("/Recetas/${doc.id}")
                                                        val refUserId = db.document("/Usuarios/${docUsers.id}")

                                                        val fieldsAndValuesDBUR = HashMap<String, Any>()
                                                        fieldsAndValuesDBUR.put("esFav?", false)
                                                        fieldsAndValuesDBUR.put("tipo", "Plato")
                                                        fieldsAndValuesDBUR.put("titulo", nombreReceta.text.toString())
                                                        fieldsAndValuesDBUR.put("pais", paisReceta.text.toString())
                                                        fieldsAndValuesDBUR.put("creador", user)
                                                        fieldsAndValuesDBUR.put("id_receta", refRecetaId)
                                                        fieldsAndValuesDBUR.put("id_usuario", refUserId)

                                                        db.collection("Usuario-Recetas").add(fieldsAndValuesDBUR).addOnSuccessListener {
                                                            Toast.makeText(this.context, ("Se ha subido tu receta correctamente"), Toast.LENGTH_LONG).show()
                                                            listener.onButtonPressed("SUBIR")

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
    }
}
