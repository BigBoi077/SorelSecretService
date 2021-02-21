package cegepst.example.sorelsecretservice.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suspicious_activity")
class SuspiciousActivity(
    @ColumnInfo(name = "trust_level") var trustLevel: Int,
    @ColumnInfo(name = "behavior_id") var behaviorID: Long,
    @ColumnInfo(name = "location") var location: String,
    @ColumnInfo(name = "date_creation") var createdDate: String
) {
    fun onActivitiesUpdated() {
        TODO("Not yet implemented")
    }

    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0
}