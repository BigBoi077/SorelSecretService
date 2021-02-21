package cegepst.example.sorelsecretservice.controllers

import cegepst.example.sorelsecretservice.models.ActivityWithBehavior
import cegepst.example.sorelsecretservice.stores.AppStore
import cegepst.example.sorelsecretservice.views.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainController(mainActivity: MainActivity) {

    private var suspiciousActivities = ArrayList<ActivityWithBehavior>()
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
    }

    private fun loadActivities() {
        GlobalScope.launch {
            suspiciousActivities = ArrayList(database.suspiciousActivitiesDAO().getAllWithBehaviors())
            withContext(Dispatchers.Main) {
                activity?.onSuspiciousActivitiesUpdated()
            }
        }
    }

    fun getAdapterContent(position: Int, callback: (Long, String, Int) -> Unit) {
        val suspiciousActivity = suspiciousActivities[position].activity
        val behavior = suspiciousActivities[position].behaviour
        callback(
                behavior.ID,
                suspiciousActivity.location,
                suspiciousActivity.trustLevel
        )
    }

    fun addSuspiciousActivity(suspiciousActivity: ActivityWithBehavior) {
        GlobalScope.launch {
            val id = database.suspiciousActivitiesDAO().insert(suspiciousActivity)
            suspiciousActivity.activity.ID = id
            suspiciousActivities.add(database.suspiciousActivitiesDAO().getWithBehavior(id))
            withContext(Dispatchers.Main) {
                activity?.onSuspiciousActivitiesUpdated()
            }
        }
        activity?.onSuspiciousActivitiesAdded(activitiesSize - 1)
    }

    fun modifySuspiciousActivity(activityWithBehavior: ActivityWithBehavior) {
        val suspiciousActivity = suspiciousActivities.find { activityWithBehavior ->
            activityWithBehavior.activity.ID == activityWithBehavior.activity.ID
        } ?: return
        val currentActivity = suspiciousActivity.activity

        currentActivity.createdDate = getCurrentDate()
        currentActivity.location = activityWithBehavior.activity.location
        currentActivity.trustLevel = activityWithBehavior.activity.trustLevel
        currentActivity.behaviorID = activityWithBehavior.behaviour.ID

        GlobalScope.launch {
            database.suspiciousActivitiesDAO().update(currentActivity)
            val updatedActicityWithBehavior = database.suspiciousActivitiesDAO().getWithBehavior(activityWithBehavior.activity.ID)
            suspiciousActivities[suspiciousActivities.indexOf(suspiciousActivity)] = updatedActicityWithBehavior
            withContext(Dispatchers.Main) {
                activity?.onSuspiciousActivitiesUpdated()
            }
        }
    }

    private fun getCurrentDate(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)
        return formatted
    }

    fun modifySuspiciousActivity(position: Int) {
        activity?.onModifySuspiciousActivity(suspiciousActivities[position].activity.ID)
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