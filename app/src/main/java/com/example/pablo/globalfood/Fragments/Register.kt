package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*
import java.util.regex.Pattern


class Register : Fragment() {

    private lateinit var listener: OnButtonPressedListener
    private var fieldsOk = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        registrarse.setOnClickListener {
            fieldsOk = true
            checkFields()

            if (fieldsOk) {
                firebaseRegister()
            }
        }

        haveAcc.setOnClickListener{
            listener.onButtonPressed(haveAcc.tag.toString())
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }


    private fun checkFields() {
        checkEmail()
        checkPassword()
        checkRepeatPassword()
    }

    private fun checkEmail() {
        val email = emailR.text.toString()
        if (!Pattern.compile(".+\\@.+\\..+").matcher(email).matches()) {
            emailR.error = getString(R.string.email_invalido)
            fieldsOk = false
        }
    }

    private fun checkPassword() {
        val password = contrasenaR.text.toString()
        if (password.isEmpty() || password.length < 8) {
            contrasenaR.error = getString(R.string.contrasena_error)
            fieldsOk = false
        }
    }

    private fun checkRepeatPassword() {
        if (confirmacion_contrasena.text.toString() != contrasenaR.text.toString()) {
            confirmacion_contrasena.error = getString(R.string.confirma_contra_incorrecta)
            fieldsOk = false
        }
    }

    private fun firebaseRegister(){
        val email = emailR.text.toString()
        val password = contrasenaR.text.toString()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    if(!it.isSuccessful){
                        return@addOnCompleteListener
                    }else{
                        val db = FirebaseFirestore.getInstance()
                        val newUserForDB = FirebaseAuth.getInstance().currentUser!!.uid
                        val fieldsAndValuesDB = HashMap<String, Any>()
                        fieldsAndValuesDB["fecha_registrado"] = " "
                        fieldsAndValuesDB["activo?"] = true

                        db.collection("Usuarios").document(newUserForDB)
                                .set(fieldsAndValuesDB)
                                .addOnSuccessListener {
                                    Log.d("Register", "se ha registrado en firestore")
                                    //hacer inserts en Usuarios-RecRes para setear esFav del nuevo user
                                    obtenerRecetasparaInsertarEnUsuariosRecetas()
                                    obtenerRestaurantesparaInsertarEnUsuariosRestaurantes()

                                    Toast.makeText(this.context, getString(R.string.register_correcto), Toast.LENGTH_LONG).show()
                                    listener.onButtonPressed(registrarse.tag.toString())
                                }
                                .addOnFailureListener{

                                }
                    }
                }
                .addOnFailureListener{
                    emailR.error = getString(R.string.error_user_already_exists)
                }
    }

    private fun obtenerRecetasparaInsertarEnUsuariosRecetas(){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        db.collection("Recetas")
                .addSnapshotListener { values, _ ->
                    if (values != null) {
                        for (doc in values) {
                            if (doc.getString("tipo") != null) {
                                val refRecetaId = db.document("/Recetas/${doc.id}")

                                val fieldsAndValuesDB = HashMap<String, Any>()
                                fieldsAndValuesDB.put("esFav?", false)
                                fieldsAndValuesDB.put("id_receta", refRecetaId)
                                fieldsAndValuesDB.put("id_usuario", refUserId)
                                fieldsAndValuesDB.put("tipo", doc.getString("tipo")!!)
                                fieldsAndValuesDB.put("titulo", doc.getString("titulo")!!)
                                fieldsAndValuesDB.put("pais", doc.getString("pais")!!)
                                fieldsAndValuesDB.put("creador", doc.getString("creador")!!)

                                db.collection("Usuario-Recetas").add(fieldsAndValuesDB)
                            }
                        }
                    }
                }
    }


    private fun obtenerRestaurantesparaInsertarEnUsuariosRestaurantes(){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        db.collection("Restaurantes")
                .addSnapshotListener { values, _ ->
                    if (values != null) {
                        for (doc in values) {
                            if (doc.getString("tipo") != null) {
                                val refRestauranteId = db.document("/Restaurantes/${doc.id}")

                                val fieldsAndValuesDB = HashMap<String, Any>()
                                fieldsAndValuesDB.put("esFav?", false)
                                fieldsAndValuesDB.put("id_restaurante", refRestauranteId)
                                fieldsAndValuesDB.put("id_usuario", refUserId)
                                fieldsAndValuesDB.put("tipo", doc.getString("tipo")!!)
                                fieldsAndValuesDB.put("titulo", doc.getString("titulo")!!)
                                fieldsAndValuesDB.put("pais", doc.getString("pais")!!)


                                db.collection("Usuario-Restaurantes").add(fieldsAndValuesDB)
                            }
                        }
                    }
                }
    }



}
