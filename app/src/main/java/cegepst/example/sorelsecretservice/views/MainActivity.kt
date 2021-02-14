package cegepst.example.sorelsecretservice.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cegepst.example.sorelsecretservice.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMenu()
    }

    private fun initMenu() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.addSuspiciousActivityMenuButton -> {
                    val intent = Intent(this, AddSuspiciousActivity::class.java)
                    startActivity(intent)
                }
                R.id.suspiciousActivitiesMenuButton -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.suspiciousActivitiesStatsMenuButton -> {
                    val intent = Intent(this, StatsActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }
}