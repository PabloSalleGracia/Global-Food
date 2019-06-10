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
import android.widget.SearchView
import com.example.pablo.globalfood.Adapters.ListFavRecipesAdapter
import com.example.pablo.globalfood.Adapters.ListFavRestaurantsAdapter
import com.example.pablo.globalfood.Model.FavRecipe
import com.example.pablo.globalfood.Model.FavRestaurant
import com.example.pablo.globalfood.Model.MyRecipe
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.favorite_recipes.*
import kotlinx.android.synthetic.main.favorite_restaurants.*


class FavRestaurants : Fragment() {

    private lateinit var listener: OnButtonPressedListener
    val datosFavRestaurants = ArrayList<FavRestaurant>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorite_restaurants, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fireBaseSelectFavRecipes()

        search_view_fav_restaurants.setOnClickListener{
            listener.onButtonPressed("SEARCH")
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

    private fun fireBaseSelectFavRecipes(){
        val db = FirebaseFirestore.getInstance()
        //id del usuario logeado en firebase
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        db.collection("Usuario-Restaurantes")
                .whereEqualTo("id_usuario", refUserId)
                .whereEqualTo("esFav?", true)
                .addSnapshotListener { values, _ ->
                    if (values != null) {
                        if(values.size() != 0) {
                            for (doc in values) {
                                if (doc.getString("tipo") != null) {

                                    for (dc in values.documentChanges) {
                                        when (dc.type) {
                                            DocumentChange.Type.MODIFIED -> {
                                                println("MODIFIED")
                                            }
                                            DocumentChange.Type.ADDED -> {
                                                if(!datosFavRestaurants.contains(FavRestaurant(doc.getString("titulo")!!, doc.getString("pais")!!,
                                                                doc.getString("tipo")!!, doc.getBoolean("esFav?")!!))){
                                                    datosFavRestaurants.add(FavRestaurant(doc.getString("titulo")!!, doc.getString("pais")!!,
                                                            doc.getString("tipo")!!, doc.getBoolean("esFav?")!!))
                                                    println("old add$dc.oldIndex")
                                                    println("new add$dc.newIndex")
                                                    println(datosFavRestaurants)
                                                }
                                            }
                                            DocumentChange.Type.REMOVED -> {
                                                println(datosFavRestaurants)
                                                //datosFavRestaurants.removeAt(dc.oldIndex)
                                                datosFavRestaurants.remove(FavRestaurant(doc.getString("titulo")!!, doc.getString("pais")!!,
                                                        doc.getString("tipo")!!, doc.getBoolean("esFav?")!!))
                                                println(datosFavRestaurants)
                                            }

                                        }
                                    }

                                    fillListFavRestaurants()
                                }
                            }
                        }else{
                            datosFavRestaurants.clear()
                        }
                    }
                }
    }

    private fun fillListFavRestaurants(){
        val listFavRestau: ListView = view!!.findViewById(R.id.list_fav_restaurants)

        val favRestaurantAdapter = ListFavRestaurantsAdapter(context!!, datosFavRestaurants)
        listFavRestau.adapter = favRestaurantAdapter

        listFavRestau.onItemClickListener = (AdapterView.OnItemClickListener { _, _, position, _ ->
            listener.onItemPressed(favRestaurantAdapter.dataSource[position].title, favRestaurantAdapter.dataSource[position].resDish)
        })
    }

}
