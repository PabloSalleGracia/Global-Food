package com.example.pablo.globalfood

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var emailValid = false
        var passValid = false
        var fechaValid = false
        var fechaNacimiento: String
        var añoActual = 1
        var año = 1
        val comprobarEmail = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        login.setOnClickListener(){

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
                passValid = true
            }

            if(emailValid && passValid){
                textVisual.text = email.text
                val success = "Logeado correctamente"
                val duration = Toast.LENGTH_LONG

                val toast = Toast.makeText(applicationContext, success, duration)
                toast.show()
            }



        }

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

            if(añoActual - año >= 18){
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

        fecha.setOnClickListener({ v ->
            val c = Calendar.getInstance()
            val actualYear = c.get(Calendar.YEAR)
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
                fechaNacimiento = "$dayOfMonth/$monthOfYear/$year"
                fecha.text = fechaNacimiento

                añoActual = actualYear
                año = year


            },year , month, day)
            dpd.show()



        })


    }
}
