package ir.developre.chistangame.global

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultRegistry
import ir.cafebazaar.poolakey.Connection
import ir.cafebazaar.poolakey.ConnectionState
import ir.cafebazaar.poolakey.Payment
import ir.cafebazaar.poolakey.config.PaymentConfiguration
import ir.cafebazaar.poolakey.config.SecurityCheck
import ir.cafebazaar.poolakey.exception.DynamicPriceNotSupportedException
import ir.cafebazaar.poolakey.request.PurchaseRequest
import ir.developre.chistangame.R
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.model.UserModel

class DialogShop(val context: Activity) : Dialog(context) {

    private lateinit var dialog: Dialog
    private lateinit var tapsellShop: TapsellShop
    private val customToast by lazy { CustomToast(context) }
    private var checkOpenDialog = true
    private val paymentConfiguration = PaymentConfiguration(
        localSecurityCheck = SecurityCheck.Enable(rsaPublicKey = Utils.rsaPublicKey)
    )
    private val payment by lazy(LazyThreadSafetyMode.NONE) {
        Payment(context = context, config = paymentConfiguration)
    }
    private lateinit var paymentConnection: Connection
    private var coin50 = false
    private var coin80 = false
    private var coin100 = false
    private var coin150 = false
    private var coin300 = false


    fun showDialog(activityResultRegistry: ActivityResultRegistry) {
        if (checkOpenDialog) {
            checkOpenDialog = false
            startPaymentConnection()
            dialog = Dialog(context)
            dialog.setContentView(R.layout.layout_dialog_shop)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.CENTER)
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            dialog.setCancelable(false)
            val btnClose = dialog.findViewById<View>(R.id.btn_close)
            val btnSeeAds = dialog.findViewById<View>(R.id.btn_see_ad)
            val btnBuyCoinOne = dialog.findViewById<View>(R.id.btn_buy_coin_one)
            val btnBuyCoinTwo = dialog.findViewById<View>(R.id.btn_buy_coin_two)
            val btnBuyCoinThree = dialog.findViewById<View>(R.id.btn_buy_coin_three)
            val btnBuyCoinFour = dialog.findViewById<View>(R.id.btn_buy_coin_four)
            val btnBuyCoinFive = dialog.findViewById<View>(R.id.btn_buy_coin_five)

            btnClose.setOnClickListener {
                paymentConnection.disconnect()
                checkOpenDialog = true
                dialog.dismiss()
            }

            btnSeeAds.setOnClickListener {
                if (CheckNetworkConnection.isOnline(context)) {
                    tapsellShop = TapsellShop(context as Activity)
                    tapsellShop.connectToTapsell()
                    tapsellShop.requestAdGift()

                } else {
                    customToast.customToast(
                        colorBackground = R.drawable.simple_shape_background_toast_error,
                        img = R.drawable.vector_close_circle,
                        message = context.getString(R.string.w_no_access_to_internet)
                    )
                }
            }

            btnBuyCoinOne.setOnClickListener {
                if (CheckNetworkConnection.isOnline(context)) {
                    if (paymentConnection.getState() == ConnectionState.Connected) {
                        purchaseProduct(
                            productId = Utils.productId_50,
                            payload = "payload",
                            "",
                            activityResultRegistry
                        )
                        coin50 = true
                    }
                } else {
                    customToast.customToast(
                        R.drawable.simple_shape_background_toast_error,
                        R.drawable.vector_close_circle,
                        context.getString(R.string.w_no_access_to_internet)
                    )
                }
            }

            btnBuyCoinTwo.setOnClickListener {
                if (CheckNetworkConnection.isOnline(context)) {
                    if (paymentConnection.getState() == ConnectionState.Connected) {
                        purchaseProduct(
                            productId = Utils.productId_80,
                            payload = "payload",
                            "", activityResultRegistry
                        )
                        coin80 = true
                    }
                } else {
                    customToast.customToast(
                        R.drawable.simple_shape_background_toast_error,
                        R.drawable.vector_close_circle,
                        context.getString(R.string.w_no_access_to_internet)
                    )
                }
            }

            btnBuyCoinThree.setOnClickListener {
                if (CheckNetworkConnection.isOnline(context)) {
                    if (paymentConnection.getState() == ConnectionState.Connected) {
                        purchaseProduct(
                            productId = Utils.productId_100,
                            payload = "payload",
                            "", activityResultRegistry
                        )
                        coin100 = true
                    }
                } else {
                    customToast.customToast(
                        R.drawable.simple_shape_background_toast_error,
                        R.drawable.vector_close_circle,
                        context.getString(R.string.w_no_access_to_internet)
                    )
                }
            }

            btnBuyCoinFour.setOnClickListener {
                if (CheckNetworkConnection.isOnline(context)) {
                    if (paymentConnection.getState() == ConnectionState.Connected) {
                        purchaseProduct(
                            productId = Utils.productId_150,
                            payload = "payload",
                            "", activityResultRegistry
                        )
                        coin150 = true
                    }
                } else {
                    customToast.customToast(
                        R.drawable.simple_shape_background_toast_error,
                        R.drawable.vector_close_circle,
                        context.getString(R.string.w_no_access_to_internet)
                    )
                }
            }

            btnBuyCoinFive.setOnClickListener {
                if (CheckNetworkConnection.isOnline(context)) {
                    if (paymentConnection.getState() == ConnectionState.Connected) {
                        purchaseProduct(
                            productId = Utils.productId_300,
                            payload = "payload",
                            "", activityResultRegistry
                        )
                        coin300 = true
                    }
                } else {
                    customToast.customToast(
                        R.drawable.simple_shape_background_toast_error,
                        R.drawable.vector_close_circle,
                        context.getString(R.string.w_no_access_to_internet)
                    )
                }
            }

            dialog.show()
        }
    }

    private fun startPaymentConnection() {
        paymentConnection = payment.connect {
            connectionSucceed {
                Log.i("pay", "startPaymentConnection: connectionSucceed")
            }
            connectionFailed {
                Log.i("pay", "startPaymentConnection: connectionFailed")
            }
            disconnected {
                Log.i("pay", "startPaymentConnection: disconnected")
            }
        }
    }

    private fun purchaseProduct(
        productId: String,
        payload: String,
        dynamicPriceToken: String?,
        activityResultRegistry: ActivityResultRegistry
    ) {

        val purchaseRequest = PurchaseRequest(
            productId = productId,
            payload = payload,
            dynamicPriceToken = dynamicPriceToken,
        )

        payment.purchaseProduct(
            registry = activityResultRegistry,
            request = purchaseRequest
        ) {
            purchaseFlowBegan {
                Log.i("pay", "purchaseProduct: purchaseFlowBegan")
            }
            failedToBeginFlow { throwable ->
                if (throwable is DynamicPriceNotSupportedException) {

                    Log.i("pay", "purchaseProduct: failedToBeginFlow ${throwable.message}")

                    purchaseProduct(productId, payload, null, activityResultRegistry)
                } else {
                    Log.i("pay", "purchaseProduct: failedToBeginFlow ${throwable.message}")

                }
            }
            purchaseSucceed { purchaseEntity ->

                consumePurchasedItem(purchaseEntity.purchaseToken)
                Log.i("pay", "purchaseSucceed ")
            }
            purchaseCanceled {
                Log.i("pay", "purchaseCanceled ")
            }
            purchaseFailed { throwable ->
                Log.i("pay", "purchaseFailed: throwable ${throwable.message}")

            }

        }
    }

    private fun consumePurchasedItem(purchaseToken: String) {
        payment.consumeProduct(purchaseToken) {
            consumeSucceed {
                Log.i("pay", "consumePurchasedItem: ")
                updateDatabaseUserAndCoin()
            }
            consumeFailed {
                Log.i("pay", "consumeFailed:  ${it.message}")
            }
        }
    }

    private fun updateDatabaseUserAndCoin() {
        if (coin50) {
            updateDatabaseUser(50)
        } else if (coin80) {
            updateDatabaseUser(80)
        } else if (coin100) {
            updateDatabaseUser(100)
        } else if (coin150) {
            updateDatabaseUser(150)
        } else if (coin300) {
            updateDatabaseUser(300)
        }
    }

    private lateinit var dataBase: AppDataBase
    private lateinit var txtRuby: TextView

    private fun updateDatabaseUser(ruby: Int) {
        txtRuby = context.findViewById(R.id.text_coin)

        dataBase = AppDataBase.getDatabase(context)
        val read = dataBase.user().readDataUser().coin
        val newRuby = read + ruby

        dataBase.user().updateDataUser(UserModel(id = 1, coin = newRuby))
        txtRuby.text = newRuby.toString()

        customToast.customToast(
            R.drawable.simple_shape_background_toast_info,
            R.drawable.vector_info_circle,
            "$ruby یاقوت به شما اضافه شد."
        )
    }

}