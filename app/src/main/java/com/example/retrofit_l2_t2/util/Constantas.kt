package com.example.retrofit_l2_t2.util

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

object Constantas {

}

fun Fragment.snackBar(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
}