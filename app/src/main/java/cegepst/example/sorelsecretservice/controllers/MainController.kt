package cegepst.example.sorelsecretservice.controllers

import cegepst.example.sorelsecretservice.models.Behaviour
import cegepst.example.sorelsecretservice.models.SuspiciousActivity
import cegepst.example.sorelsecretservice.stores.AppStore
import cegepst.example.sorelsecretservice.views.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

class MainController(mainActivity: MainActivity) {

    private var suspiciousActivities = ArrayList<SuspiciousActivity>()
    private var behaviours = ArrayList<Behaviour>()
    private val weakRef = WeakReference(mainActivity)
    private val database = AppStore(mainActivity)

    private val activity: MainActivity?
        get() {
            return weakRef.get()
        }

    val activitiesSize: Int
        get() {
            return suspiciousActivities.size
        }

    init {
        loadActivities()
        loadBehaviors()
    }

    private fun loadActivities() {
        GlobalScope.launch {
            suspiciousActivities = ArrayList(database.suspiciousActivitiesDAO().getAll())
            withContext(Dispatchers.Main) {
                activity?.onSuspiciousActivitiesUpdated()
            }
        }
    }

    private fun loadBehaviors() {
        GlobalScope.launch {
            behaviours = ArrayList(database.behaviorsDAO().getAll())
        }
    }

    fun getAdapterContent(position: Int, callback: (Long, String, Int) -> Unit) {
        val suspiciousActivity = suspiciousActivities[position]
        callback(
            suspiciousActivity.behaviorID,
            suspiciousActivity.location,
            suspiciousActivity.trustLevel
        )
    }

    fun addSuspiciousActivity(suspiciousActivity: SuspiciousActivity) {
        GlobalScope.launch {
            val id = database.suspiciousActivitiesDAO().insert(suspiciousActivity)
            suspiciousActivity.ID = id
            suspiciousActivities.add(suspiciousActivity)
            withContext(Dispatchers.Main) {
                activity?.onSuspiciousActivitiesUpdated()
            }
        }
        activity?.onSuspiciousActivitiesAdded(activitiesSize - 1)
    }

    fun modifySuspiciousActivity(suspiciousActivity: SuspiciousActivity) {
        GlobalScope.launch {
            database.suspiciousActivitiesDAO().update(suspiciousActivity)
            withContext(Dispatchers.Main) {
                activity?.onSuspiciousActivitiesUpdated()
            }
        }
    }

    fun deleteSuspiciousActivity(position: Int) {
        val suspiciousActivity = suspiciousActivities[position]
        GlobalScope.launch {
            database.suspiciousActivitiesDAO().delete(suspiciousActivity)
            suspiciousActivities.removeAt(position)
            withContext(Dispatchers.Main) {
                activity?.onSuspiciousActivitiesUpdated()
            }
        }
    }
}