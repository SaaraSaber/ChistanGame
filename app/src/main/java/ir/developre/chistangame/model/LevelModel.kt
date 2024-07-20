package ir.developre.chistangame.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "levels")
@TypeConverters(Converters::class)
data class LevelModel(
    @PrimaryKey
    val id: Int,
    val titleLevel: Int,
    val isLockLevel: Boolean,
    val question: String,
    val answer: String,
    val sizeAnswer: Int,
    val listAnswer: ArrayList<Char>,
    val letters: ArrayList<Char>
)

class Converters {
    @TypeConverter
    fun fromCharList(charList: ArrayList<Char>): String {
        return charList.joinToString(separator = ",")
    }

    @TypeConverter
    fun toCharList(charListString: String): ArrayList<Char> {
        return ArrayList(charListString.split(",").map { it[0] })
    }
}
