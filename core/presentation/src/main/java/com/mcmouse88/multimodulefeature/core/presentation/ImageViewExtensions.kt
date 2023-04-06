package com.mcmouse88.multimodulefeature.core.presentation

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import coil.transform.RoundedCornersTransformation

fun ImageView.loadUrl(url: String) {
    load(url) {
        transformations(RoundedCornersTransformation(16f))
    }
}

fun ImageView.loadResource(@DrawableRes id: Int) {
    load(id) {
        transformations(RoundedCornersTransformation(16f))
    }
}

