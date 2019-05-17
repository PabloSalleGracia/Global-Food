package com.example.pablo.globalfood

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * A placeholder fragment containing a simple view.
 */
class MainMenuFragment : Fragment() {

    private lateinit var listener: OnButtonPressedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*misRecetas.setOnClickListener(){
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
        }*/


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }
}
