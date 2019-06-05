package com.example.pablo.globalfood.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pablo.globalfood.Fragments.RecipesDetail
import com.example.pablo.globalfood.Fragments.ReviewsList
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R

private var datosEnviados = "seEnvia"

class DetailActivity : AppCompatActivity(), OnButtonPressedListener {
    override fun onItemPressed(text: Any) {

        datosEnviados = text.toString()
    }

    override fun onButtonPressed(text: String) {
        when(text){
            "Ver Review" -> {
                val reviewList = ReviewsList()
                supportFragmentManager.beginTransaction().replace(R.id.detail_container, reviewList).commit()
            }
            /*"hola" -> {
                    val readReview = RecipesDetail.newInstance(recibido)
                    supportFragmentManager.beginTransaction().
                            add(R.id.detail_container, recipesDetail).
                            commit()
            }*/
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
