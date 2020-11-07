package com.codelectro.mvvmnews.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun String.formatDate(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")
    return ZonedDateTime.parse(this).format(formatter)
}