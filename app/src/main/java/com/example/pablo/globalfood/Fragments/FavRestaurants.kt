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
import com.example.pablo.globalfood.Adapters.ListFavRecipesAdapter
import com.example.pablo.globalfood.Adapters.ListFavRestaurantsAdapter
import com.example.pablo.globalfood.Model.FavRecipe
import com.example.pablo.globalfood.Model.FavRestaurant
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.favorite_recipes.*
import kotlinx.android.synthetic.main.favorite_restaurants.*


class FavRestaurants : Fragment() {

    private lateinit var listener: OnButtonPressedListener
    val datosFavRestaurants = ArrayList<FavRestaurant>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fireBaseSelectFavRecipes()
        return inflater.inflate(R.layout.favorite_restaurants, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

    fun fireBaseSelectFavRecipes(){
        val db = FirebaseFirestore.getInstance()

        db.collection("Recetas")
                .whereEqualTo("tipo", "Plato")
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(values: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                        if (p1 != null) {
                            Log.w("MainMenuActivity", "Listen failed.", p1)
                            return
                        }

                        if (values != null) {
                            for (doc in values) {
                                if (doc.get("tipo") != null) {
                                    datosFavRestaurants.add(FavRestaurant(doc.getString("titulo")!!, doc.getString("pais")!!,
                                            doc.getString("tipo")!!, doc.getLong("numFavs")!!))
                                    fillListFavRestaurants()
                                }
                            }
                        }
                        Log.d("MainMenuActivity", "Current cites in CA: $datosFavRestaurants")
                    }
                })
    }

    fun fillListFavRestaurants(){
        val listFavRestau: ListView = view!!.findViewById(R.id.list_fav_restaurants)

        val favRestaurantAdapter = ListFavRestaurantsAdapter(context!!, datosFavRestaurants)
        listFavRestau.adapter = favRestaurantAdapter

        listFavRestau.onItemClickListener = (AdapterView.OnItemClickListener { _, _, position, _ ->
            listener.onItemPressed(favRestaurantAdapter.dataSource[position].title)
            listener.onButtonPressed(list_fav_restaurants.tag.toString())
        })
    }

}
