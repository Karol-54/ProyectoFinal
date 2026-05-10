package com.jaimes.nodocivico.model

data class ReportStatus(
    val id: Int = 0,
    val reportId: Int,
    val status: String,
    val comment: String,
    val date: String
)