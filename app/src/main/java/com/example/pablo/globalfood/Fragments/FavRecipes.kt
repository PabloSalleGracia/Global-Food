package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.example.pablo.globalfood.Model.FavRecipe
import com.example.pablo.globalfood.Adapters.ListFavRecipesAdapter
import com.example.pablo.globalfood.Model.MyRecipe
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.favorite_recipes.*
import kotlinx.android.synthetic.main.my_recipes.*


class FavRecipes : Fragment() {

    private lateinit var listener: OnButtonPressedListener
    val datosFavRecipes = ArrayList<FavRecipe>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fireBaseSelectFavRecipes()
        return inflater.inflate(R.layout.favorite_recipes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
                        for (doc in values) {
                            if (doc.getString("tipo") != null) {
                                datosFavRecipes.add(FavRecipe(doc.getString("titulo")!!, doc.getString("pais")!!,
                                        doc.getString("tipo")!!, doc.getBoolean("esFav?")!!))
                                fillListFavRecipes()
                            }
                        }
                    }
                    Log.d("MainMenuActivity", "Current cites in CA: $datosFavRecipes")
                }
    }

    private fun fillListFavRecipes(){
        val listFavRecipes: ListView = view!!.findViewById(R.id.list_fav_recipes)

        val favRecipeAdapter = ListFavRecipesAdapter(context!!, datosFavRecipes )
        listFavRecipes.adapter = favRecipeAdapter

        listFavRecipes.onItemClickListener = (AdapterView.OnItemClickListener { _, _, position, _ ->
            listener.onItemPressed(favRecipeAdapter.dataSource[position].title, favRecipeAdapter.dataSource[position].resDish)
            listener.onButtonPressed(list_fav_recipes.tag.toString())
        })
    }


}
