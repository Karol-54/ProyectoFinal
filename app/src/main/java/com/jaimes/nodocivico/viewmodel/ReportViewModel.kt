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

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    private val repository: ReportRepository
    val reports: LiveData<List<Report>>
    val totalReports: LiveData<Int>
    val pendingReports: LiveData<Int>
    val syncedReports: LiveData<Int>

    val resolvedReports: LiveData<Int>

    private val _syncStatus = MutableLiveData<String>()
    val syncStatus: LiveData<String> = _syncStatus

    private val _selectedReport = MutableLiveData<Report?>()
    val selectedReport: LiveData<Report?> = _selectedReport

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ReportRepository(database)
        reports = repository.getAllReports()
        totalReports = repository.getTotalReports()
        pendingReports = repository.getPendingReports()
        syncedReports = repository.getSyncedReports()
        resolvedReports = repository.getResolvedReports()
    }

    fun getReportById(id: Int): LiveData<Report?> {
        return repository.getReportById(id)
    }

    fun addReport(report: Report) {
        viewModelScope.launch {
            val newId = repository.insertAndGetId(report)
            pushReportToApi(report.copy(id = newId.toInt()))
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

    fun syncReports() {
        viewModelScope.launch {
            _syncStatus.postValue("Sincronizando...")
            try {
                // 1. Subir reportes locales no sincronizados
                val localReports = repository.getUnsyncedReports()
                android.util.Log.d("SYNC", "Reportes no sincronizados: ${localReports.size}")
                localReports.forEach { report ->
                    android.util.Log.d("SYNC", "Subiendo reporte ID: ${report.id}, isSynced: ${report.isSynced}")
                    val reporteApi = com.jaimes.nodocivico.network.ReporteApi(
                        titulo = report.title,
                        descripcion = report.description,
                        categoria = report.category,
                        prioridad = report.priority,
                        ubicacion = report.location,
                        fecha = report.date
                    )
                    val response = com.jaimes.nodocivico.network.RetrofitClient.api.crearReporte(reporteApi)
                    if (response.isSuccessful) {
                        val newId = response.body()?.id ?: report.id
                        repository.markAsSynced(report.id, newId)
                        android.util.Log.d("SYNC", "Marcado como sync. OldID: ${report.id}, NewID: $newId")
                        val existe = repository.getReportByIdDirect(newId)
                        android.util.Log.d("SYNC", "Verificando ID $newId en local: ${existe != null}")
                    }
                }

                // 2. Bajar reportes de la API que no existen en local
                val response = com.jaimes.nodocivico.network.RetrofitClient.api.listarReportes()
                if (response.isSuccessful) {
                    response.body()?.forEach { reporteApi ->
                        val existe = repository.getReportByIdDirect(reporteApi.id)
                        if (existe == null) {
                            repository.insert(Report(
                                id = reporteApi.id,
                                title = reporteApi.titulo,
                                description = reporteApi.descripcion,
                                category = reporteApi.categoria,
                                priority = reporteApi.prioridad,
                                location = reporteApi.ubicacion,
                                date = reporteApi.fecha,
                                status = reporteApi.estado,
                                isSynced = true
                            ))
                        }
                    }
                    _syncStatus.postValue("Sincronizado ✓")
                } else {
                    _syncStatus.postValue("Error al sincronizar")
                }
            } catch (e: Exception) {
                _syncStatus.postValue("Sin conexión ✗")
                _error.postValue("Error de sincronización: ${e.message}")
            }
        }
    }



    fun pushReportToApi(report: Report) {
        viewModelScope.launch {
            try {
                val reporteApi = com.jaimes.nodocivico.network.ReporteApi(
                    titulo = report.title,
                    descripcion = report.description,
                    categoria = report.category,
                    prioridad = report.priority,
                    ubicacion = report.location,
                    fecha = report.date
                )
                val response = com.jaimes.nodocivico.network.RetrofitClient.api.crearReporte(reporteApi)
                if (response.isSuccessful) {
                    val newId = response.body()?.id ?: report.id
                    repository.markAsSynced(report.id, newId)
                    android.util.Log.d("API", "Reporte enviado y marcado como sincronizado")
                } else {
                    _error.postValue("Error al enviar: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error de red: ${e.message}")
            }
        }
    }
}