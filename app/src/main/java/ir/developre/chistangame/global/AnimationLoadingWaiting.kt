package ir.developre.chistangame.global

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import ir.developre.chistangame.R

class AnimationLoadingWaiting(context: Context) {

    private val loading: Dialog = Dialog(context)

    init {
        loading.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.layout_loading_waiting)
            show()
        }

    }

    fun dismissLoading() {
        loading.dismiss()
    }

}