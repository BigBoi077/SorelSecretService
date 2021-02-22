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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_CODE_ADD -> addSuspiciousActivity(data ?: Intent())
            REQUEST_CODE_EDIT -> editSuspiciousActivity(data ?: Intent())
        }
    }

    private fun addSuspiciousActivity(data: Intent) {
        val trustLevel = data.getStringExtra("trustLevel")
        val behavior = data.getIntExtra("behavior", 0)
        val location = data.getIntExtra("location", 0)
        if (trustLevel != null) {
            mainController.addSuspiciousActivity(trustLevel.toInt(), behavior, location)
        }
    }

    private fun editSuspiciousActivity(data: Intent) {
        val id = data.getLongExtra("id", 0)
        val trustLevel = data.getStringExtra("trustLevel")
        val behavior = data.getIntExtra("behavior", 0)
        val location = data.getIntExtra("location", 0)
        if (trustLevel != null) {
            mainController.editActivity(id, trustLevel.toInt(), behavior, location)
        }
    }

    fun onAddNewSuspicion(view: View) {
        val intent = Intent(this, CreateSuspicionActivity::class.java)
        intent.putExtra("activityTitle", "Create Character")
        startActivityForResult(intent, REQUEST_CODE_ADD)
    }

    fun onModifySuspiciousActivity(id: Long) {
        val intent = Intent(this, CreateSuspicionActivity::class.java)
        intent.putExtra("id", id)
        startActivityForResult(intent, REQUEST_CODE_EDIT)
    }

    fun onSuspiciousActivitiesUpdated() {
        suspiciousActivityAdapter.notifyDataSetChanged()
    }

    fun onSuspiciousActivitiesAdded(position: Int) {
        suspiciousActivityAdapter.notifyItemChanged(position)
    }
}