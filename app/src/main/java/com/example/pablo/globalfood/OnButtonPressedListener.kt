package com.example.pablo.globalfood

interface OnButtonPressedListener {
    fun onButtonPressed(text: String)
    fun onItemPressed(titulo: String, tipo: String, fav: String)
}