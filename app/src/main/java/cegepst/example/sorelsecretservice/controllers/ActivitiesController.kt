package cegepst.example.sorelsecretservice.controllers

import android.util.Log
import cegepst.example.sorelsecretservice.models.Behaviour
import cegepst.example.sorelsecretservice.models.LocationEnumarator
import cegepst.example.sorelsecretservice.models.SuspiciousActivity
import cegepst.example.sorelsecretservice.stores.AppStore
import cegepst.example.sorelsecretservice.views.CreateSuspicionActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

class ActivitiesController(createSuspicionActivity: CreateSuspicionActivity) {

    private val weakReference = WeakReference(createSuspicionActivity)
    private val locations = LocationEnumarator.values()
    private val database = AppStore(createSuspicionActivity)
    private lateinit var behaviors: List<Behaviour>

    private var activitiesAndBehaviorsLoaded = 0
    private var loadedActivity: SuspiciousActivity? = null

    private val activity: CreateSuspicionActivity?
        get() {
            return weakReference.get()
        }

    init {
        loadSpinner()
        loadBehaviors()
    }

    private fun loadBehaviors() {
        GlobalScope.launch {
            behaviors = database.behaviorsDAO().getAll()
            withContext(Dispatchers.Main) {
                activity?.onBehaviorsLoaded(behaviors.map { behaviour -> behaviour.name })
            }
        }
    }

    fun loadActivity(id: Long) {
        GlobalScope.launch {
            val suspiciousActivity = database.suspiciousActivitiesDAO().get(id)
            Log.d("FAIL !!!", "${suspiciousActivity.trustLevel}")
            loadedActivity = suspiciousActivity
            withContext(Dispatchers.Main) {
                activity?.onSuspiciousActivityLoaded(suspiciousActivity)
            }
        }
    }

    private fun loadSpinner() {
        var locations: ArrayList<String> = ArrayList()
        for (location in LocationEnumarator.values()) {
            locations.add(location.location)
        }
        activity?.onLocationsLoaded(locations)
    }

    fun onActivitiesOrBehaviorsLoaded() {
        if (++activitiesAndBehaviorsLoaded == 2) {
            val behaviour =
                behaviors.find { behavior -> behavior.ID == loadedActivity?.behaviorID } ?: return
            activity?.onActivitiesAndBehaviorsLoaded(behaviors.indexOf(behaviour))
        }
    }

    fun getBehaviorId(position: Int): Long {
        return behaviors[position].ID
    }
}