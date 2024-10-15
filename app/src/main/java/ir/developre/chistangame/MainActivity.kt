package ir.developre.chistangame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import androidx.navigation.NavController
import androidx.navigation.findNavController
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.ActivityMainBinding
import ir.developre.chistangame.global.PlayMusicService

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
        setContentView(binding.root)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.Watery)
        window.statusBarColor = ContextCompat.getColor(this, R.color.friendlyFrost)

        navController = findNavController(R.id.my_nav_host_fragment)
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