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
        insertDbSetting()
    }

    private fun insertDbSetting() {
        dataBase = AppDataBase.getDatabase(this)
        dataBase.setting().saveDataSetting(SettingModel(1, true, true))
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