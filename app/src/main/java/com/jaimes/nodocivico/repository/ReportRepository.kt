package com.jaimes.nodocivico.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.jaimes.nodocivico.db.AppDatabase
import com.jaimes.nodocivico.db.ReportEntity
import com.jaimes.nodocivico.model.Report

class ReportRepository(private val database: AppDatabase) {

    fun getAllReports(): LiveData<List<Report>> {
        return database.reportDao().getAllReports().map { entities ->
            entities.map { it.toReport() }
        }
    }

    fun getReportById(id: Int): LiveData<Report?> {
        return database.reportDao().getReportById(id).map { it?.toReport() }
    }

    fun getTotalReports(): LiveData<Int> = database.reportDao().getTotalReports()

    fun getPendingReports(): LiveData<Int> = database.reportDao().getPendingReports()

    fun getSyncedReports(): LiveData<Int> = database.reportDao().getSyncedReports()

    suspend fun insert(report: Report) {
        database.reportDao().insert(report.toEntity())
    }

    suspend fun update(report: Report) {
        database.reportDao().update(report.toEntity())
    }

    suspend fun delete(report: Report) {
        database.reportDao().delete(report.toEntity())
    }

    private fun ReportEntity.toReport() = Report(
        id = id,
        title = title,
        description = description,
        category = category,
        priority = priority,
        location = location,
        date = date,
        status = status,
        userId = userId
    )

    private fun Report.toEntity() = ReportEntity(
        id = id,
        title = title,
        description = description,
        category = category,
        priority = priority,
        location = location,
        date = date,
        status = status,
        userId = userId
    )
}