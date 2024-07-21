package ir.developre.chistangame.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey
    val id: Int,
    val firstName: String? = null,
    val lastName: String? = null,
    val coin: Int
)
