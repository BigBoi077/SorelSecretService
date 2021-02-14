package cegepst.example.sorelsecretservice.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cegepst.example.sorelsecretservice.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class StatsActivity : AppCompatActivity() {

    private fun initMenu() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.addSuspiciousActivityMenuButton -> {
                    val intent = Intent(this, AddSuspiciousBehavior::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivityForResult(intent, 0)
                    overridePendingTransition(0, 0)
                }
                R.id.suspiciousActivitiesMenuButton -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivityForResult(intent, 0)
                    overridePendingTransition(0, 0)
                }
                R.id.suspiciousActivitiesStatsMenuButton -> {
                    val intent = Intent(this, StatsActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivityForResult(intent, 0)
                    overridePendingTransition(0, 0)
                }
            }
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        initMenu()
    }
}