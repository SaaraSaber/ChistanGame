package ir.developre.chistangame

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.developre.chistangame.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickBtnStartGame()
        clickBtnSetting()
        clickBtnShop()
        clickBtnAboutUs()
    }

    private fun clickBtnStartGame() {
        binding.btnStartGame.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_levelsFragment)
        }
    }

    private fun clickBtnSetting() {
        binding.btnSetting.setOnClickListener {
            dialogSetting()
        }
    }

    private fun dialogSetting() {
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.layout_dialog_setting)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )

//        val layoutMusic = dialog.findViewById<View>(R.id.layout_music)
//        val layoutVolume = dialog.findViewById<View>(R.id.layout_volume)
        val btnClose = dialog.findViewById<View>(R.id.btn_close)
        val btnMusic = dialog.findViewById<AppCompatImageButton>(R.id.btn_music)
        val btnVolume = dialog.findViewById<AppCompatImageButton>(R.id.btn_volume)
        val btnRestart = dialog.findViewById<View>(R.id.layout_restart)

        btnMusic.setOnClickListener {
            if (Utils.playMusic) {
                btnMusic.setImageResource(R.drawable.vector_no_music)
                Utils.playMusic = false
            } else {
                btnMusic.setImageResource(R.drawable.vector_music)
                Utils.playMusic = true
            }
        }

        btnVolume.setOnClickListener {
            if (Utils.playVolume) {
                btnVolume.setImageResource(R.drawable.vector_no_volume)
                Utils.playVolume = false
            } else {
                btnVolume.setImageResource(R.drawable.vector_volume)
                Utils.playVolume = true
            }
        }

        btnRestart.setOnClickListener {
            dialogRestart()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
    }

    private fun dialogRestart() {
        dialog = Dialog(requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_dialog_question)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        dialog.show()
    }

    private fun clickBtnShop() {
        binding.btnShop.setOnClickListener {
            dialogShop()
        }
    }

    private fun dialogShop() {
        dialog = Dialog(requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_dialog_shop)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        dialog.show()
    }

    private fun clickBtnAboutUs() {
        binding.btnAboutUs.setOnClickListener { dialogAboutUs() }
    }

    private fun dialogAboutUs() {
        dialog = Dialog(requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_dialog_about_us)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        dialog.show()
    }


}