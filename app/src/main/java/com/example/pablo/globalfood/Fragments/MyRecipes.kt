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
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.my_recipes.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */


class MyRecipes : Fragment() {

    private lateinit var listener: OnButtonPressedListener
    val datosMyRecipes = ArrayList<MyRecipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pruebaFireBaseSelect()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_recipes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val listMyRecipes: ListView = view!!.findViewById(R.id.list_my_recipes)

        /*datosMyRecipes.add(MyRecipe("My", "Recipes", "prueba", 1))
        datosMyRecipes.add(MyRecipe("titulo", "checa", "plato", 0))
        datosMyRecipes.add(MyRecipe("titulo", "india", "plato", 1))
        datosMyRecipes.add(MyRecipe("titulo", "mexicana", "plato", 0))
        datosMyRecipes.add(MyRecipe("titulo", "india", "plato", 1))
        datosMyRecipes.add(MyRecipe("titulo", "india", "plato", 1))
        datosMyRecipes.add(MyRecipe("titulo", "australiana", "plato", 0))*/



        val myRecipeAdapter = ListMyRecipesAdapter(context!!, datosMyRecipes)
        listMyRecipes.adapter = myRecipeAdapter


        listMyRecipes.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
            listener.onItemPressed(myRecipeAdapter.dataSource[position].title)
        }

        myRecipeAdapter.notifyDataSetChanged()
        /*listMyRecipes.onItemClickListener = (AdapterView.OnItemClickListener { _, _, position, _ ->

            //como pasar datos como con el extra, y como abrir nuevo fragment pasandole esos datos seleccionados
            //se pasan con el bundle?

            //var hola = "hola"
            listener.onItemPressed(myRecipeAdapter.dataSource[position].title)
            listener.onButtonPressed(list_my_recipes.tag.toString())

            /* BORRAR FILAS
            datos2.removeAt(position)
            myReci.notifyDataSetChanged()*/

        })*/

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

    fun pruebaFireBaseSelect(){
        val db = FirebaseFirestore.getInstance()

        db.collection("Recetas")
                .whereEqualTo("titulo", "TituloReceta")
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                        if (p1 != null) {
                            Log.w("MainMenuActivity", "Listen failed.", p1)
                            return
                        }

                        if (p0 != null) {
                            for (doc in p0) {
                                if (doc.get("titulo") != null) {

                                    datosMyRecipes.add(MyRecipe(doc.getString("titulo")!!, doc.getString("pais")!!,
                                            doc.getString("tipo")!!, doc.getLong("numFavs")!!))

                                }
                            }
                        }
                        Log.d("MainMenuActivity", "Current cites in CA: $datosMyRecipes")
                    }
                })

    }


}
