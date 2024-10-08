package ir.developre.chistangame.global

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.TextView
import ir.developre.chistangame.R
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.model.UserModel
import ir.tapsell.plus.AdRequestCallback
import ir.tapsell.plus.AdShowListener
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.TapsellPlusInitListener
import ir.tapsell.plus.model.AdNetworkError
import ir.tapsell.plus.model.AdNetworks
import ir.tapsell.plus.model.TapsellPlusAdModel
import ir.tapsell.plus.model.TapsellPlusErrorModel


class TapsellShop(val context: Activity) {

    private val dataBase by lazy { AppDataBase.getDatabase(context) }
    private val customToastGame by lazy { CustomToast(context) }
    private lateinit var animationLoadingWaiting: AnimationLoadingWaiting
    var checkFinishedAd = false

    fun connectToTapsell() {
        try {
            animationLoadingWaiting = AnimationLoadingWaiting(context)

            TapsellPlus.initialize(context, Utils.KEY_TAPSELL,
                object : TapsellPlusInitListener {
                    override fun onInitializeSuccess(adNetworks: AdNetworks) {
                        Log.d("onInitializeSuccess", adNetworks.name)
                    }

                    override fun onInitializeFailed(
                        adNetworks: AdNetworks,
                        adNetworkError: AdNetworkError
                    ) {
                        Log.e(
                            "onInitializeFailed",
                            "ad network: ${adNetworks.name}, error: ${adNetworkError.errorMessage}"
                        )
                        animationLoadingWaiting.dismissLoading()
                    }
                }
            )
            TapsellPlus.setGDPRConsent(context, true)
            TapsellPlus.setDebugMode(Log.DEBUG)

        } catch (t: Throwable) {
            customToastGame.customToast(
                R.drawable.simple_shape_background_toast_error,
                R.drawable.vector_close_circle,
                context.getString(R.string.not_ad)
            )
        }
    }


    fun requestAdGift() {
        try {
            TapsellPlus.requestRewardedVideoAd(
                context as Activity?,
                Utils.TAPSELL_GIFT_VIDEO_KEY,
                object : AdRequestCallback() {
                    override fun response(tapsellPlusAdModel: TapsellPlusAdModel) {
                        super.response(tapsellPlusAdModel)

                        // Ad is ready to show
                        // Put the ad's responseId to your responseId variable
                        val rewardedResponseId = tapsellPlusAdModel.responseId
                        animationLoadingWaiting.dismissLoading()
                        showAdGift(context, rewardedResponseId)

                    }

                    override fun error(message: String) {
                        Log.i("showAd", "error: $message")

                        animationLoadingWaiting.dismissLoading()

                        customToastGame.customToast(
                            R.drawable.simple_shape_background_toast_error,
                            R.drawable.vector_close_circle,
                            context.getString(R.string.not_ad)
                        )
                        // Handle error if needed
                    }
                }
            )
        } catch (t: Throwable) {
            customToastGame.customToast(
                R.drawable.simple_shape_background_toast_error,
                R.drawable.vector_close_circle,
                context.getString(R.string.not_ad)
            )
        }
    }


    private var numberCoin = 0
    private fun showAdGift(context1: Context, responseId: String) {
        try {
            //گرفتن تعداد سکه فعلی کاربر
            numberCoin = dataBase.user().readDataUser().coin

            TapsellPlus.showRewardedVideoAd(
                context1 as Activity?, responseId,
                object : AdShowListener() {
                    override fun onOpened(tapsellPlusAdModel: TapsellPlusAdModel) {
                        super.onOpened(tapsellPlusAdModel)
                        Log.i("showAd", "onOpened: ")
                    }

                    override fun onClosed(tapsellPlusAdModel: TapsellPlusAdModel) {
                        super.onClosed(tapsellPlusAdModel)
                        Log.i("showAd", "onClosed: ")
                        if (checkFinishedAd) {

                            updateTableUser()

                            checkFinishedAd = false
                        } else {
                            customToastGame.customToast(
                                R.drawable.simple_shape_background_toast_warning,
                                R.drawable.vector_warning_circle,
                                context.getString(R.string.w_see_end_Ad)
                            )
                        }
                    }

                    override fun onRewarded(tapsellPlusAdModel: TapsellPlusAdModel) {
                        super.onRewarded(tapsellPlusAdModel)
                        Log.i("showAd", "onRewarded: ")

                        checkFinishedAd = true

                    }

                    override fun onError(tapsellPlusErrorModel: TapsellPlusErrorModel) {
                        super.onError(tapsellPlusErrorModel)

                        Log.i("showAd", "onError: ")
                    }
                }
            )
        } catch (t: Throwable) {
            customToastGame.customToast(
                R.drawable.simple_shape_background_toast_error,
                R.drawable.vector_close_circle,
                context.getString(R.string.not_ad)
            )
        }
    }


    private lateinit var txtCoinHeader: TextView

    private fun updateTableUser() {
        //سکه جدید کاربر
        val newCreditUser = numberCoin + Utils.NUMBER_OF_COINS_FOR_SEEING_ADS_TO_SHOP

        customToastGame.customToast(
            R.drawable.simple_shape_background_toast_info,
            R.drawable.vector_info_circle,
            "${Utils.NUMBER_OF_COINS_FOR_SEEING_ADS_TO_SHOP} یاقوت به شما اضافه شد."
        )

        txtCoinHeader = context.findViewById(R.id.text_coin)
        txtCoinHeader.text = newCreditUser.toString()

        //اپدیت کردن جدول user
        dataBase.user().updateDataUser(UserModel(id = 1, coin = newCreditUser))

    }

}
