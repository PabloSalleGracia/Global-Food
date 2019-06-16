package com.example.pablo.globalfood.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pablo.globalfood.Fragments.AddNewRecipe
import com.example.pablo.globalfood.Fragments.Search
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_new_recipe.*
import java.util.HashMap

private var tituloRecRes = "seEnviaTitulo"
private var tipoRecRes = "seEnviaTipo"

class AddNewRecipeActivity : AppCompatActivity(), OnButtonPressedListener{

    override fun onItemPressed(titulo: String, tipo: String, fav: String) {
        tituloRecRes = titulo
        tipoRecRes = tipo
        openDetailRecipes()
    }

    override fun onButtonPressed(text: String) {
        when(text){
            "SUBIR" -> goToLastScreen()
            "VOLVER" -> goToLastScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_recipe)

        val palabraBuscada = intent.getStringExtra("palabraBusqueda")
        val add = intent.getStringExtra("addRecipe")


        if(savedInstanceState == null){
            if(palabraBuscada != null){
                val search = Search()
                supportFragmentManager.beginTransaction().add(R.id.add_container, search).addToBackStack(null).commit()
            }
            if(add != null){
                openAddNewRecipe()
            }
        }

    }

    private fun openAddNewRecipe(){
        //addToBackStack hace al usuario volver a una pagina blanca si le da atras con el boton del movil (FrameLayou de la Activity)
        //pero sino falla la aplicacion, error pendiente de arreglar/mejorar
        val addFrag = AddNewRecipe()
        supportFragmentManager.beginTransaction().add(R.id.add_container, addFrag).addToBackStack(null).commit()
    }

    private fun goToLastScreen(){
        //vuelvo a abrir el intent porque si uso super.OnBackPressed() salta el mismo error comentado en la funcion openAddNewRecipe
        //pendiente de mejorar/arreglar tambien
        val menuAgain = Intent(this, MainMenuActivity::class.java)
        startActivity(menuAgain)
    }

    private fun openDetailRecipes(){
        val detailActivity = Intent(this, DetailActivity::class.java)
        detailActivity.putExtra("tituloRecRes", tituloRecRes)
        detailActivity.putExtra("tipoRecRes", tipoRecRes)
        //startActivityForResult(intent2, MainMenuActivity.REQUEST_CODE)
        startActivity(detailActivity)
    }

}
