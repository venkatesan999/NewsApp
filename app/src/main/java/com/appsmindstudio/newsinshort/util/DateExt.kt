package com.appsmindstudio.newsinshort.util

import java.text.SimpleDateFormat
import java.util.Locale

val yyyy_MM_dd_T_HH_mm_ss = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
val MMM_dd = SimpleDateFormat("MMMM dd, yyyy HH:mm a", Locale.ENGLISH)

fun String?.convertToDateFormat(
    inputFormat: SimpleDateFormat,
    outputFormat: SimpleDateFormat,
): String = this?.takeIf { !isNullOrEmpty() }
    ?.let { inputFormat.parse(it) }
    ?.let { outputFormat.format(it) }
    ?: "-"