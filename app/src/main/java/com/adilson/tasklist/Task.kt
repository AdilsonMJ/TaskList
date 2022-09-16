package com.adilson.tasklist

data class Task(
    val title : String,
    val descrption : String,
    var done : Boolean = false
)
