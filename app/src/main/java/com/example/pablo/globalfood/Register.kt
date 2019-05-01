package com.example.pablo.globalfood


import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 *
 */
class Register : Fragment() {


    interface OnButtonPressedListener {
        fun onButtonPressed(text: String)
    }

    private lateinit var listener: OnButtonPressedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*register.setOnClickListener {
            listener.onButtonPressed(email.text.toString())
        }*/
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

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
