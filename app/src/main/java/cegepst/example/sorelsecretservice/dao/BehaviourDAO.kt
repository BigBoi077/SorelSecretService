package cegepst.example.sorelsecretservice.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cegepst.example.sorelsecretservice.models.Behaviour

interface BehaviourDAO {
    @Query("SELECT * FROM behaviors")
    fun getAll(): List<Behaviour>

    @Query("SELECT * FROM behaviors WHERE id=:id")
    fun get(id: Long): Behaviour

    @Insert
    fun insert(behaviour: Behaviour): Long

    @Delete
    fun delete(behaviour: Behaviour)
}