package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.example.pablo.globalfood.Adapters.ListFavRecipesAdapter
import com.example.pablo.globalfood.Model.FavRecipe
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.favorite_recipes.*


class FavRecipes : Fragment(){

    private lateinit var listener: OnButtonPressedListener
    private val datosFavRecipes = ArrayList<FavRecipe>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorite_recipes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fireBaseSelectFavRecipes()

        search_view_fav_recipes.setOnClickListener{
            listener.onButtonPressed("SEARCH")
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

    private fun fireBaseSelectFavRecipes(){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        db.collection("Usuario-Recetas")
                .whereEqualTo("id_usuario", refUserId)
                .whereEqualTo("esFav?", true)
                .addSnapshotListener { values, _ ->
                    if (values != null) {
                        for (dc in values.documentChanges) {
                            when (dc.type) {
                                DocumentChange.Type.MODIFIED -> {
                                    println("MODIFIED")
                                }
                                DocumentChange.Type.ADDED -> {
                                        datosFavRecipes.add(FavRecipe(dc.document.data["titulo"].toString(), dc.document.data["pais"].toString(),
                                                dc.document.data["tipo"].toString(), dc.document.data["esFav?"] as Boolean))
                                }
                                DocumentChange.Type.REMOVED -> {
                                    datosFavRecipes.remove(FavRecipe(dc.document.data["titulo"].toString(), dc.document.data["pais"].toString(),
                                            dc.document.data["tipo"].toString(), dc.document.data["esFav?"] as Boolean))
                                }
                            }
                        }
                        fillListFavRecipes()
                    }
                }
    }

    private fun fillListFavRecipes(){
        if(view != null){
            val listFavRecipes: ListView = view!!.findViewById(R.id.list_fav_recipes)

            val favRecipeAdapter = ListFavRecipesAdapter(context!!, datosFavRecipes )
            listFavRecipes.adapter = favRecipeAdapter


            listFavRecipes.onItemClickListener = (AdapterView.OnItemClickListener { _, _, position, _ ->
                listener.onItemPressed(favRecipeAdapter.dataSource[position].title, favRecipeAdapter.dataSource[position].resDish, favRecipeAdapter.dataSource[position].esFav.toString())
            })
        }

    }


}
