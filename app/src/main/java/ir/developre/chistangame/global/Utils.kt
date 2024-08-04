package ir.developre.chistangame.global

object Utils {
    var playMusic = true
    var playVolume = true
    const val STATUS_LOGIN = "statusLogin"
    var isAllEditTextsFilled = false
    var back_from_tapsell = false

    const val PACKAGE_NAME = "ir.developre.chistangame"
    const val LINK_SHARED_APP = "https://cafebazaar.ir/app/ir.developre.chistangame"

    /*currentLevel:
    این زمانی پر میشه که کاربر روی لول مورد نظر کلیک کنه و زمانی که لول مورد نظر رو درست بره
    یکی به کارنت لول اضافه میشه و میره لول بعدی
     */
    var currentLevel = 0
    const val LAST_LEVEL = 100

    //................coin.................

    const val ENOUGH_COIN_FOR_CONTINUE_GAME = 8
    const val BASE_COIN = 30
    const val NUMBER_OF_COIN_FOR_WRONG_ANSWER = 8
    const val NUMBER_OF_COIN_FOR_CORRECT_ANSWER = 4
    const val NUMBER_OF_COIN_FOR_CORRECT_ANSWER_AND_SEEING_ADS = 8
    const val NUMBER_OF_COIN_FOR_HELP = 3

    const val NUMBER_OF_COINS_FOR_SEEING_ADS_TO_SHOP = 10


    //................tapsell.................

    const val KEY_TAPSELL = "lnhobniddjmbjkbdphrbpqgkemmanmsmtrkfphfdaqirgmpiappllcbdetneohidgsiaof"
    const val TAPSELL_GIFT_VIDEO_KEY = "66a0fa46c975af22e3bc6f62"

    //...................poolakey
    const val rsaPublicKey =
        "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDCjjce+d+Z+uvqokEpSVNEW1Q8OW9ekRuGuwktuh0DWfWy8EbVTnHeDAVcFrHnQ9k938JyP5J9nQc17CK+ERxxPwMsjATts7XtdAfEaBf2SUJ5Q4XKJkxXmnsOIhBWIgF0MW/5UIb94t9foqWEY3XdifcK64M5qdUO6j+/fSHoBQvzjhyFjK/KQHKXzPLn6M2OYhNvdgH2WHetK+e5KvL+Z+t8dNoy0Cj4giDIsYECAwEAAQ=="
    const val productId_50 = "coin1"
    const val productId_80 = "coin2"
    const val productId_100 = "coin3"
    const val productId_150 = "coin4"
    const val productId_300 = "coin5"


}