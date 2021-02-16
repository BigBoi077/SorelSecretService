package cegepst.example.sorelsecretservice.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cegepst.example.sorelsecretservice.models.SuspiciousActivity

interface SuspiciousActivityDAO {
    @Query("SELECT * FROM suspiciousActivities")
    fun getAll(): List<SuspiciousActivity>

    @Query("SELECT * FROM suspiciousActivities WHERE id=:id")
    fun get(id: Long): SuspiciousActivity

    @Insert
    fun insert(activity: SuspiciousActivity): Long

    @Update
    fun update(activity: SuspiciousActivity)

    @Delete
    fun delete(activity: SuspiciousActivity)
}