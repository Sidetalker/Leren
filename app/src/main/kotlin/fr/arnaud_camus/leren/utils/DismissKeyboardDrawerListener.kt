package fr.arnaud_camus.leren.utils

import android.content.Context
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by arnaud on 3/6/16.
 */
class DismissKeyboardDrawerListener: DrawerLayout.SimpleDrawerListener() {
    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        val inputMethodManager = drawerView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(drawerView.getApplicationWindowToken(), 0)
    }
}