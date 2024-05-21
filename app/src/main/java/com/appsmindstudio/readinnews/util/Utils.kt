package com.appsmindstudio.readinnews.util

import com.appsmindstudio.readinnews.R
import com.appsmindstudio.readinnews.data.models.NewsCategories

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
            "Business",
            "Entertainment",
            "Health",
            "Science",
            "Sports",
            "Technology"
        )

    val staticOnBoardList = listOf(
        R.drawable.new1,
        R.drawable.new2,
        R.drawable.new3,
    )

    val categoryList = listOf(
        NewsCategories(R.drawable.business, "Business"),
        NewsCategories(R.drawable.technology, "Technology"),
        NewsCategories(R.drawable.all, "All"),
        NewsCategories(R.drawable.science, "Science"),
        NewsCategories(R.drawable.health, "Health"),
        NewsCategories(R.drawable.entertainment, "Entertainment"),
        NewsCategories(R.drawable.sport, "Sports"),
    )
}