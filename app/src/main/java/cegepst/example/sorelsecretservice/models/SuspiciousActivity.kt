package cegepst.example.sorelsecretservice.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "suspicious_activity")
class SuspiciousActivity(
    @ColumnInfo(name = "id_activity") val ID: Long,
    @ColumnInfo(name = "trust_level") val trustLevel: Int,
    @ColumnInfo(name = "behavior_id") val behaviorID: Long,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "date_creation") val createdDate: Long
)