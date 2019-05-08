package com.example.pablo.globalfood


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*
import java.util.regex.Pattern


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LoginFragment : Fragment() {

    private lateinit var listener: OnButtonPressedListener

    var fieldsOk = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        login.setOnClickListener {
            listener.onButtonPressed(login.text.toString())
            fieldsOk = true
            checkEmail(email)
            checkPass(contrasena)
            if (fieldsOk) {
                Toast.makeText(this.context, getString(R.string.login_correcto), Toast.LENGTH_LONG).show()
            }
        }

        register.setOnClickListener {
            listener.onButtonPressed(register.text.toString())
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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }


}
