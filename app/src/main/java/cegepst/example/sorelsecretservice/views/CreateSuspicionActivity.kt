package cegepst.example.sorelsecretservice.views

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import cegepst.example.sorelsecretservice.R
import cegepst.example.sorelsecretservice.models.LocationEnumarator

class CreateSuspicionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_suspicion)
        populateSpinner()
    }

    private fun populateSpinner() {
        val spinner: Spinner = findViewById(R.id.locationList)
        var locations: ArrayList<String> = ArrayList()
        for (location in LocationEnumarator.values()) {
            locations.add(location.location)
        }
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, locations)
    }
}