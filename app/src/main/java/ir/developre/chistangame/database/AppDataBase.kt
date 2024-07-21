package ir.developre.chistangame.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.developre.chistangame.database.dao.LevelsDao
import ir.developre.chistangame.database.dao.SettingDao
import ir.developre.chistangame.database.dao.UserDao
import ir.developre.chistangame.model.LevelModel
import ir.developre.chistangame.model.SettingModel
import ir.developre.chistangame.model.UserModel

@Database(
    entities = [
        SettingModel::class, LevelModel::class, UserModel::class],
    version = 3,
    exportSchema = true
)

abstract class AppDataBase : RoomDatabase() {

    abstract fun setting(): SettingDao
    abstract fun levels(): LevelsDao
    abstract fun user(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "game_database"
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}