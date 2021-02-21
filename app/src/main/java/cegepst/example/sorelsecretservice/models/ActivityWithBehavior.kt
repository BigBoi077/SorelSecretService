package cegepst.example.sorelsecretservice.models

import androidx.room.Embedded
import androidx.room.Relation

data class ActivityWithBehavior(
    @Embedded val activity: SuspiciousActivity,
    @Relation(
        parentColumn = "behavior_id",
        entityColumn = "id"
    ) val behaviour: Behaviour
)