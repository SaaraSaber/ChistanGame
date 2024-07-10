package ir.developre.chistangame.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.developre.chistangame.R
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.FragmentHomeBinding
import ir.developre.chistangame.global.PlayMusicService
import ir.developre.chistangame.global.Utils
import ir.developre.chistangame.model.SettingModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialog: Dialog
    private lateinit var dialogSetting: Dialog
    private lateinit var dataBase: AppDataBase

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

        dataBase = AppDataBase.getDatabase(requireActivity())

        clickOnLayoutIncludeIncreaseRuby()
        clickBtnStartGame()
        clickBtnSetting()
        clickBtnShop()
        clickBtnAboutUs()
    }

    private fun clickOnLayoutIncludeIncreaseRuby() {
        binding.layoutIncreaseRubyHome.btnBuyRuby.setOnClickListener { dialogShop() }
        binding.layoutIncreaseRubyHome.layoutBuyRuby.setOnClickListener { dialogShop() }
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
        readDataBaseSetting()
        dialogSetting = Dialog(requireContext())
        dialogSetting.setContentView(R.layout.layout_dialog_setting)
        dialogSetting.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogSetting.window!!.setGravity(Gravity.CENTER)
        dialogSetting.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )

        val btnClose = dialogSetting.findViewById<View>(R.id.btn_close)
        val btnMusic = dialogSetting.findViewById<AppCompatImageButton>(R.id.btn_music)
        val btnVolume = dialogSetting.findViewById<AppCompatImageButton>(R.id.btn_volume)
        val btnRestart = dialogSetting.findViewById<View>(R.id.layout_restart)

        if (Utils.playMusic) {
            btnMusic.setImageResource(R.drawable.vector_music)
        } else {
            btnMusic.setImageResource(R.drawable.vector_no_music)
        }

        if (Utils.playVolume) {
            btnVolume.setImageResource(R.drawable.vector_volume)
        } else {
            btnVolume.setImageResource(R.drawable.vector_no_volume)
        }

        btnMusic.setOnClickListener {
            if (Utils.playMusic) {
                btnMusic.setImageResource(R.drawable.vector_no_music)
                Utils.playMusic = false
                requireActivity().stopService(
                    Intent(
                        requireActivity(),
                        PlayMusicService::class.java
                    )
                )
            } else {
                btnMusic.setImageResource(R.drawable.vector_music)
                Utils.playMusic = true
                requireActivity().startService(
                    Intent(
                        requireActivity(),
                        PlayMusicService::class.java
                    )
                )
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
            dialogSetting.dismiss()
            updateDataBaseSetting()
        }

        dialogSetting.setOnDismissListener {
            updateDataBaseSetting()
        }


        dialogSetting.show()
    }

    private fun readDataBaseSetting() {

        val playMusic = dataBase.setting().readDataSetting().playMusic
        val playVolume = dataBase.setting().readDataSetting().playVolume
        Utils.playMusic = playMusic
        Utils.playVolume = playVolume
    }

    private fun updateDataBaseSetting() {
        dataBase.setting().updateDataSetting(
            SettingModel(
                1,
                Utils.playMusic,
                Utils.playVolume
            )
        )
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
        val btnClose = dialog.findViewById<View>(R.id.btn_close)
        btnClose.setOnClickListener {
            dialog.dismiss()
            updateDataBaseSetting()
        }
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
        val btnClose = dialog.findViewById<View>(R.id.btn_close)
        btnClose.setOnClickListener {
            dialog.dismiss()
            updateDataBaseSetting()
        }
        val btnEmail = dialog.findViewById<View>(R.id.btn_email)

        btnEmail.setOnClickListener {
            sendProblemWithEmail()
        }

        dialog.show()
    }

    @SuppressLint("IntentReset")
    private fun sendProblemWithEmail() {
        val deviceModel = Build.MODEL
        val deviceMANUFACTURER = Build.MANUFACTURER
        val deviceSdk = Build.VERSION.SDK_INT
        val deviceBrand = Build.BRAND
        val deviceVersionCode = Build.VERSION.RELEASE

        val emailAddress = requireContext().getString(R.string.address_email)
        val emailSubject = requireContext().getString(R.string.problem_in_game)
        val emailText =
            "مشکل خود را در این قسمت توضیح دهید\n\nمدل گوشی: $deviceModel\nبرند گوشی: $deviceMANUFACTURER\nسازنده گوشی: $deviceBrand\nSDK: $deviceSdk\nVersionCode: $deviceVersionCode\n\n"

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.setType("text/plain")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailText)
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

}