package cegepst.example.sorelsecretservice.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cegepst.example.sorelsecretservice.R
import cegepst.example.sorelsecretservice.controllers.BehaviorsController
import cegepst.example.sorelsecretservice.views.adapters.BehaviorAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val REQUEST_CODE_ADD = 1

class BehaviorsActivity : AppCompatActivity() {

    private lateinit var controller: BehaviorsController
    private lateinit var behaviorAdapter: BehaviorAdapter

    private fun initMenu() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.addSuspiciousActivityMenuButton -> {
                    val intent = Intent(this, BehaviorsActivity::class.java)
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
        controller = BehaviorsController(this)
        behaviorAdapter = BehaviorAdapter(controller)
        val recyclerView = findViewById<RecyclerView>(R.id.listSuspiciousBehaviors)
        recyclerView.adapter = behaviorAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        behaviorAdapter.onDeleteListener = { position ->
            controller.deleteBehavior(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_suspicious_behavior)
        initMenu()
        initAdapter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_CODE_ADD -> addBehavior(data ?: Intent())
        }
    }

    private fun addBehavior(data: Intent) {
        val name = data.getStringExtra("name")
        val description = data.getStringExtra("description")

        if (description != null && name != null) {
            Toast.makeText(this, "NEW BEHAVIOR $name | $description", Toast.LENGTH_SHORT).show()
            controller.addBehavior(name, description)
        }
    }

    fun onAddNewBehavior(view: View) {
        val intent = Intent(this, CreateBehaviorActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_ADD)
    }

    fun onBehaviorsUpdated() {
        behaviorAdapter.notifyDataSetChanged()
    }
}