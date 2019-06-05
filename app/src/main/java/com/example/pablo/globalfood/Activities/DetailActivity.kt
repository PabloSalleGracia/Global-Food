package com.example.pablo.globalfood.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pablo.globalfood.Fragments.ReadReview
import com.example.pablo.globalfood.Fragments.RecipesDetail
import com.example.pablo.globalfood.Fragments.ReviewsList
import com.example.pablo.globalfood.Fragments.WriteReview
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.OnTitleSelectedListener
import com.example.pablo.globalfood.R

private var datosEnviados = "seEnvia"
private var tituloEnviado = "ensladnas"

class DetailActivity : AppCompatActivity(), OnButtonPressedListener, OnTitleSelectedListener {

    override fun onTitleSelected(text: String) {
       tituloEnviado = text
    }

    override fun onItemPressed(text: Any) {

        datosEnviados = text.toString()

       // when(datosEnviados){
       //     "hola" -> {
                val readReview = ReadReview.newInstance(datosEnviados)
                supportFragmentManager.beginTransaction().
                        replace(R.id.detail_container, readReview).
                        commit()

       // }
    }

    override fun onButtonPressed(text: String) {
        when(text){
            "Ver Review" -> {
                val reviewList = ReviewsList()
                supportFragmentManager.beginTransaction().replace(R.id.detail_container, reviewList).commit()
            }
            "Escribir review" -> {
                val writeReview = WriteReview.newInstance(tituloEnviado)
                supportFragmentManager.beginTransaction().replace(R.id.detail_container, writeReview).commit()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val recibido = intent.getStringExtra("id")


        if(savedInstanceState == null){
            val recipesDetail = RecipesDetail.newInstance(recibido)
            supportFragmentManager.beginTransaction().
                    add(R.id.detail_container, recipesDetail).
                    commit()
        }
    }


}
