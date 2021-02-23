package cegepst.example.sorelsecretservice.controllers

import cegepst.example.sorelsecretservice.models.Behaviour
import cegepst.example.sorelsecretservice.stores.AppStore
import cegepst.example.sorelsecretservice.views.BehaviorsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import java.util.*

class BehaviorsController(behaviorsActivity: BehaviorsActivity) {

    private var behaviors = ArrayList<Behaviour>()
    private var weakReference = WeakReference(behaviorsActivity)
    private val database = AppStore(behaviorsActivity)

    val activity: BehaviorsActivity?
        get() {
            return weakReference.get()
        }

    val behaviorsSize: Int
        get() {
            return behaviors.size
        }

    init {
        loadBehaviors()
    }

    private fun loadBehaviors() {
        GlobalScope.launch {
            behaviors = ArrayList(database.behaviorsDAO().getAll())
            withContext(Dispatchers.Main) {
                activity?.onBehaviorsUpdated()
            }
        }
    }

    fun getAdapterContent(position: Int, callback: (String, String) -> Unit) {
        val behavior = behaviors[position]
        callback(behavior.name, behavior.description)
    }

    fun deleteBehavior(position: Int) {
        val behavior = behaviors[position]
        GlobalScope.launch {
            database.behaviorsDAO().delete(behavior)
            behaviors.removeAt(position)
            withContext(Dispatchers.Main) {
                activity?.onBehaviorsUpdated()
            }
        }
    }

    fun addBehavior(name: String, description: String) {
        val behavior = Behaviour(name, description)
        GlobalScope.launch {
            val id = database.behaviorsDAO().insert(behavior)
            behavior.ID = id
            behaviors.add(database.behaviorsDAO().get(id))
            withContext(Dispatchers.Main) {
                activity?.onBehaviorsUpdated()
            }
        }
    }
}