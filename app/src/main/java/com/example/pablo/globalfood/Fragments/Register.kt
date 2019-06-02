package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import kotlinx.android.synthetic.main.fragment_register.*
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val TEXT = "text"

/**
 * A simple [Fragment] subclass.
 *
 */
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
                Toast.makeText(this.context, getString(R.string.register_correcto), Toast.LENGTH_LONG).show()
                listener.onButtonPressed(registrarse.tag.toString())
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
        if (confirmacionContrasena.text.toString() != contrasenaR.text.toString()) {
            confirmacionContrasena.error = getString(R.string.confirma_contra_incorrecta)
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
                        println("se ha registrao")
                    }
                }
                .addOnFailureListener{
                    println("error")
                }
    }



}
