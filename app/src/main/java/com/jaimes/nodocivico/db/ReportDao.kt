package com.jaimes.nodocivico.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReportDao {

    @Query("SELECT * FROM reports ORDER BY date DESC")
    fun getAllReports(): LiveData<List<ReportEntity>>

    @Query("SELECT * FROM reports WHERE id = :id")
    fun getReportById(id: Int): LiveData<ReportEntity?>

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

    @Query("SELECT * FROM reports WHERE id = :id LIMIT 1")
    suspend fun getReportByIdDirect(id: Int): ReportEntity?

    @Query("UPDATE reports SET isSynced = 1 WHERE id = :oldId")
    suspend fun markAsSynced(oldId: Int)

    @Query("SELECT * FROM reports WHERE isSynced = 0")
    suspend fun getUnsyncedReports(): List<ReportEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndGetId(report: ReportEntity): Long

    @Query("SELECT COUNT(*) FROM reports WHERE status = 'Resuelto'")
    fun getResolvedReports(): LiveData<Int>
}