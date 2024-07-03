package ir.developre.chistangame.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "setting")
data class SettingModel(
    @PrimaryKey
    val id: Int,
    val playMusic: Boolean,
    val playVolume: Boolean
)
