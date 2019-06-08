package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pablo.globalfood.Model.FavRecipe
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import kotlinx.android.synthetic.main.fragment_register.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern
import java.util.*


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

    /*companion object {
        @JvmStatic
        fun newInstance(text: String = "") =
                Register().apply {
                    arguments = Bundle().apply {
                        putString(TEXT, text)
                    }
                }
    }*/


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

    /*private fun loadFields(extras: Bundle) {
        register_email.setText(extras.getString(LoginActivity.EMAIL_EXTRA))
        register_password.setText(extras.getString(LoginActivity.PASSWORD_EXTRA))
    }*/

    /*private fun getLoginBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(LoginActivity.EMAIL_EXTRA, register_email.text.toString())
        bundle.putString(LoginActivity.PASSWORD_EXTRA, register_password.text.toString())
        return bundle
    }*/

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
                        fieldsAndValuesDB.put("fecha_registrado", " ")
                        fieldsAndValuesDB.put("activo?", true)

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
                    emailR.error = "Este usuario ya existe, introduce otro email"
                }
    }

    fun obtenerRecetasparaInsertarEnUsuariosRecetas(){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        db.collection("Recetas")
                .addSnapshotListener { values, _ ->
                    if (values != null) {
                        for (doc in values) {
                            if (doc.get("tipo") != null) {
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

    fun crearReceta(){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid

        val fieldsAndValuesDB = HashMap<String, Any>()
        fieldsAndValuesDB.put("esFav?", false)
        fieldsAndValuesDB.put("id_receta", emailR.text.toString())
        fieldsAndValuesDB.put("id_usuario", emailR.text.toString())
        fieldsAndValuesDB.put("tipo", emailR.text.toString())
        fieldsAndValuesDB.put("titulo", emailR.text.toString())
        fieldsAndValuesDB.put("pais", emailR.text.toString())
        fieldsAndValuesDB.put("creador", user)

        //comprobar que ningun campo esta vacio o no dejar insertar nueva receta

        db.collection("Recetas").add(fieldsAndValuesDB)

        //ademas he de hacer insert en usuario recetas de la nueva receta a todos los users ya registrados
    }

    fun obtenerRestaurantesparaInsertarEnUsuariosRestaurantes(){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        db.collection("Restaurantes")
                .addSnapshotListener { values, _ ->
                    if (values != null) {
                        for (doc in values) {
                            if (doc.get("tipo") != null) {
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
