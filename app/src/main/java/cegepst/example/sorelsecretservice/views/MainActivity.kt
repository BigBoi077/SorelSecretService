package cegepst.example.sorelsecretservice.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cegepst.example.sorelsecretservice.R
import cegepst.example.sorelsecretservice.controllers.MainController
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val REQUEST_CODE_ADD = 1
private const val REQUEST_CODE_EDIT = 2

class MainActivity : AppCompatActivity() {

    private lateinit var mainController: MainController
    private lateinit var suspiciousActivityAdapter: SuspiciousActivityAdapter

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

    private fun initAdapter() {
        suspiciousActivityAdapter = SuspiciousActivityAdapter(mainController)
        val recyclerView = findViewById<RecyclerView>(R.id.listSuspiciousActivities)
        recyclerView.adapter = suspiciousActivityAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        addEventListeners()
    }

    private fun addEventListeners() {
        suspiciousActivityAdapter.onDeleteListener = { position ->
            mainController.deleteSuspiciousActivity(position)
        }
        suspiciousActivityAdapter.onModifyListener = { position ->
            mainController.modifySuspiciousActivity(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainController = MainController(this)
        initMenu()
        initAdapter()
    }

    fun onAddNewSuspicion(view: View) {
        val intent = Intent(this, CreateSuspicionActivity::class.java)
        intent.putExtra("activityTitle", "Create Character")
        startActivityForResult(intent, REQUEST_CODE_ADD)
    }

    fun onSuspiciousActivitiesUpdated() {
        TODO("Not yet implemented")
    }

    fun onSuspiciousActivitiesAdded(size: Int) {
        TODO("Not yet implemented")
    }

    fun onModifySuspiciousActivity(id: Long) {

    }
}