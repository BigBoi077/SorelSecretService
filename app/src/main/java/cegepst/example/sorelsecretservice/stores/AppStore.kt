package cegepst.example.sorelsecretservice.stores

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cegepst.example.sorelsecretservice.dao.BehaviourDAO
import cegepst.example.sorelsecretservice.dao.SuspiciousActivityDAO
import cegepst.example.sorelsecretservice.models.Behaviour
import cegepst.example.sorelsecretservice.models.SuspiciousActivity

@Database(entities = [SuspiciousActivity::class, Behaviour::class], version = 1)
abstract class AppStore : RoomDatabase() {

    abstract fun suspiciousActivitiesDAO(): SuspiciousActivityDAO
    abstract fun behaviorsDAO(): BehaviourDAO

    companion object {
        @Volatile
        private var instance: AppStore? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppStore::class.java,
            "Sorel-Secret-Service-5"
        )
            .build()
    }
}