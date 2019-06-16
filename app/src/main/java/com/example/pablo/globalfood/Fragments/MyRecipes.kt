package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.example.pablo.globalfood.Adapters.ListMyRecipesAdapter
import com.example.pablo.globalfood.Model.MyRecipe
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.my_recipes.*

private const val SEARCH = "SEARCH"

class MyRecipes : Fragment() {

    private lateinit var listener: OnButtonPressedListener
    private val datosMyRecipes = ArrayList<MyRecipe>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_recipes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fireBaseSelectMyRecipes()

        search_view_my_recipes.setOnClickListener{
            listener.onButtonPressed(SEARCH)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

    private fun fireBaseSelectMyRecipes(){
        val db = FirebaseFirestore.getInstance()
        //id del usuario logeado en firebase
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        //val prueba = HashMap<String, String>()
        val refUserId = db.document("/Usuarios/$user")


        //consulto una sola tabla con todos los datos aunque esten repetidos
        //denormalization data -> añades datos redudantes/repetidos/ para acceder a ellos mas facil y hacer querys mas eficientes
        db.collection("Usuario-Recetas")
                .whereEqualTo("id_usuario", refUserId)
                .whereEqualTo("creador", user)
                .addSnapshotListener { values, _ ->
                    if(values != null){
                        for(doc in values){
                            if (doc.getString("tipo") != null) {
                                /*aunque este en otra pantalla, si esta lista cambia
                                0 esta se actualiza, y si se actualiza y da null
                                por falta o exceso de una peta, y sino peta se duplica porque obtiene los nuevos datos
                                de la base de datos*/
                                if(datosMyRecipes.size < values.size()){
                                    datosMyRecipes.add(MyRecipe(doc.getString("titulo")!!, doc.getString("pais")!!,
                                            doc.getString("tipo")!!, doc.getBoolean("esFav?")!!))
                                }else{
                                    datosMyRecipes.clear()
                                    datosMyRecipes.add(MyRecipe(doc.getString("titulo")!!, doc.getString("pais")!!,
                                            doc.getString("tipo")!!, doc.getBoolean("esFav?")!!))
                                }

                                fillListMyRecipes()
                            }

                        }
                    }
                }

        //Consulto las dos tablas y seteo valores de cada una
        /*db.collection("Recetas")
                .addSnapshotListener(EventListener<QuerySnapshot> { values, p1 ->
                    if (p1 != null) {
                        Log.w("MainMenuActivity", "Listen failed.", p1)
                        return@EventListener
                    }
                    if (values != null) {
                        for (doc in values) {
                            if (doc.get("titulo") != null){
                                val refRecetaId = db.document("/Recetas/${doc.id}")


                                db.collection("Usuario-Recetas")
                                        .whereEqualTo("id_usuario", refUserId)
                                        //.whereEqualTo("id_receta", db.document("/Recetas/"+refRecetaId))
                                        .addSnapshotListener(EventListener<QuerySnapshot> {values2, p2 ->

                                            if (p2 != null) {
                                                Log.w("MainMenuActivity", "Listen failed.", p2)
                                                return@EventListener
                                            }
                                            if (values2 != null) {


                                                for(doc2 in values2){
                                                    if(doc2.get("titulo")!=null){

                                                        datosMyRecipes.add(MyRecipe(doc.getString("titulo")!!, doc.getString("pais")!!,
                                                                doc.getString("tipo")!!, doc2.getBoolean("esFav?")!!))
                                                        fillListMyRecipes()
                                                        Log.d("MainMenuActivity", "Current cites in CA: $datosMyRecipes")
                                                    }

                                                }
                                            }
                                        })

                            }
                        }
                    }
                    //Log.d("MainMenuActivity", "Current cites in CA: $datosMyRecipes")
                })*/

    }

    private fun fillListMyRecipes(){
        if(view != null){
            val listMyRecipes: ListView = view!!.findViewById(R.id.list_my_recipes)

            val myRecipeAdapter = ListMyRecipesAdapter(context!!, datosMyRecipes)
            listMyRecipes.adapter = myRecipeAdapter

            listMyRecipes.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
                listener.onItemPressed(myRecipeAdapter.dataSource[position].title, myRecipeAdapter.dataSource[position].resDish, myRecipeAdapter.dataSource[position].esFav.toString() )
            }
        }

    }


}
