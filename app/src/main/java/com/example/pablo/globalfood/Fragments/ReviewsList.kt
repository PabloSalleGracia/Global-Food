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
import com.example.pablo.globalfood.Adapters.ListReviewsAdapter
import com.example.pablo.globalfood.Model.FavRecipe
import com.example.pablo.globalfood.Model.Review
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.OnTitleSelectedListener

import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.detail.*
import kotlinx.android.synthetic.main.reviews_list.*

private const val tituloRecibido = "titulo"
private const val tipoRecibido = "tipo"

class ReviewsList : Fragment() {

    private var tituloRewList: String? = null
    private var tipoRewList: String? = null
    private var paisListRev: String? = null
    val datosReviews = ArrayList<Review>()
    private lateinit var listener : OnButtonPressedListener
    private lateinit var listenerReview : OnTitleSelectedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.reviews_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tituloRewList = it.getString(tituloRecibido)
            tipoRewList = it.getString(tipoRecibido)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(tituloRewList: String, tipoRewList: String) =
                ReviewsList().apply {
                    arguments = Bundle().apply {
                        putString(tituloRecibido, tituloRewList)
                        putString(tipoRecibido, tipoRewList)
                    }
                }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        titulo_reviews_list.text = tituloRewList
        resDishListRev.text = tipoRewList

        fireBaseSelectReviews()

        volver_review_list.setOnClickListener{
            listener.onButtonPressed("Volver")
        }

        write_reviews.setOnClickListener{
            listenerReview.onTitleSelected(titulo_reviews_list.text.toString(), tipoRewList!!)
            listener.onButtonPressed(write_reviews.tag.toString())
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
        listenerReview = activity as OnTitleSelectedListener
    }

    fun fireBaseSelectReviews(){
        val db = FirebaseFirestore.getInstance()

        if(tipoRewList == "Plato"){
            db.collection("Recetas")
                    .whereEqualTo("titulo", tituloRewList)
                    .addSnapshotListener { values, _ ->
                        if (values != null) {
                            for (doc in values) {
                                if (doc.getString("titulo") != null) {
                                    //fix?
                                    paisListRev = doc.getString("pais")
                                    paisListReviews.text = paisListRev
                                    doc.reference.collection("Reviews")
                                            .addSnapshotListener{ reviews, _ ->
                                                if (reviews != null) {
                                                    for (docRev in reviews) {
                                                        if (docRev.get("autor") != null) {
                                                            datosReviews.add(Review(docRev.getString("autor")!!,
                                                                    docRev.getString("descripBreve")!!, doc.getString("pais")!!
                                                            ))
                                                        }
                                                    }
                                                    fillListReviews()
                                                }
                                            }

                                }

                            }

                        }
                    }
        }else{
            db.collection("Restaurantes")
                    .whereEqualTo("titulo", tituloRewList)
                    .addSnapshotListener { values, _ ->
                        if (values != null) {
                            for (doc in values) {
                                if (doc.getString("titulo") != null) {
                                    //fix?
                                    paisListRev = doc.getString("pais")
                                    paisListReviews.text = paisListRev
                                    doc.reference.collection("Reviews")
                                            .addSnapshotListener{ reviews, _ ->
                                                if (reviews != null) {
                                                    for (docRev in reviews) {
                                                        if (docRev.get("autor") != null) {
                                                            datosReviews.add(Review(docRev.getString("autor")!!,
                                                                    docRev.getString("descripBreve")!!, doc.getString("pais")!!
                                                            ))
                                                            fillListReviews()
                                                        }
                                                    }
                                                }
                                            }
                                }
                            }
                        }
                    }
        }


    }

    fun fillListReviews(){

        if(view != null){
            val listReviews: ListView = view!!.findViewById(R.id.list_reviews)


            val reviews = ListReviewsAdapter(context!!, datosReviews)
            listReviews.adapter = reviews


            listReviews.onItemClickListener = (AdapterView.OnItemClickListener { _, _, position, _ ->
                listenerReview.onAutorSelected(reviews.dataSource[position].nombreAutor)
                listener.onItemPressed(tituloRewList!!, tipoRewList!! )
            })

        }

    }

    override fun onPause() {
        super.onPause()
        datosReviews.clear()
    }



}
