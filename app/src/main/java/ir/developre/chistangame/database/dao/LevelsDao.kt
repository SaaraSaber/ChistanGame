package ir.developre.chistangame.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.developre.chistangame.model.LevelModel

@Dao
interface LevelsDao {
    @Query("SELECT * FROM levels")
    fun readDataLevel(): List<LevelModel>

    @Insert
    fun saveDataLevel(level: LevelModel): Long

    @Update
    fun updateDataLevel(level: LevelModel): Int
}