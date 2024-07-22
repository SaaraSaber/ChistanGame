package ir.developre.chistangame.global

import android.app.Application
import android.content.Context
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.model.LevelModel
import ir.developre.chistangame.model.SettingModel
import ir.developre.chistangame.model.UserModel
import ir.developre.chistangame.sharedPref.SharedPreferencesGame

class AppController: Application() {
    private lateinit var dataBase: AppDataBase

    override fun onCreate() {
        super.onCreate()

        //font
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/lalezar_regular.ttf")
                            .build()
                    )
                )
                .build()
        )

        if (!checkEnterToAppForFirst()) {
            saveEnterToAppForFirst()
        }

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    private lateinit var sharedPreferencesGame: SharedPreferencesGame

    private fun saveEnterToAppForFirst() {
        sharedPreferencesGame = SharedPreferencesGame(this)
        sharedPreferencesGame.saveStatusFirst(true)

        insertDataToDbSetting()
        insertDataToDbLevels()
        insertDataToDbUser()

    }

    private fun checkEnterToAppForFirst(): Boolean {
        sharedPreferencesGame = SharedPreferencesGame(this)
        val result = sharedPreferencesGame.readStatusFirst()
        return result
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

}