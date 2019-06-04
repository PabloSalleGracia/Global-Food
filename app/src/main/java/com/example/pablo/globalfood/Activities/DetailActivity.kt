package com.example.pablo.globalfood.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pablo.globalfood.Fragments.RecipesDetail
import com.example.pablo.globalfood.R

class DetailActivity : AppCompatActivity() {

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
