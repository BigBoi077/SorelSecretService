package cegepst.example.sorelsecretservice.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cegepst.example.sorelsecretservice.models.Behaviour

@Dao
interface BehaviourDAO {

    @Query("SELECT * FROM behavior")
    fun getAll(): List<Behaviour>

    @Query("SELECT * FROM behavior WHERE ID=:id")
    fun get(id: Long): Behaviour

    @Insert
    fun insert(behaviour: Behaviour): Long

    @Delete
    fun delete(behaviour: Behaviour)
}