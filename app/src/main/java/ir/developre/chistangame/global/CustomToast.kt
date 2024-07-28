package ir.developre.chistangame.global

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import ir.developre.chistangame.R


class CustomToast(val context: Context) : Toast(context) {

    fun customToast(colorBackground: Int, img: Int, message: String) {
        val toast = Toast(context)

        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_custom_toast, null)

        val colorLayout = view.findViewById<LinearLayout>(R.id.bg_layout_costume_toast)
        val image = view.findViewById<View>(R.id.img_costume_toast)
        val text = view.findViewById<TextView>(R.id.txt_costume_toast)
        text.isSelected = true

        colorLayout.setBackgroundResource(colorBackground)
        image.setBackgroundResource(img)
        text.text = message

        this.cancel()
        toast.cancel()

        toast.view = view
        toast.setGravity(Gravity.TOP, 30, 30)
        toast.duration = LENGTH_LONG
        toast.show()

    }

}