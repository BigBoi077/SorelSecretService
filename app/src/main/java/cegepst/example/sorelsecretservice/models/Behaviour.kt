package cegepst.example.sorelsecretservice.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "behavior")
class Behaviour(
        @ColumnInfo(name = "behavior_name") var name: String,
        @ColumnInfo(name = "behavior_description") var description: String
) {
    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0

}