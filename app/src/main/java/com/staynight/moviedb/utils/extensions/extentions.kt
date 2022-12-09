package com.staynight.moviedb.utils.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.staynight.moviedb.R

fun navigateTo(fragment: Fragment, fragmentManager: FragmentManager) {
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.fl_container, fragment, fragment.tag)
    transaction.addToBackStack(fragment.tag)
    transaction.commit()
}