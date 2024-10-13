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
//        sharedPreferencesGame = SharedPreferencesGame(this)
//        sharedPreferencesGame.saveStatusFirst(true)

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
        dataBase.levels().saveDataLevel(
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
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 51,
                titleLevel = 51,
                isLockLevel = true,
                question = "آن چیست که به هوا می پرد ولی دم، بال و پا ندارد؟",
                answer = "بادکنک",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'ب',
                    'ا',
                    'د',
                    'ک',
                    'ن',
                    'ک',
                ),
                letters = arrayListOf('ک', 'م', 'ک', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ب')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 52,
                titleLevel = 52,
                isLockLevel = true,
                question = "یک کیلوگرم پر سنگین تر است یا یک کیلوگرم آجر؟",
                answer = "برابر است",
                sizeAnswer = 8,
                listAnswer = arrayListOf(
                    'ب',
                    'ر',
                    'ا',
                    'ب',
                    'ر',
                    'ا',
                    'س',
                    'ت',
                ),
                letters = arrayListOf('ا', 'س', 'ه', 'ت', 'د', 'ک', 'ر', 'ر', 'ب', 'ی', 'ا', 'ب')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 53,
                titleLevel = 53,
                isLockLevel = true,
                question = "من مثل یک پر سبک هستم، ولی قویترین فرد هم بیشتر از ۵ دقیقه نمیتواند مرا نگه دارد. من چه هستم؟",
                answer = "نفس",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ن',
                    'ف',
                    'س',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 54,
                titleLevel = 54,
                isLockLevel = true,
                question = "چیست که روی سه پا میایستد و روی یک پا راه میرود؟",
                answer = "فرغون",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ف',
                    'ر',
                    'غ',
                    'و',
                    'ن',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'ر', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'غ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 55,
                titleLevel = 55,
                isLockLevel = true,
                question = "آن چیست که دست دارد، پا ندارد همیشه هم با دستاش دستاتو می پوشونه؟",
                answer = "پیراهن",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'پ',
                    'ی',
                    'ر',
                    'ا',
                    'ه',
                    'ن',
                ),
                letters = arrayListOf('چ', 'ر', 'س', 'ی', 'د', 'پ', 'ن', 'ف', 'و', 'ه', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 56,
                titleLevel = 56,
                isLockLevel = true,
                question = "من پر از کلید هستم، اما نمیتوانم هیچ دربی را باز کنم. من چیستم؟",
                answer = "پیانو",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'پ',
                    'ی',
                    'ا',
                    'ن',
                    'و',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ی', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 57,
                titleLevel = 57,
                isLockLevel = true,
                question = "آن چیست که چند بار به انسان داده می شود دو بار اول رایگانه ولی بار سوم پولیه؟",
                answer = "دندان",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'د',
                    'ن',
                    'د',
                    'ا',
                    'ن',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'پ', 'ن', 'د', 'و', 'س', 'ا', 'ن')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 58,
                titleLevel = 58,
                isLockLevel = true,
                question = "آن چیست که وقتی چشم هامون را باز می کنیم دیگر نمی بینیم؟",
                answer = "خواب",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'خ',
                    'و',
                    'ا',
                    'ب',
                ),
                letters = arrayListOf('ب', 'م', 'س', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'خ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 59,
                titleLevel = 59,
                isLockLevel = true,
                question = "من هر روز ریش کوتاه می کنم اما ریشام همونجوری باقی می مونن، من کی هستم؟",
                answer = "آرایشگر",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'آ',
                    'ر',
                    'ا',
                    'ی',
                    'ش',
                    'گ',
                    'ر',
                ),
                letters = arrayListOf('ش', 'گ', 'س', 'ی', 'د', 'ر', 'ن', 'ر', 'و', 'س', 'ا', 'آ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 60,
                titleLevel = 60,
                isLockLevel = true,
                question = "می دونید بچه ی قورباغه و لک لک چی می شه؟",
                answer = "قلک",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ق',
                    'ل',
                    'ک',
                ),
                letters = arrayListOf('چ', 'ق', 'س', 'ت', 'د', 'پ', 'ن', 'ل', 'و', 'س', 'ا', 'ک')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 61,
                titleLevel = 61,
                isLockLevel = true,
                question = "اولین بزی که دیابت داشت چه نام دارد؟",
                answer = "بزبز قندی",
                sizeAnswer = 8,
                listAnswer = arrayListOf(
                    'ب',
                    'ز',
                    'ب',
                    'ز',
                    'ق',
                    'ن',
                    'د',
                    'ی',
                ),
                letters = arrayListOf('ق', 'م', 'ن', 'ز', 'ب', 'پ', 'د', 'ف', 'ز', 'ی', 'ا', 'ب')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 62,
                titleLevel = 62,
                isLockLevel = true,
                question = "آن چیست که روز و شب دست به کمر ایستاده؟",
                answer = "آفتابه",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'آ',
                    'ف',
                    'ت',
                    'ا',
                    'ب',
                    'ه',
                ),
                letters = arrayListOf('چ', 'ه', 'س', 'ب', 'د', 'پ', 'ن', 'ف', 'ت', 'س', 'ا', 'آ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 63,
                titleLevel = 63,
                isLockLevel = true,
                question = "آن چیست که برای همه ی مردم لباس می دوزد ولی خودش لباس ندارد؟",
                answer = "سوزن",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'س',
                    'و',
                    'ز',
                    'ن',
                ),
                letters = arrayListOf('ی', 'م', 'س', 'ت', 'د', 'ز', 'ن', 'ط', 'و', 'س', 'ا', 'خ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 64,
                titleLevel = 64,
                isLockLevel = true,
                question = "کشوری که در پیشانی انسان جای دارد. کدام است؟",
                answer = "چین",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'چ',
                    'ی',
                    'ن',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'ی', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 65,
                titleLevel = 65,
                isLockLevel = true,
                question = "همه آن را دارند و هیچ کس آن را از دست نمی دهد. آن چیست؟",
                answer = "سایه",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'س',
                    'ا',
                    'ی',
                    'ه',
                ),
                letters = arrayListOf('چ', 'ه', 'س', 'ت', 'ی', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 66,
                titleLevel = 66,
                isLockLevel = true,
                question = "آن چیست که گردن دارد اما سر ندارد. دست دارد اما پای ندارد؟",
                answer = "کوزه",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ک',
                    'و',
                    'ز',
                    'ه',
                ),
                letters = arrayListOf('چ', 'ه', 'س', 'ز', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ک')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 67,
                titleLevel = 67,
                isLockLevel = true,
                question = "پا دارد، اما نمی تواند راه برود. آن چیست؟",
                answer = "میز",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'م',
                    'ی',
                    'ز',
                ),
                letters = arrayListOf('چ', 'ز', 'س', 'ت', 'د', 'ی', 'ن', 'ف', 'و', 'س', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 68,
                titleLevel = 68,
                isLockLevel = true,
                question = "ذات آن آب و دشمن آن هم آب! آن چیست؟",
                answer = "یخ",
                sizeAnswer = 2,
                listAnswer = arrayListOf(
                    'ی',
                    'خ',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'پ', 'ی', 'ف', 'و', 'خ', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 69,
                titleLevel = 69,
                isLockLevel = true,
                question = "یک واژه بگوید که همه حروف در آن باشد!",
                answer = "الفبا",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ا',
                    'ل',
                    'ف',
                    'ب',
                    'ا',
                ),
                letters = arrayListOf('چ', 'ب', 'س', 'ت', 'ل', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ا')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 70,
                titleLevel = 70,
                isLockLevel = true,
                question = "آن چیست که کلی برگ دارد اما ساقه ندارد؟",
                answer = "کتاب",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ک',
                    'ت',
                    'ا',
                    'ب',
                ),
                letters = arrayListOf('ک', 'م', 'س', 'ت', 'د', 'ب', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 71,
                titleLevel = 71,
                isLockLevel = true,
                question = "آن چیست که داخل هر غذایی ریخته شود مزه می دهد، اما اشک تو رو هم در می آورد؟",
                answer = "پیاز",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'پ',
                    'ی',
                    'ا',
                    'ز',
                ),
                letters = arrayListOf('چ', 'م', 'ی', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ز')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 72,
                titleLevel = 72,
                isLockLevel = true,
                question = "آن چیست که دست به کمر است، تپل مپل است حرف میزند قل قل؟",
                answer = "کتری",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ک',
                    'ت',
                    'ر',
                    'ی',
                ),
                letters = arrayListOf('ی', 'م', 'ک', 'ت', 'د', 'ر', 'ن', 'ف', 'و', 'س', 'ق', 'ی')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 73,
                titleLevel = 73,
                isLockLevel = true,
                question = "یک زن و شوهر ۷ پسر دارند و هر پسر یک خواهر دارد. این خانواده چند نفر هستند؟",
                answer = "ده نفر",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'د',
                    'ه',
                    'ن',
                    'ف',
                    'ر',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'د', 'ن', 'ف', 'ر', 'ه', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 74,
                titleLevel = 74,
                isLockLevel = true,
                question = "وقتی که می خوام تغییر کنم سر و صدا می کنم . وقتی ام که تغییر کنم بزرگ می شم اما وزنم کمتر میشه، من چیم؟",
                answer = "پف فیل",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'پ',
                    'ف',
                    'ف',
                    'ی',
                    'ل',
                ),
                letters = arrayListOf('چ', 'ی', 'س', 'ف', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ل')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 75,
                titleLevel = 75,
                isLockLevel = true,
                question = "اون چیه که همیشه روبروته اما نمی تونی ببینی؟",
                answer = "آینده",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'آ',
                    'ی',
                    'ن',
                    'د',
                    'ه',
                ),
                letters = arrayListOf('ه', 'م', 'س', 'ت', 'د', 'ی', 'ن', 'ف', 'و', 'س', 'آ', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 76,
                titleLevel = 76,
                isLockLevel = true,
                question = "تصور کن یه مردی بدون چتر زیر بارونه اما هیچ کدوم از موهای سرش خیس نمیشه، چه جوری ممکنه؟",
                answer = "کچله",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ک',
                    'چ',
                    'ل',
                    'ه',
                ),
                letters = arrayListOf('ک', 'م', 'ه', 'ت', 'د', 'ل', 'ن', 'ف', 'و', 'س', 'ا', 'چ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 77,
                titleLevel = 77,
                isLockLevel = true,
                question = "آن چیست که بال ندارد ولی پرواز می کند و چشم ندارد ولی گریه می کند؟",
                answer = "ابر",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ا',
                    'ب',
                    'ر',
                ),
                letters = arrayListOf('چ', 'م', 'ر', 'ت', 'د', 'ب', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 78,
                titleLevel = 78,
                isLockLevel = true,
                question = "آن چیست داغ است و در آسمان. نورانی و درخشان. خیره نشو هرگز به آن. شب ها می شود نهان؟",
                answer = "خورشید",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'خ',
                    'و',
                    'ر',
                    'ش',
                    'ی',
                    'د',
                ),
                letters = arrayListOf('خ', 'د', 'س', 'ت', 'د', 'ر', 'ن', 'ف', 'و', 'ی', 'ا', 'ش')
            )
        )

        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 79,
                titleLevel = 79,
                isLockLevel = true,
                question = "آن چیست که هر چی بیشتر شود کمتر می بینید؟",
                answer = "مه",
                sizeAnswer = 2,
                listAnswer = arrayListOf(
                    'م',
                    'ه',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'ه', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 80,
                titleLevel = 80,
                isLockLevel = true,
                question = "آن چیست که شنیده می شود اما نمی توان آن را لمس کرد یا دید؟",
                answer = "صدا",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ص',
                    'د',
                    'ا',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'پ', 'ن', 'د', 'و', 'ص', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 81,
                titleLevel = 81,
                isLockLevel = true,
                question = "آن چیست که پر از تیغ است اما نه ساقه دارد نه ریشه؟",
                answer = "جوجه تیغی",
                sizeAnswer = 8,
                listAnswer = arrayListOf(
                    'ج',
                    'و',
                    'ج',
                    'ه',
                    'ت',
                    'ی',
                    'غ',
                    'ی',
                ),
                letters = arrayListOf('چ', 'ی', 'س', 'غ', 'د', 'ت', 'ی', 'ف', 'ه', 'و', 'ج', 'ج')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 82,
                titleLevel = 82,
                isLockLevel = true,
                question = "آن چه میوه ای که دو حرف اولش یک عدد دو رقمی است؟",
                answer = "سیب",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'س',
                    'ی',
                    'ب',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ی', 'ب', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 83,
                titleLevel = 83,
                isLockLevel = true,
                question = "چه چیزی را میتوانید بگیرید، اما پرتاب نکنید؟",
                answer = "سرماخوردگی",
                sizeAnswer = 10,
                listAnswer = arrayListOf(
                    'س',
                    'ر',
                    'م',
                    'ا',
                    'خ',
                    'و',
                    'ر',
                    'د',
                    'گ',
                    'ی',
                ),
                letters = arrayListOf('س', 'ا', 'ر', 'خ', 'م', 'و', 'ر', 'د', 'ی', 'گ', 'ت', 'ص')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 84,
                titleLevel = 84,
                isLockLevel = true,
                question = "رنگ سفید سنگها میان سفره ها/ هرکس نداند نامه او/ مزه ندارد کام او؟ ",
                answer = "نمک",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ن',
                    'م',
                    'ک',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ک')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 85,
                titleLevel = 85,
                isLockLevel = true,
                question = " آن چیست که همه جا می رود ولی از خانه اش بیرون نمی آید؟ ",
                answer = "لاک پشت",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'ل',
                    'ا',
                    'ک',
                    'پ',
                    'ش',
                    'ت',
                ),
                letters = arrayListOf('ن', 'م', 'و', 'ت', 'ز', 'پ', 'ن', 'ک', 'ح', 'ل', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 86,
                titleLevel = 86,
                isLockLevel = true,
                question = "بچه ای است که به محضی به مادرش می رسد ناپدید می شود!",
                answer = "رودخانه",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'ر',
                    'و',
                    'د',
                    'خ',
                    'ا',
                    'ن',
                    'ه',
                ),
                letters = arrayListOf('خ', 'ر', 'س', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ه')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 87,
                titleLevel = 87,
                isLockLevel = true,
                question = "کدام اتاق نشیمنی است که حرکت می کند؟ ",
                answer = "اتاق ماشین",
                sizeAnswer = 9,
                listAnswer = arrayListOf(
                    'ا',
                    'ت',
                    'ا',
                    'ق',
                    'م',
                    'ا',
                    'ش',
                    'ی',
                    'ن',
                ),
                letters = arrayListOf('ش', 'م', 'ا', 'ت', 'ا', 'ی', 'ن', 'ق', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 88,
                titleLevel = 88,
                isLockLevel = true,
                question = "اگر لحظه ای آن را بخوری، باید روزها بخوابی! ",
                answer = "سرما",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'س',
                    'ر',
                    'م',
                    'ا',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'پ', 'ر', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 89,
                titleLevel = 89,
                isLockLevel = true,
                question = "شهری است که با یک خواننده شروع می شود و با یک ماده معطر تمام می شود؟ ",
                answer = "اندیمشک",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'ا',
                    'ن',
                    'د',
                    'ی',
                    'م',
                    'ش',
                    'ک',
                ),
                letters = arrayListOf('ک', 'م', 'س', 'ی', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 90,
                titleLevel = 90,
                isLockLevel = true,
                question = "خیلی خطرناک و وحشی است اما اگر وارونه اش کنید آرام و رام می شود؟ ",
                answer = "مار",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'م',
                    'ا',
                    'ر',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'ر', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 91,
                titleLevel = 91,
                isLockLevel = true,
                question = "آن كدام هشت است كه نه نمي شود؟",
                answer = "هشت پا",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ه',
                    'ش',
                    'ت',
                    'پ',
                    'ا',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'ه', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 92,
                titleLevel = 92,
                isLockLevel = true,
                question = "چهار انگشت و یک شست دارد اما نه استخوان دارد و نه مغز!",
                answer = "دستکش",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'د',
                    'س',
                    'ت',
                    'ک',
                    'ش',
                ),
                letters = arrayListOf('ک', 'م', 'س', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 93,
                titleLevel = 93,
                isLockLevel = true,
                question = "میوه ایی است که از هر طرف که آن را بخوانی باز میوه است؟ ",
                answer = "توت",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ت',
                    'و',
                    'ت',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'ت', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 94,
                titleLevel = 94,
                isLockLevel = true,
                question = "آن کدام شبه فلزی است که اگر وارونه اش کنید نوعی سبزیجات خواهد شد؟",
                answer = "جیوه",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ج',
                    'ی',
                    'و',
                    'ه',
                ),
                letters = arrayListOf('چ', 'م', 'ه', 'ت', 'د', 'ی', 'ن', 'ف', 'و', 'س', 'ا', 'ج')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 95,
                titleLevel = 95,
                isLockLevel = true,
                question = "جانوری خطرناک هستم که اگر وارونه ام کنید واحد اندازه گیری می شوم!",
                answer = "رتیل",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ر',
                    'ت',
                    'ی',
                    'ل',
                ),
                letters = arrayListOf('ل', 'م', 'ی', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'ر')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 96,
                titleLevel = 96,
                isLockLevel = true,
                question = "در آسمان فقط یکی از آن هست اما در زمین بسیار زیاد! ",
                answer = "مشتری",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'م',
                    'ش',
                    'ت',
                    'ر',
                    'ی',
                ),
                letters = arrayListOf('ی', 'م', 'س', 'ت', 'د', 'ر', 'ن', 'ف', 'و', 'س', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 97,
                titleLevel = 97,
                isLockLevel = true,
                question = "وقتی همه اعداد روی صفحه شماره تلفن را ضرب می کنید چه عددی به دست می آید؟",
                answer = "عدد صفر",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'ع',
                    'د',
                    'د',
                    'ص',
                    'ف',
                    'ر',
                ),
                letters = arrayListOf('ر', 'ش', 'ص', 'ت', 'د', 'د', 'ن', 'ف', 'و', 'س', 'ش', 'ع')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 98,
                titleLevel = 98,
                isLockLevel = true,
                question = "نوعی غذای فرنگی که هم خوردنش در ایران رواج دارد و هم در وسطش رود ایرانی قرار گرفته است! ",
                answer = "ماکارونی",
                sizeAnswer = 8,
                listAnswer = arrayListOf(
                    'م',
                    'ا',
                    'ک',
                    'ا',
                    'ر',
                    'و',
                    'ن',
                    'ی',
                ),
                letters = arrayListOf('ی', 'م', 'س', 'ت', 'ر', 'پ', 'ن', 'ا', 'و', 'س', 'ا', 'ک')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 99,
                titleLevel = 99,
                isLockLevel = true,
                question = " آن چیست که از ته پر می شود و از بالا خالی؟",
                answer = "تفنگ",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ت',
                    'ف',
                    'ن',
                    'گ',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ت', 'د', 'پ', 'ن', 'ف', 'و', 'س', 'ا', 'گ')
            )
        )

        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 100,
                titleLevel = 100,
                isLockLevel = true,
                question = "از آنجایی که از یک معدن آمده ام، همیشه با چوب محاصره ام کرده اند. همه از من استفاده میکنند. من چه چیزی هستم؟ ",
                answer = "مغزی مداد",
                sizeAnswer = 8,
                listAnswer = arrayListOf(
                    'م',
                    'غ',
                    'ز',
                    'ی',
                    'م',
                    'د',
                    'ا',
                    'د',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )

        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 101,
                titleLevel = 101,
                isLockLevel = true,
                question = "آن چیست که اگر از آسمان بیفتد نمیشکند، ولی با دندان می شکند؟",
                answer = "تخمه",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ت',
                    'خ',
                    'م',
                    'ه',
                ),
                letters = arrayListOf('چ', 'م', 'ه', 'د', 'د', 'خ', 'ی', 'ز', 'و', 'ت', 'ا', 'م')
            )
        )

        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 102,
                titleLevel = 102,
                isLockLevel = true,
                question = "آن چیست که در طول شبانه روز دست به کمر ایستاده؟ ",
                answer = "آفتابه",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'آ',
                    'ف',
                    'ت',
                    'ا',
                    'ب',
                    'ه',
                ),
                letters = arrayListOf('ط', 'ب', 'س', 'ف', 'ت', 'ا', 'ی', 'ه', 'و', 'آ', 'ا', 'ح')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 103,
                titleLevel = 103,
                isLockLevel = true,
                question = "کدام کلمه در فرهنگ لغت به اشتباه تلفظ شده است؟",
                answer = "غلط",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'غ',
                    'ل',
                    'ط',
                ),
                letters = arrayListOf('چ', 'م', 'ط', 'د', 'ت', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'ل')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 104,
                titleLevel = 104,
                isLockLevel = true,
                question = "نه انگورم نه انار، هم انگورم هم در انار. زنجیر نیستم اما در زنجیرم. نخجیر نیستم اما در نخجیرم. آن چیست؟",
                answer = "انجیر",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ا',
                    'ن',
                    'ج',
                    'ی',
                    'ر',
                ),
                letters = arrayListOf('چ', 'م', 'ر', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'ج', 'ا', 'ن')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 105,
                titleLevel = 105,
                isLockLevel = true,
                question = "دندانه های زیادی دارد، اما نمی تواند گاز بگیرد. آن چیست؟",
                answer = "شانه",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ش',
                    'ا',
                    'ن',
                    'ه',
                ),
                letters = arrayListOf('چ', 'ر', 'ن', 'د', 'م', 'پ', 'ا', 'ز', 'و', 'ه', 'ت', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 106,
                titleLevel = 106,
                isLockLevel = true,
                question = "باید باز شود، اما قفل و کلیدی برای باز شدن آن نیست. آن چیست؟",
                answer = "تخم مرغ",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'ت',
                    'خ',
                    'م',
                    'م',
                    'ر',
                    'غ'
                ),
                letters = arrayListOf('چ', 'ر', 'خ', 'د', 'م', 'پ', 'ی', 'ز', 'و', 'غ', 'ت', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 107,
                titleLevel = 107,
                isLockLevel = true,
                question = "آن چیست که در زمستان زندگی می کند، تابستان می میرد، و ریشه های آن در بالایش است؟",
                answer = "قندیل",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ق',
                    'ن',
                    'د',
                    'ی',
                    'ل',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'ن', 'ی', 'ز', 'و', 'ل', 'ا', 'ق')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 108,
                titleLevel = 108,
                isLockLevel = true,
                question = "کسی که آن را ساخته، آن را نمی خواهد. کسی که آن را خریده به آن نیازی ندارد. کسی که از آن استفاده کرده هرگز آن را ندیده. آن چیست؟",
                answer = "تابوت",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ت',
                    'ا',
                    'ب',
                    'و',
                    'ت',
                ),
                letters = arrayListOf('چ', 'ب', 'س', 'ا', 'د', 'پ', 'ت', 'ز', 'و', 'غ', 'ا', 'ت')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 109,
                titleLevel = 109,
                isLockLevel = true,
                question = "با صدای من، مردان رویاپردازی می کنند یا پا به زمین می کوبند؛ با صدای من، زنان می خندند یا گاهی گریه می کنند.",
                answer = "موسیقی",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'م',
                    'و',
                    'س',
                    'ی',
                    'ق',
                    'ی',
                ),
                letters = arrayListOf('چ', 'ی', 'س', 'ق', 'د', 'س', 'ی', 'ز', 'و', 'غ', 'و', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 110,
                titleLevel = 110,
                isLockLevel = true,
                question = "یک دانه در کنج، یک دانه در خانه، هیچ در اتاق، و دو تا در دندان. آن چیست؟",
                answer = "حرف ن",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ح',
                    'ر',
                    'ف',
                    'ن',
                ),
                letters = arrayListOf('ن', 'م', 'س', 'ف', 'د', 'پ', 'ی', 'ر', 'و', 'غ', 'ا', 'ح')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 111,
                titleLevel = 111,
                isLockLevel = true,
                question = "آن چیست که برخی حیوانات در دهان و بعضی در دم دارند؟",
                answer = "نیش",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ن',
                    'ی',
                    'ش',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'ش', 'ن', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 112,
                titleLevel = 112,
                isLockLevel = true,
                question = "آن چیست كه خود ریسد و خود بافد جامه. پوست در پوست گرد یكدیگر؟",
                answer = "عنكبوت",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'ع',
                    'ن',
                    'ک',
                    'ب',
                    'و',
                    'ت'
                ),
                letters = arrayListOf('ت', 'ب', 'س', 'ک', 'د', 'پ', 'ی', 'ز', 'و', 'ع', 'ا', 'ن')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 113,
                titleLevel = 113,
                isLockLevel = true,
                question = "آن چیست گرد و كوچك، آویز و معلق. گرد است و دراز و در ندارد؟",
                answer = "انگور",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ا',
                    'ن',
                    'گ',
                    'و',
                    'ر',
                ),
                letters = arrayListOf('چ', 'ر', 'س', 'د', 'د', 'پ', 'گ', 'ز', 'و', 'غ', 'ا', 'ن')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 114,
                titleLevel = 114,
                isLockLevel = true,
                question = "تمام فضای خانه را می گیرد، اما می تواند از سوراخ کلید بیرون رود. آن چیست؟ ",
                answer = "دود",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'د',
                    'و',
                    'د',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 115,
                titleLevel = 115,
                isLockLevel = true,
                question = "چه چیزی می تواند یک اتاق را پر کند اما فضایی را اشغال نمی کند؟",
                answer = "روشنایی",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'ر',
                    'و',
                    'ش',
                    'ن',
                    'ا',
                    'ی',
                    'ی',
                ),
                letters = arrayListOf('ی', 'ش', 'س', 'ا', 'د', 'پ', 'ن', 'ز', 'و', 'ی', 'ا', 'ر')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 116,
                titleLevel = 116,
                isLockLevel = true,
                question = "هر چه آب در آن بریزند، پر نمی شود. آن چیست؟ ",
                answer = "آبکش",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'آ',
                    'ب',
                    'ک',
                    'ش',
                ),
                letters = arrayListOf('چ', 'ب', 'ش', 'د', 'آ', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'ک')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 117,
                titleLevel = 117,
                isLockLevel = true,
                question = "آن چیست که خیال می کنیم ما او را می خوریم، اما در واقع اوست که ما را می خورد؟",
                answer = "غصه",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'غ',
                    'ص',
                    'ه',
                ),
                letters = arrayListOf('ه', 'م', 'س', 'ص', 'د', 'س', 'ق', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 118,
                titleLevel = 118,
                isLockLevel = true,
                question = "در آسمان فقط یکی از آن هست اما در زمین بسیار زیاد!",
                answer = "مشتری",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'م',
                    'ش',
                    'ت',
                    'ر',
                    'ی',
                ),
                letters = arrayListOf('چ', 'م', 'ج', 'د', 'د', 'ت', 'ی', 'ر', 'و', 'و', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 119,
                titleLevel = 119,
                isLockLevel = true,
                question = "آن چیست که در رادیو و دریا مشترک و هردو آن را دارند؟",
                answer = "موج",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'م',
                    'و',
                    'ج',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 120,
                titleLevel = 120,
                isLockLevel = true,
                question = "آن چه كشوری است كه در چشم ماست؟",
                answer = "کره",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ک',
                    'ر',
                    'ه',
                ),
                letters = arrayListOf('ه', 'م', 'س', 'ر', 'د', 'پ', 'ی', 'ک', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 121,
                titleLevel = 121,
                isLockLevel = true,
                question = "آن چیست كه هر چه آن را بیشتر بكشیم كوتاه تر می شود؟",
                answer = "سیگار",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'س',
                    'ی',
                    'گ',
                    'ا',
                    'ر',
                ),
                letters = arrayListOf('گ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ز', 'ا', 'غ', 'ا', 'ر')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 122,
                titleLevel = 122,
                isLockLevel = true,
                question = "آن چیست كه بی علم و دانش ،همه چیز را همانگونه كه هست آشكار می سازد؟",
                answer = "آیینه",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'آ',
                    'ی',
                    'ی',
                    'ن',
                    'ه'
                ),
                letters = arrayListOf('ه', 'ی', 'س', 'د', 'د', 'آ', 'ی', 'ز', 'و', 'غ', 'ا', 'ن')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 123,
                titleLevel = 123,
                isLockLevel = true,
                question = "آن چه گرد سبز رنگی است که اگر در آب بریزند،قرمز می شود؟",
                answer = "حنا",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ح',
                    'ن',
                    'ا',
                ),
                letters = arrayListOf('چ', 'ن', 'س', 'د', 'د', 'پ', 'ا', 'ز', 'و', 'غ', 'ا', 'ح')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 124,
                titleLevel = 124,
                isLockLevel = true,
                question = "از چوب ساخته شده، اما نمی توان آن را اره کرد. آن چیست؟",
                answer = "خاک اره",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'خ',
                    'ا',
                    'ک',
                    'ا',
                    'ر',
                    'ه',
                ),
                letters = arrayListOf('ه', 'م', 'س', 'ر', 'د', 'ا', 'ی', 'ک', 'و', 'غ', 'ا', 'خ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 125,
                titleLevel = 125,
                isLockLevel = true,
                question = "در وسط “ تهران ” قطعا چه چیزی پیدا میشود؟",
                answer = "حرف ر",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ح',
                    'ر',
                    'ف',
                    'ر',
                ),
                letters = arrayListOf('ت', 'ر', 'س', 'د', 'د', 'ف', 'ی', 'ر', 'و', 'غ', 'ا', 'ح')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 126,
                titleLevel = 126,
                isLockLevel = true,
                question = "آن چیست که دوچشم دارد و وقتی انگشت به چشمانش فرو می کنی گوشهایش تیز می شود؟",
                answer = "قیچی",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ق',
                    'ی',
                    'چ',
                    'ی',
                ),
                letters = arrayListOf('ی', 'م', 'چ', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'ق')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 127,
                titleLevel = 127,
                isLockLevel = true,
                question = "کدام کشور است که اگر نام اولش را بردارید یک بیماری می شود؟",
                answer = "کوبا",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ک',
                    'و',
                    'ب',
                    'ا',
                ),
                letters = arrayListOf('چ', 'م', 'ا', 'د', 'د', 'ب', 'ی', 'ز', 'و', 'غ', 'ا', 'ک')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 128,
                titleLevel = 128,
                isLockLevel = true,
                question = "همه دنبال ام می گردند، ولی از رو به رو شدن با من تنفر دارند. برای برخی عذاب، و برای برخی تسکین ام. من چه هستم؟",
                answer = "حقیقت",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ح',
                    'ق',
                    'ی',
                    'ق',
                    'ت',
                ),
                letters = arrayListOf('ط', 'ت', 'س', 'د', 'د', 'ق', 'ی', 'ق', 'و', 'غ', 'ه', 'ح')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 129,
                titleLevel = 129,
                isLockLevel = true,
                question = "آن چیست که روی آب می شکند، ولی هرگز روی خشکی نمی شکند؟",
                answer = "موج",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'م',
                    'و',
                    'ج',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ج', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 130,
                titleLevel = 130,
                isLockLevel = true,
                question = "آن چیست که قابل اندازه گیری هست، ولی قابل مشاهده نیست؟",
                answer = "زمان",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ز',
                    'م',
                    'ا',
                    'ن',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ن', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 131,
                titleLevel = 131,
                isLockLevel = true,
                question = "آن چیست که خورشید می پزدش، دست می چیندش، پا بر آن گذاشته می شود، و خورده میشود؟",
                answer = "انگور",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ا',
                    'ن',
                    'گ',
                    'و',
                    'ر',
                ),
                letters = arrayListOf('چ', 'ر', 'س', 'گ', 'د', 'پ', 'ی', 'ن', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 132,
                titleLevel = 132,
                isLockLevel = true,
                question = "دیده نمیشود، وزنی ندارد، ولی وقتی داخل یک بشکه میرود، آن را سبک تر میکند. آن چیست؟",
                answer = "سوراخ",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'س',
                    'و',
                    'ر',
                    'ا',
                    'خ',
                ),
                letters = arrayListOf('خ', 'م', 'س', 'ر', 'د', 'پ', 'و', 'ز', 'و', 'غ', 'ا', 'س')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 133,
                titleLevel = 133,
                isLockLevel = true,
                question = "اگر به صورت ام نگاه کنی، هیچ کجا ۱۳ نخواهی یافت. من چه هستم؟",
                answer = "ساعت",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'س',
                    'ا',
                    'ع',
                    'ت',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'ت', 'ی', 'ز', 'و', 'ع', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 134,
                titleLevel = 134,
                isLockLevel = true,
                question = "چه چیزی میتواند به سراسر دنیا سفر کند در حالی که در یک گوشه نشسته است؟",
                answer = "تمبر",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ت',
                    'م',
                    'ب',
                    'ر',
                ),
                letters = arrayListOf('چ', 'ر', 'س', 'د', 'ب', 'پ', 'ی', 'ز', 'و', 'ت', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 135,
                titleLevel = 135,
                isLockLevel = true,
                question = "آن چیست که هر بار دوش میگیرد کوچکتر میشود؟",
                answer = "صابون",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ص',
                    'ا',
                    'ب',
                    'و',
                    'ن',
                ),
                letters = arrayListOf('ب', 'م', 'ص', 'د', 'د', 'ا', 'ی', 'ز', 'و', 'غ', 'ا', 'ن')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 136,
                titleLevel = 136,
                isLockLevel = true,
                question = "آن چه میوه ای است که هسته هایش بیرون آن است؟",
                answer = "توت فرنگی",
                sizeAnswer = 8,
                listAnswer = arrayListOf(
                    'ت',
                    'و',
                    'ت',
                    'ف',
                    'ر',
                    'ن',
                    'گ',
                    'ی',
                ),
                letters = arrayListOf('ی', 'ن', 'گ', 'ر', 'ف', 'پ', 'ت', 'ز', 'و', 'غ', 'ا', 'ت')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 137,
                titleLevel = 137,
                isLockLevel = true,
                question = "آن چیست که سفید است و مردم را می ترساند؟",
                answer = "شبه",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ش',
                    'ب',
                    'ه',
                ),
                letters = arrayListOf('چ', 'ه', 'س', 'د', 'د', 'ش', 'ی', 'ز', 'و', 'ب', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 138,
                titleLevel = 138,
                isLockLevel = true,
                question = "آن چیست که نمی گذارد قایق ها و کشتی ها در آب تکان بخورند؟",
                answer = "لنگر",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ل',
                    'ن',
                    'گ',
                    'ر',
                ),
                letters = arrayListOf('ر', 'م', 'س', 'گ', 'د', 'پ', 'ی', 'ن', 'و', 'غ', 'ا', 'ل')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 139,
                titleLevel = 139,
                isLockLevel = true,
                question = "آن چیست که هر چه بیشتر باشد، کمتر می بینید؟",
                answer = "تاریکی",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'ت',
                    'ا',
                    'ر',
                    'ی',
                    'ک',
                    'ی',
                ),
                letters = arrayListOf('ی', 'م', 'س', 'ک', 'د', 'پ', 'ی', 'ر', 'و', 'غ', 'ا', 'ت')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 140,
                titleLevel = 140,
                isLockLevel = true,
                question = "کدام یک از حبوبات را واروونه کنیم نام یکی از وعده های غذایی می شود؟",
                answer = "ماش",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'م',
                    'ا',
                    'ش',
                ),
                letters = arrayListOf('چ', 'م', 'ش', 'د', 'د', 'پ', 'ا', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 141,
                titleLevel = 141,
                isLockLevel = true,
                question = "همیشه در حال آمدن است، اما هیچ گاه نمی رسد. آن چیست؟",
                answer = "فردا",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ف',
                    'ر',
                    'د',
                    'ا',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ز', 'ف', 'غ', 'ا', 'ر')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 142,
                titleLevel = 142,
                isLockLevel = true,
                question = "آن چیست که گرد است بالا و پائین می پرد. می توانی پرتش کنی، می توانی بگیری اش. نباید به شیشه ها بکوبی اش؟",
                answer = "توپ",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ت',
                    'و',
                    'پ',
                ),
                letters = arrayListOf('چ', 'پ', 'س', 'د', 'د', 'پ', 'ی', 'ت', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 143,
                titleLevel = 143,
                isLockLevel = true,
                question = "لطفا صبوری کنید با من، در این دنیا تازه واردم. زیاد گریه میکنم، به من شیر بدین. همه به من لبخند می زنند، لطفا بغلم کنید. من چیستم؟",
                answer = "نوزاد",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ن',
                    'و',
                    'ز',
                    'ا',
                    'د',
                ),
                letters = arrayListOf('چ', 'م', 'ز', 'د', 'ا', 'پ', 'ن', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 144,
                titleLevel = 144,
                isLockLevel = true,
                question = "آن چیست که خوش طعم و شیرین است؛ می توانی لیسش بزنی. هم در پیاله است و هم در قیف. یکی از طعم های اش طعم وانیلی ست؟",
                answer = "بستنی",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ب',
                    'س',
                    'ت',
                    'ن',
                    'ی',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ن', 'د', 'ت', 'ی', 'ز', 'و', 'ب', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 145,
                titleLevel = 145,
                isLockLevel = true,
                question = "آن چیست که چهار پا دارد اما دم ندارد. معمولا فقط شب ها صدایش را می شنوی؟",
                answer = "قورباغه",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'ق',
                    'و',
                    'ر',
                    'ب',
                    'ا',
                    'غ',
                    'ه',
                ),
                letters = arrayListOf('خ', 'ه', 'غ', 'د', 'غ', 'ق', 'ی', 'ر', 'و', 'غ', 'ا', 'ب')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 146,
                titleLevel = 146,
                isLockLevel = true,
                question = "آن چیست كه پا و سر ندارد. جز نام دو جانور ندارد اندر شكمش ستارگانند. اندام ظریف چون صنوبر دارد؟",
                answer = "خربزه",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'خ',
                    'ر',
                    'ب',
                    'ز',
                    'ه',
                ),
                letters = arrayListOf('ه', 'م', 'ب', 'د', 'د', 'ر', 'ی', 'ز', 'و', 'غ', 'ا', 'خ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 147,
                titleLevel = 147,
                isLockLevel = true,
                question = "وارد آن می شوی، ولی داخل آن نیستی؛ فضای زیادی دارد، ولی اتاق ندارد. کلیدهای زیادی دارد، ولی دری را نمی تواند باز کند. آن چیست؟",
                answer = "کامپیوتر",
                sizeAnswer = 8,
                listAnswer = arrayListOf(
                    'ک',
                    'ا',
                    'م',
                    'پ',
                    'ی',
                    'و',
                    'ت',
                    'ر',
                ),
                letters = arrayListOf('ر', 'م', 'ت', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'ک', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 148,
                titleLevel = 148,
                isLockLevel = true,
                question = "سخت است ولی سنگ نیست تخم می گذارد ولی مرغ نیست چهار پا دارد ولی سگ نیست؟",
                answer = "لاک پشت",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'ل',
                    'ا',
                    'ک',
                    'پ',
                    'ش',
                    'ت',
                ),
                letters = arrayListOf('ت', 'م', 'ش', 'د', 'د', 'پ', 'ی', 'ز', 'ل', 'غ', 'ا', 'ک')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 149,
                titleLevel = 149,
                isLockLevel = true,
                question = "کدام حیوان است که اگر آن را از آخر به اول بخوانی پلید و بدیمن می شود؟",
                answer = "موش",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'م',
                    'و',
                    'ش',
                ),
                letters = arrayListOf('چ', 'م', 'ش', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 150,
                titleLevel = 150,
                isLockLevel = true,
                question = "آن کدام جانور است که دو حرف اولش تکراری است؟",
                answer = "ببر",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ب',
                    'ب',
                    'ر',
                ),
                letters = arrayListOf('ر', 'م', 'ب', 'د', 'د', 'ب', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 151,
                titleLevel = 151,
                isLockLevel = true,
                question = "میتونی حدس بزنی کدوم میوه تمام حروف جیران رو داره؟",
                answer = "انجیر",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ا',
                    'ن',
                    'ج',
                    'ی',
                    'ر',
                ),
                letters = arrayListOf('چ', 'ر', 'س', 'د', 'ج', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'ن')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 152,
                titleLevel = 152,
                isLockLevel = true,
                question = "آن کدام میوه است که سه حرف اولش نام یک کشور است؟",
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
                letters = arrayListOf('چ', 'م', 'ه', 'د', 'ن', 'پ', 'ی', 'د', 'و', 'ن', 'ا', 'ه')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 153,
                titleLevel = 153,
                isLockLevel = true,
                question = "کدام گیاه است که یک حیوان درون نامش پنهان شده است؟",
                answer = "مارچوبه",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'م',
                    'ا',
                    'ر',
                    'چ',
                    'و',
                    'ب',
                    'ه',
                ),
                letters = arrayListOf('چ', 'ه', 'س', 'ب', 'د', 'چ', 'ی', 'ر', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 154,
                titleLevel = 154,
                isLockLevel = true,
                question = "یک استخر کوچک با دو لایه دیوار دور آن یکی سفید و نرم و دیگری تیره و سفت؟",
                answer = "نارگیل",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'ن',
                    'ا',
                    'ر',
                    'گ',
                    'ی',
                    'ل',
                ),
                letters = arrayListOf('چ', 'م', 'ل', 'د', 'گ', 'پ', 'ی', 'ر', 'و', 'غ', 'ا', 'ن')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 155,
                titleLevel = 155,
                isLockLevel = true,
                question = "آن کدام میوه است که در اسمش یکی از اجزای چهره پنهان شده است؟",
                answer = "آلبالو",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'آ',
                    'ل',
                    'ب',
                    'ا',
                    'ل',
                    'و',
                ),
                letters = arrayListOf('ل', 'م', 'ب', 'د', 'ا', 'پ', 'ی', 'ل', 'و', 'غ', 'ا', 'آ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 156,
                titleLevel = 156,
                isLockLevel = true,
                question = "آن کدام خوراکی است که سه حرف آخرش، نمی تواند حرف بزند؟",
                answer = "بلال",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ب',
                    'ل',
                    'ا',
                    'ل',
                ),
                letters = arrayListOf('ل', 'م', 'س', 'د', 'ل', 'پ', 'ی', 'ب', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 157,
                titleLevel = 157,
                isLockLevel = true,
                question = "کدام رنگ است که اگر حرف “ن” را به انتهای آن اضافه کنیم، می توانیم سوار آن شویم؟",
                answer = "ماشی",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'م',
                    'ا',
                    'ش',
                    'ی',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'ش', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 158,
                titleLevel = 158,
                isLockLevel = true,
                question = "آن چیست که هم در کولر است و هم در تلویزیون؟",
                answer = "کانال",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ک',
                    'ا',
                    'ن',
                    'ا',
                    'ل',
                ),
                letters = arrayListOf('چ', 'ل', 'س', 'د', 'د', 'ا', 'ی', 'ن', 'و', 'غ', 'ا', 'ک')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 159,
                titleLevel = 159,
                isLockLevel = true,
                question = "کدام سیاره است که سـه حـرف اولـش یک عضو بدن است؟",
                answer = "مریخ",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'م',
                    'ر',
                    'ی',
                    'خ',
                ),
                letters = arrayListOf('چ', 'م', 'خ', 'د', 'د', 'پ', 'ی', 'ر', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 160,
                titleLevel = 160,
                isLockLevel = true,
                question = "کدام عضو بدن است که دو حرف اولش مایه حیات است؟",
                answer = "ابرو",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ا',
                    'ب',
                    'ر',
                    'و',
                ),
                letters = arrayListOf('چ', 'م', 'ر', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'ب')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 161,
                titleLevel = 161,
                isLockLevel = true,
                question = "اون چیه که هم تو ماشین هست هم تو انسان؟",
                answer = "دنده",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'د',
                    'ن',
                    'د',
                    'ه',
                ),
                letters = arrayListOf('چ', 'ه', 'س', 'د', 'د', 'پ', 'ی', 'ز', 'ن', 'غ', 'ا', 'ل')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 162,
                titleLevel = 162,
                isLockLevel = true,
                question = "آن کدام خوراکی است که سه حرف اولش نپخته است؟",
                answer = "خامه",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'خ',
                    'ا',
                    'م',
                    'ه',
                ),
                letters = arrayListOf('ه', 'م', 'س', 'د', 'د', 'م', 'ی', 'ز', 'و', 'غ', 'ا', 'خ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 163,
                titleLevel = 163,
                isLockLevel = true,
                question = "کدام اسم دختر است که اگر حرف آخر آن را برداریم اسم یک پرنده میشود؟",
                answer = "سارا",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'س',
                    'ا',
                    'ر',
                    'ا',
                ),
                letters = arrayListOf('ا', 'م', 'س', 'ر', 'د', 'پ', 'ی', 'ز', 'س', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 164,
                titleLevel = 164,
                isLockLevel = true,
                question = "آن چیست که دَم دارد و نَم دارد و دیگی به شکم دارد؟",
                answer = "سماور",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'س',
                    'م',
                    'ا',
                    'و',
                    'ر',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ر', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 165,
                titleLevel = 165,
                isLockLevel = true,
                question = "اون چیه که شب ها بدون صدا بیرون میاد و روزها دزدیده میشه؟",
                answer = "ستاره",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'س',
                    'ت',
                    'ا',
                    'ر',
                    'ه',
                ),
                letters = arrayListOf('ه', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ر', 'و', 'غ', 'ا', 'ت')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 166,
                titleLevel = 166,
                isLockLevel = true,
                question = "کدام شغل است که دو حرف اولش را پرندگان دارند و سه حرف آخرش را عنکبوت میسازد؟",
                answer = "پرستار",
                sizeAnswer = 6,
                listAnswer = arrayListOf(
                    'پ',
                    'ر',
                    'س',
                    'ت',
                    'ا',
                    'ر',
                ),
                letters = arrayListOf('ر', 'م', 'س', 'د', 'د', 'ت', 'ی', 'ر', 'و', 'غ', 'ا', 'پ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 100,
                titleLevel = 100,
                isLockLevel = true,
                question = "اون چه پرنده ایه که اگه بیوه بشه کچل میشه؟",
                answer = "طاووس",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ط',
                    'ا',
                    'و',
                    'و',
                    'س',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'و', 'و', 'غ', 'ا', 'ط')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 168,
                titleLevel = 168,
                isLockLevel = true,
                question = "یکی از تجهیزات هواپیما که از هر طرف بخوانیم خودش میشود!!",
                answer = "رادار",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ر',
                    'ا',
                    'د',
                    'ا',
                    'ر',
                ),
                letters = arrayListOf('چ', 'ر', 'س', 'ا', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'ر')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 169,
                titleLevel = 169,
                isLockLevel = true,
                question = "آن چه شهری است که سرخ است اما مردمش سیاه!",
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
                letters = arrayListOf('ه', 'م', 'ن', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'ن', 'ا', 'ه')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 170,
                titleLevel = 170,
                isLockLevel = true,
                question = "آن چیست که هم وسیله ای در حمام است و هم یک عدد؟",
                answer = "وان",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'و',
                    'ا',
                    'ن',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'ن', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 171,
                titleLevel = 171,
                isLockLevel = true,
                question = "اون چه شهریه اگه برعکسش کنی برند آدامس میشه؟",
                answer = "کیش",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ک',
                    'ی',
                    'ش',
                ),
                letters = arrayListOf('چ', 'م', 'ش', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'ک', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 172,
                titleLevel = 172,
                isLockLevel = true,
                question = "میتونی اسم یه نوع سبزی که توی این ۶ حرف پنهان شده رو حدس بزنی؟",
                answer = "شوید",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ش',
                    'و',
                    'ی',
                    'د',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 173,
                titleLevel = 173,
                isLockLevel = true,
                question = "عددی کمتر از ۱۰۰ را بیابید که با معکوس شدن ارقام آن، یک پنجم مقدار آن به آن اضافه شود.",
                answer = "عدد45",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ع',
                    'د',
                    'د',
                    '4',
                    '5',
                ),
                letters = arrayListOf('2', '4', '6', '1', '3', '5', '7', '8', '0', 'د', 'د', 'ع')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 174,
                titleLevel = 174,
                isLockLevel = true,
                question = "اون چیه که خیلی می چسبه؟",
                answer = "چسب",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'چ',
                    'س',
                    'ب',
                ),
                letters = arrayListOf('چ', 'ب', 'س', 'د', 'د', 'س', 'ی', 'ز', 'و', 'غ', 'ا', 'چ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 175,
                titleLevel = 175,
                isLockLevel = true,
                question = "اگر دو تا هزار پا همدیگر رو بغل کنند چی می شه؟",
                answer = "زیپ",
                sizeAnswer = 3,
                listAnswer = arrayListOf(
                    'ز',
                    'ی',
                    'پ',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 176,
                titleLevel = 176,
                isLockLevel = true,
                question = "بچه ی شیر و پلنگ چی میشه؟",
                answer = "شیلنگ",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ش',
                    'ی',
                    'ل',
                    'ن',
                    'گ',
                ),
                letters = arrayListOf('گ', 'م', 'س', 'ن', 'د', 'ل', 'ی', 'ز', 'و', 'غ', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 177,
                titleLevel = 177,
                isLockLevel = true,
                question = "اگر سر پرگار گیج برود چه میکشد؟",
                answer = "بیضی",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ب',
                    'ی',
                    'ض',
                    'ی',
                ),
                letters = arrayListOf('ی', 'م', 'ز', 'ض', 'د', 'پ', 'ی', 'ظ', 'و', 'غ', 'ا', 'ب')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 178,
                titleLevel = 178,
                isLockLevel = true,
                question = "هم بادکنک، هم خوردنی! اولش بامزه، آخرش بی مزه!",
                answer = "آدامس",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'آ',
                    'د',
                    'ا',
                    'م',
                    'س',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'م', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'آ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 179,
                titleLevel = 179,
                isLockLevel = true,
                question = "برای خوردن من رو میخرید ولی هیچوقت من رو نمیخورید. من چی ام؟",
                answer = "بشقاب",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ب',
                    'ش',
                    'ق',
                    'ا',
                    'ب',
                ),
                letters = arrayListOf('چ', 'م', 'ش', 'د', 'د', 'ب', 'ی', 'ز', 'ق', 'غ', 'ا', 'ب')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 180,
                titleLevel = 180,
                isLockLevel = true,
                question = "آن چیست که موهای طلایی دارد و در گوشه ای ایستاده است؟",
                answer = "جارو",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ج',
                    'ا',
                    'ر',
                    'و',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ر', 'و', 'غ', 'ا', 'ج')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 181,
                titleLevel = 181,
                isLockLevel = true,
                question = "یک خانه وجود دارد. شخصی نابینا وارد آن میشود و بینا از آن خارج میشود! آن چیست؟",
                answer = "مدرسه",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'م',
                    'د',
                    'ر',
                    'س',
                    'ه',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ه', 'ر', 'پ', 'ی', 'ز', 'د', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 182,
                titleLevel = 182,
                isLockLevel = true,
                question = "من زندگی ندارم، اما عمرم می تونه تمام بشه. من چی هستم؟",
                answer = "باتری",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ب',
                    'ا',
                    'ت',
                    'ر',
                    'ی',
                ),
                letters = arrayListOf('چ', 'م', 'ر', 'د', 'د', 'ب', 'ی', 'ز', 'ت', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 183,
                titleLevel = 183,
                isLockLevel = true,
                question = "من پر از سوزن هستم، اما نمی توانم خیاطی کنم. من چی هستم؟",
                answer = "درخت کاج",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'د',
                    'ر',
                    'خ',
                    'ت',
                    'ک',
                    'ا',
                    'ج',
                ),
                letters = arrayListOf('خ', 'م', 'ت', 'د', 'ر', 'پ', 'ی', 'ز', 'و', 'ک', 'ا', 'ج')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 184,
                titleLevel = 184,
                isLockLevel = true,
                question = "وقتی راه می روم می پرم و وقتی می ایستم می نشینم. من چه حیوانی هستم؟",
                answer = "کانگورو",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'ک',
                    'ا',
                    'ن',
                    'گ',
                    'و',
                    'ر',
                    'و',
                ),
                letters = arrayListOf('چ', 'م', 'و', 'ر', 'د', 'پ', 'گ', 'ز', 'و', 'ک', 'ا', 'ن')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 185,
                titleLevel = 185,
                isLockLevel = true,
                question = "آن چیست که لباس سیاهی به تن دارد و کلاه سبزی به سر؟",
                answer = "بادمجان",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'ب',
                    'ا',
                    'د',
                    'م',
                    'ج',
                    'ا',
                    'ن',
                ),
                letters = arrayListOf('ن', 'م', 'ا', 'د', 'د', 'پ', 'ج', 'ز', 'و', 'ب', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 186,
                titleLevel = 186,
                isLockLevel = true,
                question = "کجا از یک سوراخ وارد می شوید و از دو سوراخ خارج می شوید؟",
                answer = "شلوار",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ش',
                    'ل',
                    'و',
                    'ا',
                    'ر',
                ),
                letters = arrayListOf('چ', 'م', 'ش', 'د', 'د', 'ل', 'ی', 'ر', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 187,
                titleLevel = 187,
                isLockLevel = true,
                question = "من شهر دارم اما خانه ندارم. من جنگل دارم، اما درخت ندارم. من آب دارم اما ماهی ندارم. من چی هستم؟",
                answer = "نقشه",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ن',
                    'ق',
                    'ش',
                    'ه',
                ),
                letters = arrayListOf('ه', 'م', 'ش', 'د', 'د', 'پ', 'ق', 'ز', 'و', 'غ', 'ا', 'ن')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 188,
                titleLevel = 188,
                isLockLevel = true,
                question = "اون چیه که به اندازه فیل بزرگ است اما وزنی ندارد؟",
                answer = "سایه فیل",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'س',
                    'ا',
                    'ی',
                    'ه',
                    'ف',
                    'ی',
                    'ل',
                ),
                letters = arrayListOf('ی', 'م', 'س', 'د', 'ف', 'ه', 'ی', 'ز', 'و', 'غ', 'ا', 'ل')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 189,
                titleLevel = 189,
                isLockLevel = true,
                question = "موجود سرد و بی جان. گیرد ولی دوصد جان. دهان تنگ و تاریک. گردن دراز و باریک.",
                answer = "تفنگ",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ت',
                    'ف',
                    'ن',
                    'گ',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'گ', 'د', 'پ', 'ن', 'ز', 'ف', 'غ', 'ت', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 190,
                titleLevel = 190,
                isLockLevel = true,
                question = "نام گلی است که باحذف کردن حرف اولش روی آب شناورمی شود؟",
                answer = "شقایق",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ش',
                    'ق',
                    'ا',
                    'ی',
                    'ق',
                ),
                letters = arrayListOf('ش', 'م', 'ق', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'ق', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 191,
                titleLevel = 191,
                isLockLevel = true,
                question = "یک بار می چرخم، آن چه بیرون است داخل نخواهد شد. یک بار دیگر می چرخم، آن چه داخل است بیرون نخواهد شد. من چه هستم؟",
                answer = "کلید",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ک',
                    'ل',
                    'ی',
                    'د',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ز', 'ل', 'غ', 'ا', 'ک')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 192,
                titleLevel = 192,
                isLockLevel = true,
                question = "آن چیست که هرقدر از آن برداریم، بزرگ تر میشود؟",
                answer = "چاله",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'چ',
                    'ا',
                    'ل',
                    'ه',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'ه', 'د', 'ل', 'ی', 'ز', 'و', 'غ', 'ا', 'چ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 193,
                titleLevel = 193,
                isLockLevel = true,
                question = "آن چیست که بالا و پایین میرود اما هرگز یک سانت هم از جای خود جابجا نمیشود؟",
                answer = "پله برقی",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'پ',
                    'ل',
                    'ه',
                    'ب',
                    'ر',
                    'ق',
                    'ی'
                ),
                letters = arrayListOf('چ', 'ی', 'ق', 'د', 'ر', 'پ', 'ب', 'ز', 'و', 'ل', 'ه', 'پ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 194,
                titleLevel = 194,
                isLockLevel = true,
                question = "آن چیست که دو دست دارد اما نمیتواند دست بزند؟",
                answer = "ساعت",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'س',
                    'ا',
                    'ع',
                    'ت',
                ),
                letters = arrayListOf('چ', 'م', 'ت', 'د', 'د', 'ع', 'ی', 'ز', 'و', 'غ', 'ا', 'س')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 195,
                titleLevel = 195,
                isLockLevel = true,
                question = "آن کدام جنگ است که اسب ها در آسمان اش پرواز میکنند؟",
                answer = "شطرنج",
                sizeAnswer = 5,
                listAnswer = arrayListOf(
                    'ش',
                    'ط',
                    'ر',
                    'ن',
                    'ج',
                ),
                letters = arrayListOf('چ', 'م', 'ج', 'د', 'د', 'ن', 'ی', 'ر', 'و', 'ط', 'ا', 'ش')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 196,
                titleLevel = 196,
                isLockLevel = true,
                question = "به محل ملاقات دو دیوار چه میگویند؟",
                answer = "گوشه",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'گ',
                    'و',
                    'ش',
                    'ه',
                ),
                letters = arrayListOf('چ', 'ه', 'س', 'د', 'ش', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'گ')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 197,
                titleLevel = 197,
                isLockLevel = true,
                question = "بازاری است تو در تو؛ اول چرم فروشان، سپس پرده فروشان، و در دل بازار، یاقوت فروشان قرار دارند. آن چیست؟",
                answer = "انار",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ا',
                    'ن',
                    'ا',
                    'ر',
                ),
                letters = arrayListOf('چ', 'م', 'ر', 'د', 'ا', 'پ', 'ی', 'ن', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 198,
                titleLevel = 198,
                isLockLevel = true,
                question = "آن چیست که هرچه بیشتر استفاده می کنید، کوچکتر می شود؟",
                answer = "مداد",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'م',
                    'د',
                    'ا',
                    'د',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ز', 'و', 'غ', 'ا', 'م')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 199,
                titleLevel = 199,
                isLockLevel = true,
                question = "آن چیست که می تواند بدون سوزاندن شما را گرم کند؟",
                answer = "لباس",
                sizeAnswer = 4,
                listAnswer = arrayListOf(
                    'ل',
                    'ب',
                    'ا',
                    'س',
                ),
                letters = arrayListOf('چ', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ب', 'و', 'غ', 'ا', 'ل')
            )
        )
        dataBase.levels().saveDataLevel(
            LevelModel(
                id = 200,
                titleLevel = 200,
                isLockLevel = true,
                question = "ساعت آفتابی کمترین تعداد قطعات متحرک را در بین انواع ساعت دارد. کدام ساعت بیشترین قطعات متحرک را دارد؟",
                answer = "ساعت شنی",
                sizeAnswer = 7,
                listAnswer = arrayListOf(
                    'س',
                    'ا',
                    'ع',
                    'ت',
                    'ش',
                    'ن',
                    'ی',
                ),
                letters = arrayListOf('ش', 'م', 'س', 'د', 'د', 'پ', 'ی', 'ت', 'و', 'ع', 'ا', 'ن')
            )
        )

    }

    private fun insertDataToDbSetting() {
        dataBase = AppDataBase.getDatabase(this)
        dataBase.setting().saveDataSetting(SettingModel(1, playMusic = true, playVolume = true))
    }

}