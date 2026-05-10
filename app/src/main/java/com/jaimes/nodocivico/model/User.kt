package com.jaimes.nodocivico.model

data class User(
    val id: Int = 0,
    val name: String,
    val email: String,
    val neighborhood: String
)