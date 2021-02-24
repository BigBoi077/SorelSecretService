package cegepst.example.sorelsecretservice.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cegepst.example.sorelsecretservice.R
import cegepst.example.sorelsecretservice.controllers.ActivitiesController
import cegepst.example.sorelsecretservice.models.LocationEnumarator
import cegepst.example.sorelsecretservice.models.SuspiciousActivity

class CreateSuspicionActivity : AppCompatActivity() {

    private lateinit var controller: ActivitiesController

    private lateinit var headingView: TextView
    private lateinit var trustLevelView: EditText
    private lateinit var behaviorListView: Spinner
    private lateinit var locationListView: Spinner

    private var activityId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_suspicion)
        initClassContent()
        val id = intent.getLongExtra("id", 0)
        activityId = id
        if (id != 0L) {
            controller.loadActivity(id)
        }
    }

    private fun initClassContent() {
        headingView = findViewById(R.id.activityTitle)
        headingView.text = intent.getStringExtra("activityTitle")
        trustLevelView = findViewById(R.id.trustLevelEntry)
        behaviorListView = findViewById(R.id.behaviorListEntry)
        locationListView = findViewById(R.id.locationListEntry)
        controller = ActivitiesController(this)
    }

    fun onLocationsLoaded(locations: ArrayList<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, locations)
        locationListView.adapter = adapter
    }

    fun saveSuspiciousActivity(view: View) {
        val trustLevel = trustLevelView.text.toString()
        if (trustLevel.toInt() < 1 || trustLevel.toInt() > 10) {
            Toast.makeText(this, "Trust level must be between 0 and 10", Toast.LENGTH_SHORT).show()
            return
        }
        val behavior = behaviorListView.selectedItemPosition
        val location = locationListView.selectedItemPosition
        sendBackInformation(trustLevel, behavior, location)
    }

    private fun sendBackInformation(trustLevel: String, behavior: Int, location: Int) {
        val data = Intent()
        if (activityId != 0L) {
            data.putExtra("id", activityId)
        }
        data.putExtra("trustLevel", trustLevel)
        data.putExtra("behavior", behavior)
        data.putExtra("location", location)
        setResult(RESULT_OK, data)
        finish()
    }

    fun cancelSuspiciousActivity(view: View) {
        finish()
    }

    fun onBehaviorsLoaded(behaviors: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, behaviors)
        behaviorListView.adapter = adapter
        controller.onActivitiesOrBehaviorsLoaded()
    }

    fun onActivitiesAndBehaviorsLoaded(index: Int) {
        behaviorListView.setSelection(index)
    }

    fun onSuspiciousActivityLoaded(suspiciousActivity: SuspiciousActivity) {
        headingView.text = "Edit Suspicious Activity"
        trustLevelView.setText(suspiciousActivity.trustLevel.toString())
        behaviorListView.setSelection(suspiciousActivity.behaviorID.toInt() - 1)
        for (location in LocationEnumarator.values()) {
            if (suspiciousActivity.location == location.location) {
                val locationIndex = location.ordinal
                locationListView.setSelection(locationIndex)
            }
        }
    }
}