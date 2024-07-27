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

class AppController : Application() {
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
                coin = Utils.BASE_COIN
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
        /*       dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 6,
                       titleLevel = 6,
                       isLockLevel = true,
                       question = "آن چیست که گرداگرد درخت و چوب است اما هیچگاه به داخل درخت و چوب نمی رود؟",
                       answer = "پوست درخت",
                       sizeAnswer = 8,
                       listAnswer = arrayListOf(
                           'پ',
                           'و',
                           'س',
                           'ت',
                           'د',
                           'ر',
                           'خ',
                           'ت',
                       ),
                       letters = arrayListOf('چ', 'خ', 'س', 'ر', 'د', 'د', 'ت', 'ف', 'و', 'پ', 'س', 'ت')
                   )
               )

               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 7,
                       titleLevel = 7,
                       isLockLevel = true,
                       question = "این چیه که مورچه داره ، اما مار نداره ؟",
                       answer = "نقطه",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'ن',
                           'ق',
                           'ط',
                           'ه',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'ق', 'پ', 'ن', 'ف', 'و', 'ط', 'آ', 'ش')
                   )
               )

               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 8,
                       titleLevel = 8,
                       isLockLevel = true,
                       question = "همراه چای است و همرنگ برف، هم طعم عسل است و هم بوی آب!",
                       answer = "قند",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'ق',
                           'ن',
                           'د',
                       ),
                       letters = arrayListOf('ژ', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'ق', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 9,
                       titleLevel = 9,
                       isLockLevel = true,
                       question = "کدام رازداری است که تا سرش را نبریم رازش را نمی گوید؟",
                       answer = "نامه",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'ن',
                           'ا',
                           'م',
                           'ه',
                           ),
                       letters = arrayListOf('ه', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'ا', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 10,
                       titleLevel = 10,
                       isLockLevel = true,
                       question = "کدام کلید است که هیچ دری را باز نمی کند؟",
                       answer = "کلید برق",
                       sizeAnswer = 7,
                       listAnswer = arrayListOf(
                           'ک',
                           'ل',
                           'ی',
                           'د',
                           'ب',
                           'ر',
                           'ق',
                       ),
                       letters = arrayListOf('چ', 'ب', 'ی', 'ه', 'د', 'ل', 'ن', 'ف', 'ر', 'ک', 'آ', 'ق')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 11,
                       titleLevel = 11,
                       isLockLevel = true,
                       question = "آن چیست که جان دارد و نفس نمی کشد؟",
                       answer = "فنجان",
                       sizeAnswer = 5,
                       listAnswer = arrayListOf(
                           'ف',
                           'ن',
                           'ج',
                           'ا',
                           'ن',
                       ),
                       letters = arrayListOf('ج', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ن', 'ا', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 12,
                       titleLevel = 12,
                       isLockLevel = true,
                       question = "کدام خانه بیشترین کتاب ها را دارد؟",
                       answer = "کتابخانه",
                       sizeAnswer = 8,
                       listAnswer = arrayListOf(
                           'ک',
                           'ت',
                           'ا',
                           'ب',
                           'خ',
                           'ا',
                           'ن',
                           'ه',
                       ),
                       letters = arrayListOf('خ', 'م', 'س', 'ه', 'ا', 'پ', 'ن', 'خ', 'ک', 'ت', 'ا', 'ب')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 13,
                       titleLevel = 13,
                       isLockLevel = true,
                       question = "آن چیست که پر دارد و پرواز نمی کند؟",
                       answer = "پرگار",
                       sizeAnswer = 5,
                       listAnswer = arrayListOf(
                           'پ',
                           'ر',
                           'گ',
                           'ا',
                           'ر',
                       ),
                       letters = arrayListOf('چ', 'م', 'ر', 'ه', 'د', 'پ', 'ن', 'پ', 'ر', 'ش', 'ا', 'گ')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 14,
                       titleLevel = 14,
                       isLockLevel = true,
                       question = "روی آن پوست سبز دارد. زیر پوست سبز پوست سفید است. در داخل پوست سفید قرمزی وجود دارد. در داخل خانه قرمز تعداد زيادی از نوزادان وجود دارد. آن چیست؟",
                       answer = "هندوانه",
                       sizeAnswer = 7,
                       listAnswer = arrayListOf(
                           'ه',
                           'ن',
                           'د',
                           'و',
                           'ا',
                           'ن',
                           'ه',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'د', 'ه', 'ن', 'ف', 'و', 'ش', 'ا', 'ن')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 15,
                       titleLevel = 15,
                       isLockLevel = true,
                       question = "آن چیست که در فرانسه، کره جنوبی، کره شمالی، عربستان، عراق و ترکمنستان دوم شده است اما در روسیه اول؟",
                       answer = "حرف ر",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'ح',
                           'ر',
                           'ف',
                           'ر',
                       ),
                       letters = arrayListOf('ر', 'م', 'ح', 'ه', 'د', 'پ', 'ر', 'ف', 'و', 'ش', 'آ', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 16,
                       titleLevel = 16,
                       isLockLevel = true,
                       question = "چیزی است که هم در آشپزخانه وجود دارد هم اتومبیل!",
                       answer = "گاز",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'گ',
                           'ا',
                           'ز',
                       ),
                       letters = arrayListOf('گ', 'م', 'س', 'ه', 'ز', 'پ', 'ن', 'ف', 'و', 'ش', 'ا', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 17,
                       titleLevel = 17,
                       isLockLevel = true,
                       question = "برگ سبز چمنی، ورق ورق تو می شکنی؟",
                       answer = "کاهو",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'ک',
                           'ا',
                           'ه',
                           'و',
                       ),
                       letters = arrayListOf('ک', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'ا', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 18,
                       titleLevel = 18,
                       isLockLevel = true,
                       question = "در گوش و چشم هست ولی در دهان نیست؟",
                       answer = "حرف ش",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'ح',
                           'ر',
                           'ف',
                           'ش',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'د', 'پ', 'ر', 'ف', 'و', 'ش', 'ح', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 19,
                       titleLevel = 19,
                       isLockLevel = true,
                       question = "یک چشم دارد اما نمی بیند!",
                       answer = "سوزن",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'س',
                           'و',
                           'ز',
                           'ن',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ز', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'آ', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 20,
                       titleLevel = 20,
                       isLockLevel = true,
                       question = "آن چیست که در قصابی بسیار یافت می شود",
                       answer = "گوشت",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'گ',
                           'و',
                           'ش',
                           'ت',
                       ),
                       letters = arrayListOf('ت', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'گ', 'آ', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 21,
                       titleLevel = 21,
                       isLockLevel = true,
                       question = "آن چیست که روز را کنار پنجره می گذراند، و هنگام غذا کنار میز است و شب ها ناپدید می شود؟",
                       answer = "مگس",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'م',
                           'گ',
                           'س',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'د', 'پ', 'گ', 'ف', 'و', 'ش', 'آ', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 22,
                       titleLevel = 22,
                       isLockLevel = true,
                       question = "با وجود داشتن چهار پا، اما نمی تواند حرکت کند! آن چیست؟",
                       answer = "چهار پایه",
                       sizeAnswer = 8,
                       listAnswer = arrayListOf(
                           'چ',
                           'ه',
                           'ا',
                           'ر',
                           'پ',
                           'ا',
                           'ی',
                           'ه',
                       ),
                       letters = arrayListOf('چ', 'ی', 'س', 'ه', 'د', 'پ', 'ر', 'ا', 'و', 'ش', 'ا', 'ه')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 23,
                       titleLevel = 23,
                       isLockLevel = true,
                       question = "آن چیست که خیس می شود تا شما خشک بشوید",
                       answer = "حوله",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'ح',
                           'و',
                           'ل',
                           'ه',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'ل', 'پ', 'ن', 'ح', 'و', 'ه', 'آ', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 24,
                       titleLevel = 24,
                       isLockLevel = true,
                       question = "آن چیست که اگر غذا به آن بدهی زنده است و اگر آب به آن بدهی میمیرد؟",
                       answer = "آتش",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'آ',
                           'ت',
                           'ش',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'د', 'ت', 'ن', 'ف', 'و', 'ش', 'آ', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 25,
                       titleLevel = 25,
                       isLockLevel = true,
                       question = "کدام حیوان درنده را اگر وارونه کنید بر روی مردان می روید؟",
                       answer = "شیر",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'ش',
                           'ی',
                           'ر',
                       ),
                       letters = arrayListOf('چ', 'م', 'ی', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'آ', 'ر')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 26,
                       titleLevel = 26,
                       isLockLevel = true,
                       question = "کدام جانور است که سرگرگ و کمر مار و دم آهو را دارد؟",
                       answer = "گاو",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'گ',
                           'ا',
                           'و',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'ا', 'گ')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 27,
                       titleLevel = 27,
                       isLockLevel = true,
                       question = "آن چیست که سر دارد اما بدن ندارد؟",
                       answer = "سکه",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'س',
                           'ک',
                           'ه',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'د', 'ک', 'ن', 'ف', 'و', 'ش', 'آ', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 28,
                       titleLevel = 28,
                       isLockLevel = true,
                       question = "بدون شک پاییز با آن به اتمام می رسد و زمستان با آن آغاز می شود.",
                       answer = "حرف ز",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'ح',
                           'ر',
                           'ف',
                           'ز',
                       ),
                       letters = arrayListOf('ز', 'م', 'س', 'ر', 'د', 'پ', 'ن', 'ف', 'و', 'ح', 'آ', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 29,
                       titleLevel = 29,
                       isLockLevel = true,
                       question = "آن چیست که پر و خالی اش یک وزن دارد؟",
                       answer = "نوار کاست",
                       sizeAnswer = 8,
                       listAnswer = arrayListOf(
                           'ن',
                           'و',
                           'ا',
                           'ر',
                           'ک',
                           'ا',
                           'س',
                           'ت',
                       ),
                       letters = arrayListOf('ت', 'م', 'س', 'ه', 'ر', 'ا', 'ن', 'ک', 'و', 'ش', 'ا', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 30,
                       titleLevel = 30,
                       isLockLevel = true,
                       question = "کدام حیوانی است که وارونه اش در حمام وجود دارد؟",
                       answer = "فیل",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'ف',
                           'ی',
                           'ل',
                       ),
                       letters = arrayListOf('چ', 'م', 'ی', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'آ', 'ل')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 31,
                       titleLevel = 31,
                       isLockLevel = true,
                       question = "پرنده ای است که حتی اگر وارونه اش هم کنید باز پرنده است.",
                       answer = "زاغ",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'ز',
                           'ا',
                           'غ',
                       ),
                       letters = arrayListOf('چ', 'غ', 'س', 'ز', 'د', 'پ', 'ن', 'ا', 'و', 'ش', 'آ', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 32,
                       titleLevel = 32,
                       isLockLevel = true,
                       question = "ورزشکاران برای شکستنش جایزه می گیرند!",
                       answer = "رکورد",
                       sizeAnswer = 5,
                       listAnswer = arrayListOf(
                           'ر',
                           'ک',
                           'و',
                           'ر',
                           'د',
                       ),
                       letters = arrayListOf('ر', 'م', 'س', 'ر', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'آ', 'ک')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 33,
                       titleLevel = 33,
                       isLockLevel = true,
                       question = "آن کدام کشور است که منبع ویتامین ث می شد؟",
                       answer = "پرتغال",
                       sizeAnswer = 6,
                       listAnswer = arrayListOf(
                           'پ',
                           'ر',
                           'ت',
                           'غ',
                           'ا',
                           'ل',
                       ),
                       letters = arrayListOf('غ', 'م', 'س', 'ر', 'د', 'پ', 'ن', 'ت', 'و', 'ش', 'ا', 'ل')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 34,
                       titleLevel = 34,
                       isLockLevel = true,
                       question = "کدام شهر ایران است، که خوردن نیمه اولش کشنده ولی نیمه دومش را هر روز می خورند؟",
                       answer = "سمنان",
                       sizeAnswer = 5,
                       listAnswer = arrayListOf(
                           'س',
                           'م',
                           'ن',
                           'ا',
                           'ن',
                       ),
                       letters = arrayListOf('ن', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'ا', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 35,
                       titleLevel = 35,
                       isLockLevel = true,
                       question = "اون چیه که سفیده، برف نیست، ریشه داره، درخت نیست؟",
                       answer = "دندان",
                       sizeAnswer = 5,
                       listAnswer = arrayListOf(
                           'د',
                           'ن',
                           'د',
                           'ا',
                           'ن',
                       ),
                       letters = arrayListOf('چ', 'م', 'د', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'ا', 'ن')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 36,
                       titleLevel = 36,
                       isLockLevel = true,
                       question = "نام کدام جزیره است که اگر وارونه اش کنیم، بچه ها می نویسند؟",
                       answer = "قشم",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'ق',
                           'ش',
                           'م',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'آ', 'ق')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 37,
                       titleLevel = 37,
                       isLockLevel = true,
                       question = "آن چیست که هم بچه دارد و هم تفنگ؟",
                       answer = "قنداق",
                       sizeAnswer = 5,
                       listAnswer = arrayListOf(
                           'ق',
                           'ن',
                           'د',
                           'ا',
                           'ق',
                       ),
                       letters = arrayListOf('چ', 'م', 'ق', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'ا', 'ق')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 38,
                       titleLevel = 38,
                       isLockLevel = true,
                       question = "آن چیست که صدا دارد اما جثه ندارد؟",
                       answer = "باد",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'ب',
                           'ا',
                           'د',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ب', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'ا', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 39,
                       titleLevel = 39,
                       isLockLevel = true,
                       question = "آن چیست که شیرین است و طعم ندارد، سنگین است و وزن ندارد؟",
                       answer = "خواب",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'خ',
                           'و',
                           'ا',
                           'ب',
                       ),
                       letters = arrayListOf('خ', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'ا', 'ب')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 40,
                       titleLevel = 40,
                       isLockLevel = true,
                       question = "وقتی از قطب، دشت کویر و لب ساحل برمیگردم او می ماند.",
                       answer = "جای پا",
                       sizeAnswer = 5,
                       listAnswer = arrayListOf(
                           'ج',
                           'ا',
                           'ی',
                           'پ',
                           'ا',
                       ),
                       letters = arrayListOf('چ', 'م', 'ی', 'ه', 'د', 'پ', 'ا', 'ف', 'و', 'ش', 'ا', 'ج')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 41,
                       titleLevel = 41,
                       isLockLevel = true,
                       question = "من یکی دارم، تو دو تا داری، شاه سه تا و پسر شاه نه تا!",
                       answer = "نقطه",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'ن',
                           'ق',
                           'ط',
                           'ه',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ق', 'و', 'ش', 'آ', 'ط')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 42,
                       titleLevel = 42,
                       isLockLevel = true,
                       question = "دو برادر هستند که همیشه خاک بر سر هم می کنند.",
                       answer = "بیل و کلنگ",
                       sizeAnswer = 8,
                       listAnswer = arrayListOf(
                           'ب',
                           'ی',
                           'ل',
                           'و',
                           'ک',
                           'ل',
                           'ن',
                           'گ',
                       ),
                       letters = arrayListOf('ب', 'ی', 'س', 'ل', 'د', 'پ', 'ن', 'ل', 'و', 'ش', 'گ', 'ک')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 43,
                       titleLevel = 43,
                       isLockLevel = true,
                       question = "پای کوه نشسته است و عمامه به سر دارد!",
                       answer = "قارچ",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'ق',
                           'ا',
                           'ر',
                           'چ',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'ر', 'پ', 'ن', 'ف', 'و', 'ش', 'ا', 'ق')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 44,
                       titleLevel = 44,
                       isLockLevel = true,
                       question = "روزها می دود ولی شب ها، جلوی درب خانه نگهبان است!",
                       answer = "کفش",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'ک',
                           'ف',
                           'ش',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'ک', 'ش')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 45,
                       titleLevel = 45,
                       isLockLevel = true,
                       question = "عضوی از بدن که می توانید آن را به کسی که دوست دارید بدهید. اما شکسته شدن آن دردناک است و جبران ناپذیر. آن چیست؟",
                       answer = "قلب",
                       sizeAnswer = 3,
                       listAnswer = arrayListOf(
                           'ق',
                           'ل',
                           'ب',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'ل', 'ب', 'ن', 'ف', 'و', 'ش', 'آ', 'ق')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 46,
                       titleLevel = 46,
                       isLockLevel = true,
                       question = "می توانم سریع رشد کنم و منبع خوشبختی یا منبع غم و اندوه شما باشم، می توانم آرام از بین بروم یا به سرانجام برسم. من چی هستم؟",
                       answer = "رابطه",
                       sizeAnswer = 5,
                       listAnswer = arrayListOf(
                           'ر',
                           'ا',
                           'ب',
                           'ط',
                           'ه',
                       ),
                       letters = arrayListOf('ط', 'م', 'س', 'ه', 'ر', 'ب', 'ن', 'ف', 'و', 'ش', 'ا', 'چ')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 47,
                       titleLevel = 47,
                       isLockLevel = true,
                       question = "چه راهی برای دو برابر کردن پول هست؟",
                       answer = "آیینه",
                       sizeAnswer = 5,
                       listAnswer = arrayListOf(
                           'آ',
                           'ی',
                           'ی',
                           'ن',
                           'ه',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'ن', 'پ', 'ی', 'ف', 'و', 'ش', 'آ', 'ی')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 48,
                       titleLevel = 48,
                       isLockLevel = true,
                       question = "یک شئ براق، زیبا و درخشان وهوس انگیز و گران قیمت است و همسر شما می خواهد آن را به شما هدیه بدهد. چه چیزی است؟",
                       answer = "جواهر",
                       sizeAnswer = 5,
                       listAnswer = arrayListOf(
                           'ج',
                           'و',
                           'ا',
                           'ه',
                           'ر',
                       ),
                       letters = arrayListOf('چ', 'ر', 'س', 'ه', 'د', 'پ', 'ن', 'ف', 'و', 'ش', 'ا', 'ج')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 49,
                       titleLevel = 49,
                       isLockLevel = true,
                       question = "آن کدام حیوان آبزی است که قسمت دوم نامش نام یک ساز است؟",
                       answer = "خرچنگ",
                       sizeAnswer = 5,
                       listAnswer = arrayListOf(
                           'خ',
                           'ر',
                           'چ',
                           'ن',
                           'گ',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ه', 'ر', 'پ', 'ن', 'ف', 'و', 'ش', 'خ', 'گ')
                   )
               )
               dataBase.levels().saveDataLevel(
                   LevelModel(
                       id = 50,
                       titleLevel = 50,
                       isLockLevel = true,
                       question = "چیست که مال توست، ولی بیشتر بقیه بکار میبرندش؟",
                       answer = "اسمت",
                       sizeAnswer = 4,
                       listAnswer = arrayListOf(
                           'ا',
                           'س',
                           'م',
                           'ت',
                       ),
                       letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
                   )
               )
       */
    }

    private fun insertDataToDbSetting() {
        dataBase = AppDataBase.getDatabase(this)
        dataBase.setting().saveDataSetting(SettingModel(1, playMusic = true, playVolume = true))
    }

}