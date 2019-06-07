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
import com.example.pablo.globalfood.Adapters.ListMyRecipesAdapter
import com.example.pablo.globalfood.Model.MyRecipe
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.my_recipes.*



class MyRecipes : Fragment() {

    private lateinit var listener: OnButtonPressedListener
    val datosMyRecipes = ArrayList<MyRecipe>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fireBaseSelectMyRecipes()
        return inflater.inflate(R.layout.my_recipes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            /* BORRAR FILAS
            datos2.removeAt(position)
            myReci.notifyDataSetChanged()*/

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

    fun fireBaseSelectMyRecipes(){
        /*val db = FirebaseFirestore.getInstance()
        //id del usuario logeado en firebase
        val user = FirebaseAuth.getInstance().currentUser!!.uid


        val esFavRef = db.collection("Usuario-Recetas")
                .whereEqualTo("id_usuario", user)
                .whereEqualTo("id_receta", "kdns") //receta value sea la seleccionada, hay que hacer for para mirar todas
                                                            //usuario value sea el logeado, hay que hacer for de todos los usuarios
                    //devuelveme el esFav

        db.collection("Recetas")
                .whereEqualTo("tipo", "Plato")
                .whereEqualTo("pais", esFavRef)
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(values: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                        if (p1 != null) {
                            Log.w("MainMenuActivity", "Listen failed.", p1)
                            return
                        }

                        if (values != null) {
                            for (doc in values) {
                                if (doc.get("tipo") != null) {
                                    datosMyRecipes.add(MyRecipe(doc.getString("titulo")!!, doc.getString("pais")!!,
                                            doc.getString("tipo")!!, doc.getLong("numFavs")!!))
                                    fillListMyRecipes()
                                }
                            }
                        }
                        Log.d("MainMenuActivity", "Current cites in CA: $datosMyRecipes")
                    }
                })*/
    }

    fun fillListMyRecipes(){
        val listMyRecipes: ListView = view!!.findViewById(R.id.list_my_recipes)

        val myRecipeAdapter = ListMyRecipesAdapter(context!!, datosMyRecipes)
        listMyRecipes.adapter = myRecipeAdapter

        listMyRecipes.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
            listener.onItemPressed(myRecipeAdapter.dataSource[position].title)
        }
    }


}
