package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.regex.Pattern


class Login : Fragment() {

    private lateinit var listener: OnButtonPressedListener

    private var fieldsOk = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        login.setOnClickListener {
            fieldsOk = true
            checkEmail(email)
            checkPass(contrasena)
            if (fieldsOk) {
                logIn()
            }
        }

        register.setOnClickListener {
            listener.onButtonPressed(register.tag.toString())
        }
    }
    fun checkEmail(editText: EditText){
        val email = editText.text.toString()
        if (!Pattern.compile(".+\\@.+\\..+").matcher(email).matches()) {
            editText.error = getString(R.string.email_invalido)
            fieldsOk = false
        }
    }

    fun checkPass(editText: EditText){
        val password = contrasena.text.toString()
        if (password.isEmpty() || password.length < 8) {
            editText.error = getString(R.string.contrasena_error)
            fieldsOk = false
        }
    }

    fun logIn(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(), contrasena.text.toString())
                .addOnCompleteListener{
                    if(!it.isSuccessful){
                        return@addOnCompleteListener
                    }else{
                        Toast.makeText(this.context, getString(R.string.login_correcto), Toast.LENGTH_LONG).show()
                        listener.onButtonPressed(login.tag.toString())
                    }
                }
                .addOnFailureListener{
                    email.error = "El email o contraseña introducidos no son válidos"
                }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }


}
