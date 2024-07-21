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
import ir.developre.chistangame.model.UserModel
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

        if (!checkEnterToAppForFirst()) {
            saveEnterToAppForFirst()
        }


        window.navigationBarColor = ContextCompat.getColor(this, R.color.Watery)
        window.statusBarColor = ContextCompat.getColor(this, R.color.friendlyFrost)

        navController = findNavController(R.id.my_nav_host_fragment)
    }

    private lateinit var sharedPreferencesGame: SharedPreferencesGame
    private fun saveEnterToAppForFirst() {
        sharedPreferencesGame = SharedPreferencesGame(this)
        sharedPreferencesGame.saveStatusFirst(true)

        insertDataToDbSetting()
        insertDataToDbLevels()
        insertDataToDbUser()

    }

    private fun insertDataToDbUser() {
        dataBase = AppDataBase.getDatabase(this)
        dataBase.user().saveDataUser(
            UserModel(
                id = 1,
                coin = 50
            )
        )
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
                listAnswer = arrayListOf('ا', 'ن', 'س', 'ا', 'ن'),
                letters = arrayListOf('ا', 'ح', 'ن', 'گ', 'ص', 'ن', 'ت', 'س', 'د', 'ا', 'ش', 'ث')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 2,
                titleLevel = 2,
                isLockLevel = true,
                question = "آن چیست که پر از سوراخ است اما هنوز آب را نگه می دارد؟",
                answer = "اسفنج",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ا',
                    'س',
                    'ف',
                    'ن',
                    'ج',
                ),
                letters = arrayListOf('ل', 'ی', 'ب', 'ف', 'خ', 'ن', 'ا', 'ب', 'پ', 'س', 'چ', 'ج')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 3,
                titleLevel = 3,
                isLockLevel = true,
                question = "من در جوانی قد بلندم و در پیری کوتاه قد هستم. من چی هستم؟",
                answer = "شمع",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ش',
                    'م',
                    'ع',
                ),
                letters = arrayListOf('ه', 'ب', 'ر', 'گ', 'ی', 'پ', 'د', 'م', 'ش', 'ج', 'آ', 'ع')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 4,
                titleLevel = 4,
                isLockLevel = true,
                question = "مامان کیت سه فرزند دارد ، اسنپ، کراکر و ...؟ ",
                answer = "کیت",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ک',
                    'ی',
                    'ت',
                ),
                letters = arrayListOf('ی', 'م', 'د', 'آ', 'ت', 'پ', 'ن', 'ک', 'س', 'ا', 'ح', 'ک')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 5,
                titleLevel = 5,
                isLockLevel = true,
                question = "فکر کن که داری مسابقه دو میدی، در لحظه آخر از نفر دوم جلو میزنی الان نفر چندمی؟",
                answer = "دوم",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'د',
                    'و',
                    'م',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'آ', 'ش')
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