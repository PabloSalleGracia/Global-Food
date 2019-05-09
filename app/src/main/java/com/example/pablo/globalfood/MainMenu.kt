package com.example.pablo.globalfood


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main_menu.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MainMenu : Fragment() {

    private lateinit var listener: OnButtonPressedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        misRecetas.setOnClickListener {
            listener.onButtonPressed(misRecetas.tag.toString())
        }
        recetasFav.setOnClickListener {
            listener.onButtonPressed(recetasFav.tag.toString())
        }
        buscar.setOnClickListener {
            listener.onButtonPressed(buscar.tag.toString())
        }
        restaurantesFav.setOnClickListener {
            listener.onButtonPressed(restaurantesFav.tag.toString())
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener


    }


}
