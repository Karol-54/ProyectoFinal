package com.jaimes.nodocivico.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jaimes.nodocivico.db.AppDatabase
import com.jaimes.nodocivico.model.Report
import com.jaimes.nodocivico.repository.ReportRepository
import kotlinx.coroutines.launch

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ReportRepository
    val reports: LiveData<List<Report>>
    val totalReports: LiveData<Int>
    val pendingReports: LiveData<Int>
    val syncedReports: LiveData<Int>

    private val _selectedReport = MutableLiveData<Report?>()
    val selectedReport: LiveData<Report?> = _selectedReport

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ReportRepository(database)
        reports = repository.getAllReports()
        totalReports = repository.getTotalReports()
        pendingReports = repository.getPendingReports()
        syncedReports = repository.getSyncedReports()
    }

    fun getReportById(id: Int): LiveData<Report> {
        return repository.getReportById(id)
    }

    fun addReport(report: Report) {
        viewModelScope.launch {
            repository.insert(report)
        }
    }

    fun updateReport(report: Report) {
        viewModelScope.launch {
            repository.update(report)
        }
    }

    fun deleteReport(report: Report) {
        viewModelScope.launch {
            repository.delete(report)
        }
    }

    fun selectReport(report: Report) {
        _selectedReport.value = report
    }
}