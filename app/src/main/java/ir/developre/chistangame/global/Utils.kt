package ir.developre.chistangame.global

object Utils {
    var playMusic = true
    var playVolume = true
    const val STATUS_LOGIN = "statusLogin"
    var isAllEditTextsFilled = false

    const val PACKAGE_NAME = "ir.developre.chistangame"
    const val LINK_SHARED_APP = "https://cafebazaar.ir/app/ir.arinaco.mafiagames"

    /*currentLevel:
    این زمانی پر میشه که کاربر روی لول مورد نظر کلیک کنه و زمانی که لول مورد نظر رو درست بره
    یکی به کارنت لول اضافه میشه و میره لول بعدی
     */
    var currentLevel = 0
    const val LAST_LEVEL = 5

    //................coin.................

    const val BASE_COIN = 30
    const val NUMBER_OF_COIN_FOR_WRONG_ANSWER = 7
    const val NUMBER_OF_COIN_FOR_CORRECT_ANSWER = 5
    const val NUMBER_OF_COIN_FOR_CORRECT_ANSWER_AND_SEEING_ADS = 10
    const val NUMBER_OF_COIN_FOR_HELP = 3

    const val NUMBER_OF_COINS_FOR_SEEING_ADS_TO_SHOP = 10
    const val NUMBER_BUY_COIN_SHOP_1 = 10
    const val NUMBER_BUY_COIN_SHOP_2 = 10
    const val NUMBER_BUY_COIN_SHOP_3 = 10
    const val NUMBER_BUY_COIN_SHOP_4 = 10
    const val NUMBER_BUY_COIN_SHOP_5 = 10


    //................tapsell.................

    const val KEY_TAPSELL = "lnhobniddjmbjkbdphrbpqgkemmanmsmtrkfphfdaqirgmpiappllcbdetneohidgsiaof"
    const val TAPSELL_GIFT_VIDEO_KEY = "66a0fa46c975af22e3bc6f62"
}