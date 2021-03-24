package com.example.mystocks

import android.view.View

fun View.isVisible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}