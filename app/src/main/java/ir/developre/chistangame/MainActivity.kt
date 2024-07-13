package ir.developre.chistangame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.ActivityMainBinding
import ir.developre.chistangame.global.PlayMusicService
import ir.developre.chistangame.model.LevelModel
import ir.developre.chistangame.model.SettingModel
import ir.developre.chistangame.sharedPref.SharedPreferencesGame

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var dataBase: AppDataBase

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        navController = findNavController(R.id.my_nav_host_fragment)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.Watery)
        window.statusBarColor = ContextCompat.getColor(this, R.color.friendlyFrost)

        if (!checkEnterToAppForFirst()) {
            saveEnterToAppForFirst()
        }

    }

    private lateinit var sharedPreferencesGame: SharedPreferencesGame
    private fun saveEnterToAppForFirst() {
        sharedPreferencesGame = SharedPreferencesGame(this)
        sharedPreferencesGame.saveStatusFirst(true)

        insertDataToDbSetting()
        insertDataToDbLevels()

    }

    private fun insertDataToDbLevels() {
        dataBase = AppDataBase.getDatabase(this)
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 1,
                titleLevel = 1,
                isLockLevel = false,
                question = "آن کیست که در ابتدا چهار پا دارد سپس دو پا و در نهایت سه پا",
                answer = "انسان",
                sizeAnswer = 5,
                letters = arrayListOf('ا', 'ح', 'ن', 'گ', 'ص', 'ن', 'ت', 'س', 'د', 'ا', 'ش', 'ث')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 2,
                titleLevel = 2,
                isLockLevel = true,
                question = "Sample question?",
                answer = "انسان",
                sizeAnswer = 5,
                letters = arrayListOf('a', 'b', 'c', 'd', 'e', 'a', 'b', 'c', 'd', 'e', 'd', 'e')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 3,
                titleLevel = 3,
                isLockLevel = true,
                question = "Sample question?",
                answer = "انسان",
                sizeAnswer = 5,
                letters = arrayListOf('a', 'b', 'c', 'd', 'e', 'a', 'b', 'c', 'd', 'e', 'd', 'e')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 4,
                titleLevel = 4,
                isLockLevel = true,
                question = "Sample question?",
                answer = "انسان",
                sizeAnswer = 5,
                letters = arrayListOf('a', 'b', 'c', 'd', 'e', 'a', 'b', 'c', 'd', 'e', 'd', 'e')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 5,
                titleLevel = 5,
                isLockLevel = true,
                question = "Sample question?",
                answer = "انسان",
                sizeAnswer = 5,
                letters = arrayListOf('a', 'b', 'c', 'd', 'e', 'a', 'b', 'c', 'd', 'e', 'd', 'e')
            )
        )
    }

    private fun insertDataToDbSetting() {
        dataBase = AppDataBase.getDatabase(this)
        dataBase.setting().saveDataSetting(SettingModel(1, playMusic = true, playVolume = true))
    }

    private fun checkEnterToAppForFirst(): Boolean {
        sharedPreferencesGame = SharedPreferencesGame(this)
        val result = sharedPreferencesGame.readStatusFirst()
        return result
    }

    override fun onStop() {
        super.onStop()

        dataBase = AppDataBase.getDatabase(this)
        val readSetting = dataBase.setting().readDataSetting()

        if (readSetting.playMusic) {
            stopService(
                Intent(
                    this,
                    PlayMusicService::class.java
                )
            )
        }

    }

    override fun onResume() {
        super.onResume()

        dataBase = AppDataBase.getDatabase(this)
        val readSetting = dataBase.setting().readDataSetting()

        if (readSetting == null || readSetting.playMusic) {

            startService(
                Intent(
                    this,
                    PlayMusicService::class.java
                )
            )
        }

    }
}