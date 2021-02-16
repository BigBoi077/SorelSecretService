package cegepst.example.sorelsecretservice.models

class SuspiciousActivity(
    var ID: Long, var trustLevel: Int,
    val behaviorID: Long, val location: String, val createdDate: Long
)