package com.jaimes.nodocivico.model

data class Report(
    val id: Int = 0,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
    val location: String,
    val date: String,
    val status: String = "Pendiente",
    val userId: Int = 0
)