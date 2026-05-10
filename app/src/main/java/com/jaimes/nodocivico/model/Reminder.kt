package com.jaimes.nodocivico.model

data class Reminder(
    val id: Int = 0,
    val reportId: Int,
    val message: String,
    val date: String,
    val isActive: Boolean = true
)