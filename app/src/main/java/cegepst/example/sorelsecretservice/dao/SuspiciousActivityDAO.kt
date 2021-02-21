package cegepst.example.sorelsecretservice.dao

import androidx.room.*
import cegepst.example.sorelsecretservice.models.ActivityWithBehavior
import cegepst.example.sorelsecretservice.models.SuspiciousActivity

interface SuspiciousActivityDAO {
    @Query("SELECT * FROM suspicious_activity")
    fun getAll(): List<SuspiciousActivity>

    @Transaction
    @Query("SELECT * FROM suspicious_activity WHERE id=:id")
    fun getWithBehavior(id: Long): ActivityWithBehavior

    @Transaction
    @Query("SELECT * FROM suspicious_activity")
    fun getAllWithBehaviors(): List<ActivityWithBehavior>

    @Query("SELECT * FROM suspicious_activity WHERE id=:id")
    fun get(id: Long): SuspiciousActivity

    @Insert
    fun insert(activity: ActivityWithBehavior): Long

    @Update
    fun update(activity: SuspiciousActivity)

    @Delete
    fun delete(activity: ActivityWithBehavior)
}