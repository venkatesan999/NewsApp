package com.appsmindstudio.readinnews.util

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

object Utils {
    val countries = listOf(
        "United States of America - US",
        "China - CN",
        "Germany - DE",
        "Japan - JP",
        "India - IN",
        "United Kingdom (UK) - GB",
        "France - FR",
        "Italy - IT",
        "Brazil - BR",
        "Canada - CA"
    )

    val categories =
        listOf(
            "business",
            "entertainment",
            "health",
            "science",
            "sports",
            "technology"
        )
    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }
}