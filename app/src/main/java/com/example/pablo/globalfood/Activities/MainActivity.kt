package com.example.pablo.globalfood.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.pablo.globalfood.Fragments.Login
import com.example.pablo.globalfood.Fragments.Register
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private const val REGISTER = "Register"
private const val LOGIN = "Login"
private const val SIGNUP = "Registrarse"
private const val HAVEACC = "TengoCuenta"


class MainActivity : AppCompatActivity(), OnButtonPressedListener {

    override fun onItemPressed(titulo: String, tipo: String, fav: String) {
        //Not used here
    }

    override fun onButtonPressed(text: String) {
        when (text) {
            REGISTER -> openRegister()
            LOGIN -> openMenuApp()
            SIGNUP -> openLogin()
            HAVEACC -> openLogin()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            openLogin()
        }
    }

    private fun openRegister(){
        val register = Register()
        supportFragmentManager.beginTransaction().replace(R.id.main_container, register).addToBackStack(null).commit()
    }


    private fun openLogin(){
        val login = Login()
        supportFragmentManager.beginTransaction().replace(R.id.main_container, login).commit()
    }

    private fun openMenuApp(){
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }


}
