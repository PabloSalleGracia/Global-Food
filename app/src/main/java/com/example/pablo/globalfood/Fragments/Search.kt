package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SearchView
import com.example.pablo.globalfood.Adapters.ListMyRecipesAdapter
import com.example.pablo.globalfood.Model.MyRecipe
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.favorite_restaurants.*
import kotlinx.android.synthetic.main.search.*


private const val palabra = "palabra"


class Search : Fragment() {

    private lateinit var listener : OnButtonPressedListener
    private var busqueda: String? = null
    val datosMyRecipes = ArrayList<MyRecipe>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            busqueda = it.getString(palabra)

        }
    }
    companion object {
        @JvmStatic
        fun newInstance(palabraBusqueda: String) =
                Search().apply {
                    arguments = Bundle().apply {
                        putString(palabra, palabraBusqueda)
                    }
                }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        busquedaFireBase()

        volver_buscar.setOnClickListener{
            listener.onButtonPressed("VOLVER")
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

    private fun busquedaFireBase(){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        db.collection("Usuario-Recetas")
                .whereEqualTo("id_usuario", refUserId)
                .addSnapshotListener { values, _ ->
                    if (values != null) {
                        for (doc in values) {
                            if (doc.getString("titulo") != null) {
                                datosMyRecipes.add(MyRecipe(doc.getString("titulo")!!, doc.getString("pais")!!,
                                        doc.getString("tipo")!!, doc.getBoolean("esFav?")!!))
                                fillLisResults()
                            }
                        }
                    }
                }
        db.collection("Usuario-Restaurantes")
                .whereEqualTo("id_usuario", refUserId)
                .addSnapshotListener { values, _ ->
                    if (values != null) {
                        for (doc in values) {
                            if (doc.getString("titulo") != null) {
                                datosMyRecipes.add(MyRecipe(doc.getString("titulo")!!, doc.getString("pais")!!,
                                        doc.getString("tipo")!!, doc.getBoolean("esFav?")!!))
                                fillLisResults()
                            }
                        }
                    }
                }


    }

    private fun fillLisResults(){
        val listSearch: ListView = view!!.findViewById(R.id.list_buscar)

        val searchAdapter = ListMyRecipesAdapter(context!!, datosMyRecipes)
        listSearch.adapter = searchAdapter

        search_view_buscar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(resultados: String?): Boolean {
                if(resultados != null){
                    searchAdapter.filter(resultados)
                }
                return false

            }

            override fun onQueryTextSubmit(resultados: String?): Boolean {
                /*if(resultados != null){
                    busqueda = resultados
                    busquedaFireBase()
                }else{
                    busqueda = "Vacio"
                    busquedaFireBase()
                }*/
                return false
            }
        })

        listSearch.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
            listener.onItemPressed(searchAdapter.dataSource[position].title, searchAdapter.dataSource[position].resDish, searchAdapter.dataSource[position].esFav.toString() )
        }
    }

}
