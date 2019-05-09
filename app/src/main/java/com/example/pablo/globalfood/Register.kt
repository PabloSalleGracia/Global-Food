package com.example.pablo.globalfood


import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val TEXT = "text"

/**
 * A simple [Fragment] subclass.
 *
 */
class Register : Fragment() {


    private lateinit var listener: OnButtonPressedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        registrarse.setOnClickListener {
            Toast.makeText(this.context, getString(R.string.register_correcto), Toast.LENGTH_LONG).show()
            listener.onButtonPressed(registrarse.tag.toString())
        }

        haveAcc.setOnClickListener{
            listener.onButtonPressed(haveAcc.tag.toString())
        }

            /*
        register.setOnClickListener(){

            if (email.text.contains("@")) {
                //email.setText("")
                emailValid = true

            }else {
                email.error = "No es un formato válido de email"
                emailValid = false
            }

            if (contrasena.text.length < 8) {
                contrasena.error = "La contraseña debe tener almenos 8 cáracteres"
                //contrasena.setText("")
                passValid = false

            }else{
                if (confirmacionContrasena.text.toString().equals(this.contrasena.text.toString())) {
                    //email.setText("")
                    passValid = true


                }else {
                    confirmacionContrasena.error = "Las contraseñas no coinciden"
                    passValid = false
                }

            }

            if(anoActual - ano >= 18){
                fechaValid = true
            }else{

                fecha.error="No puedes registrarte si eres menor de edad"
                fecha.requestFocus()
                fechaValid = false
            }

            if(emailValid && passValid && fechaValid){
                textVisual.text = email.text
                val registered = "Te has registrado correctamente"
                val duration = Toast.LENGTH_LONG

                val toast2 = Toast.makeText(applicationContext, registered, duration)
                toast2.show()
            }


        }
         */

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

    /*fun clickFecha(){
        fecha.setOnClickListener { _ ->
            val c = Calendar.getInstance()
            val actualYear = c.get(Calendar.YEAR)
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ _, year, monthOfYear, dayOfMonth ->
                fechaNacimiento = "$dayOfMonth/$monthOfYear/$year"
                fecha.text = fechaNacimiento

                anoActual = actualYear
                ano = year


            },year , month, day)
            dpd.show()
        }
    }*/


}
