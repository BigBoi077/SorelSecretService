package cegepst.example.sorelsecretservice.controllers

import cegepst.example.sorelsecretservice.models.LocationEnumarator
import cegepst.example.sorelsecretservice.models.LocationStat
import cegepst.example.sorelsecretservice.models.SuspiciousActivity
import cegepst.example.sorelsecretservice.stores.AppStore
import cegepst.example.sorelsecretservice.views.StatsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

class StatisticsController(statsActivity: StatsActivity) {

    private var stats = ArrayList<LocationStat>()
    private var weakReference = WeakReference(statsActivity)
    private val database = AppStore(statsActivity)


    val activity: StatsActivity?
        get() {
            return weakReference.get()
        }

    val statisticsSize: Int
        get() {
            return stats.size
        }

    init {
        loadStats()
    }

    private fun loadStats() {
        GlobalScope.launch {
            for (location in LocationEnumarator.values()) {
                val stringLocation = location.location
                var suspiciousActivities = database.suspiciousActivitiesDAO().getWithLocation(stringLocation) as ArrayList<SuspiciousActivity>
                if (suspiciousActivities.size > 0) {
                    calculateStats(suspiciousActivities)
                }
            }
            withContext(Dispatchers.Main) {
                activity?.onStatsCalculated()
            }
        }
    }

    private fun calculateStats(activities: ArrayList<SuspiciousActivity>) {
        var trustTotal = 0
        for (activity in activities) {
            trustTotal += activity.trustLevel
        }
        stats.add(LocationStat(activities[0].location, calculateTrustDegree(trustTotal)))
    }

    private fun calculateTrustDegree(trustTotal: Int): String {
        if (trustTotal != 0) {
            return "${trustTotal / (trustTotal * 10)}%"
        }
        return "0%"
    }

    fun getAdapterContent(position: Int, callback: (String, String) -> Unit) {
        val stat = stats[position]
        callback(stat.location, stat.percentage)
    }
}