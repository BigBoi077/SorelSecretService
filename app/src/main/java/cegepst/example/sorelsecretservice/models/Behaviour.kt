package cegepst.example.sorelsecretservice.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "behavior")
data class Behaviour(
    @ColumnInfo(name = "id_behavior") val ID: Long,
    @ColumnInfo(name = "beahvior_name") var name: String,
    @ColumnInfo(name = "behavior_description") var description: String
)