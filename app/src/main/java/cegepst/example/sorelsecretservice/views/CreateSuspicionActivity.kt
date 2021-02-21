package cegepst.example.sorelsecretservice.views

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_suspicion)
        initClassContent()
        val id = intent.getLongExtra("id", 0)

    }

    private fun initClassContent() {
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
        trustLevelView.setText(suspiciousActivity.trustLevel)
        behaviorListView.setSelection(suspiciousActivity.behaviorID.toInt())
        for (location in LocationEnumarator.values()) {
            if (suspiciousActivity.location == location.location) {
                val locationIndex = location.ordinal
                locationListView.setSelection(locationIndex)
            }
        }
    }
}