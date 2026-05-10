package com.jaimes.nodocivico.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
    val location: String,
    val date: String,
    val status: String = "Pendiente",
    val userId: Int = 0,
    val isSynced: Boolean = false
)