package ir.developre.chistangame.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.developre.chistangame.model.SettingModel

@Dao
interface SettingDao {
    @Query("SELECT * FROM setting")
    fun readDataSetting(): SettingModel

    @Insert
    fun saveDataSetting(settingModel: SettingModel): Long

    @Update
    fun updateDataSetting(settingModel: SettingModel): Int
}