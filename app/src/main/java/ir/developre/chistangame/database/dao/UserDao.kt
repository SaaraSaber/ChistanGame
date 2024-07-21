package ir.developre.chistangame.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.developre.chistangame.model.UserModel

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun readDataUser(): UserModel

    @Insert
    fun saveDataUser(user: UserModel): Long

    @Update
    fun updateDataUser(user: UserModel): Int
}