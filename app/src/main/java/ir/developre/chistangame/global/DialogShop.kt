package ir.developre.chistangame.global

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import ir.developre.chistangame.R

class DialogShop(val context: Context) {

    private lateinit var dialog: Dialog
    private lateinit var tapsellGame: TapsellGame
    private val customToast by lazy { CustomToast(context) }

    fun showDialog() {
        dialog = Dialog(context)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
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

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnSeeAds.setOnClickListener {
            if (CheckNetworkConnection.isOnline(context)) {
                tapsellGame = TapsellGame(context as Activity)
                tapsellGame.connectToTapsell()
                tapsellGame.requestAdGift()

            } else {
                customToast.customToast(
                    colorBackground = R.drawable.simple_shape_background_toast_error,
                    img = R.drawable.vector_close_circle,
                    context.getString(R.string.w_no_access_to_internet)
                )
            }
        }

        dialog.show()
    }
}