package ir.developre.chistangame.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.app.ActivityCompat.finishAfterTransition
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.developre.chistangame.R
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.FragmentHomeBinding
import ir.developre.chistangame.global.AnimationLoadingWaiting
import ir.developre.chistangame.global.DialogShop
import ir.developre.chistangame.global.PlayMusicService
import ir.developre.chistangame.global.Utils
import ir.developre.chistangame.model.LevelModel
import ir.developre.chistangame.model.SettingModel
import ir.developre.chistangame.model.UserModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialog: Dialog
    private lateinit var dialogSetting: Dialog
    private lateinit var dataBase: AppDataBase
    private lateinit var animationLoadingWaiting: AnimationLoadingWaiting


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

        binding.layoutIncreaseRubyHome.btnBack.visibility = View.INVISIBLE

        getCoinFromDatabase()
        clickOnLayoutIncludeIncreaseRuby()
        clickBtnStartGame()
        clickBtnSetting()
        clickBtnShop()
        clickBtnAboutUs()
        clickBtnShareApp()
    }

    private fun clickBtnShareApp() {
        binding.btnShareApp.setOnClickListener {
            // متنی که می‌خواهید ارسال کنید
            val messageText = "${Utils.LINK_SHARED_APP}"

            // ایجاد Intent برای ارسال متن
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, messageText)
            }

            // اجرای Intent
            startActivity(Intent.createChooser(intent, "ارسال به:"))
        }
    }

    private fun getCoinFromDatabase() {
        val dataUser = dataBase.user().readDataUser()
        binding.layoutIncreaseRubyHome.textCoin.text = dataUser.coin.toString()
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
            dialogRestart(dialogSetting)
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

    private fun dialogRestart(dialogSetting: Dialog) {
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.layout_dialog_question)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )

        val btnOk = dialog.findViewById<View>(R.id.btn_ok)
        val btnCansel = dialog.findViewById<View>(R.id.btn_cansel)
        val btnClose = dialog.findViewById<View>(R.id.btn_close_question)

        btnCansel.setOnClickListener { dialog.dismiss() }

        btnClose.setOnClickListener { dialog.dismiss() }

        btnOk.setOnClickListener {
            animationLoadingWaiting = AnimationLoadingWaiting(requireActivity())

            deleteDatabase()
            dialog.dismiss()
            dialogSetting.dismiss()
            Utils.playMusic = true
            Utils.playVolume = true

            Handler(Looper.getMainLooper()).postDelayed({
                animationLoadingWaiting.dismissLoading()
                binding.layoutIncreaseRubyHome.textCoin.text = Utils.BASE_COIN.toString()
                requireActivity().startService(
                    Intent(
                        requireActivity(),
                        PlayMusicService::class.java
                    )
                )
            }, 3000)
        }

        dialog.show()
    }

    private fun deleteDatabase() {
        dataBase.user().updateDataUser(UserModel(id = 1, coin = Utils.BASE_COIN))
        dataBase.setting()
            .updateDataSetting(SettingModel(id = 1, playMusic = true, playVolume = true))
        val level = dataBase.levels().readDataLevel()
        level.forEach {
            if (it.id != 1)
                dataBase.levels().updateDataLevel(
                    LevelModel(
                        id = it.id,
                        titleLevel = it.titleLevel,
                        isLockLevel = true,
                        question = it.question,
                        answer = it.answer,
                        sizeAnswer = it.sizeAnswer,
                        listAnswer = it.listAnswer,
                        letters = it.letters
                    )
                )
        }
    }

    private fun clickBtnShop() {
        binding.btnShop.setOnClickListener {
            dialogShop()
        }
    }

    private lateinit var dialogShop: DialogShop

    private fun dialogShop() {
        dialogShop = DialogShop(requireActivity())
        dialogShop.showDialog()
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

    private lateinit var dialogExitApp: Dialog
    private fun dialogExitApp() {
        dialogExitApp = Dialog(requireActivity())
        dialogExitApp.setContentView(R.layout.layout_dialog_exit)
        dialogExitApp.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogExitApp.window!!.setGravity(Gravity.CENTER)
        dialogExitApp.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        val lp = dialogExitApp.window!!.attributes
        lp.dimAmount = 0.7f

        val btnClose = dialogExitApp.findViewById<View>(R.id.btn_close)!!
        val btnExit = dialogExitApp.findViewById<View>(R.id.btn_exit)!!
        val btnScoring = dialogExitApp.findViewById<View>(R.id.btn_scoring)!!

        btnClose.setOnClickListener { dialogExitApp.dismiss() }

        btnExit.setOnClickListener {
            dialogExitApp.dismiss()
            exitApp()
        }


        btnScoring.setOnClickListener { btnScoringApp() }

        dialogExitApp.show()
    }

    private fun exitApp() {
        Log.i("exitApp", "exitApp33: ")
        requireActivity().overridePendingTransition(
            R.anim.exit_anim,
            0
        ) // Disable default activity transition
        finishAfterTransition(requireActivity()) // Finish activity with exit animation
    }

    private fun btnScoringApp() {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.setData(Uri.parse("bazaar://details?id=" + Utils.PACKAGE_NAME))
        intent.setPackage("com.farsitel.bazaar")
        startActivity(intent)
    }

    private var doubleBackToExitPressedOnce = false
    override fun onResume() {
        super.onResume()

        if (view == null) {
            return
        }
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { _, keyCode, event ->
            if (event.action === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                // handle back button's click listener

                if (doubleBackToExitPressedOnce) {
//                    exitProcess(0)
                    exitApp()
                    return@setOnKeyListener true
                }

                doubleBackToExitPressedOnce = true

                dialogExitApp()

                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    doubleBackToExitPressedOnce = false
                }, 2000)

                true

            } else false
        }

    }


}