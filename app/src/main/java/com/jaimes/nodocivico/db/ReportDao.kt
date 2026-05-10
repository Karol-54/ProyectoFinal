package com.jaimes.nodocivico.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReportDao {

    @Query("SELECT * FROM reports ORDER BY date DESC")
    fun getAllReports(): LiveData<List<ReportEntity>>

    @Query("SELECT * FROM reports WHERE id = :id")
    fun getReportById(id: Int): LiveData<ReportEntity>

    @Query("SELECT * FROM reports WHERE status = :status")
    fun getReportsByStatus(status: String): LiveData<List<ReportEntity>>

    @Query("SELECT COUNT(*) FROM reports")
    fun getTotalReports(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM reports WHERE status = 'Pendiente'")
    fun getPendingReports(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM reports WHERE isSynced = 1")
    fun getSyncedReports(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: ReportEntity)

    @Update
    suspend fun update(report: ReportEntity)

    @Delete
    suspend fun delete(report: ReportEntity)
}