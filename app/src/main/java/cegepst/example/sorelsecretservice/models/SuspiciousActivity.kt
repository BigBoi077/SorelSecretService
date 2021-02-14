package cegepst.example.sorelsecretservice.models

class SuspiciousActivity(val ID: Long, var confidenceLevel: Int,
                         val behaviorID: Long, val location: String, val createdDate: Long) {

}