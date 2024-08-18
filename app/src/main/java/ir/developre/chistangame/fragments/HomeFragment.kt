package ir.developre.chistangame.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import ir.developre.chistangame.sharedPref.SharedPreferencesGame


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialog: Dialog
    private lateinit var dialogSetting: Dialog
    private lateinit var dataBase: AppDataBase
    private lateinit var animationLoadingWaiting: AnimationLoadingWaiting
    private var checkOpenDialog = true
    private var checkOpenDialog2 = true


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
        getDataSetting()
        clickOnLayoutIncludeIncreaseRuby()
        clickBtnStartGame()
        clickBtnSetting()
        clickBtnShop()
        clickBtnAboutUs()
        clickBtnShareApp()
    }

    private fun getDataSetting() {
        dataBase = AppDataBase.getDatabase(requireActivity())
        val setting = dataBase.setting().readDataSetting()
        Utils.playVolume = setting.playVolume
        Utils.playMusic = setting.playMusic
    }

    private fun clickBtnShareApp() {
        binding.btnShareApp.setOnClickListener {
            // متنی که می‌خواهید ارسال کنید
            val messageText =
                "با نصب اپلیکیشن چیستان باز، به دنیایی از چالش های ذهنی قدم بگذارید و از حل معماهای جذاب لذت ببرید! آماده اید که هوش خود را محک بزنید؟\n\n${Utils.LINK_SHARED_APP}"

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
        if (checkOpenDialog) {
            checkOpenDialog = false

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
                checkOpenDialog = true
                dialogSetting.dismiss()
                updateDataBaseSetting()
            }

            dialogSetting.setOnDismissListener {
                checkOpenDialog = true
                updateDataBaseSetting()
            }

            dialogSetting.show()
        }
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
        if (checkOpenDialog2) {
            checkOpenDialog2 = false

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

            btnCansel.setOnClickListener {
                dialog.dismiss()
                checkOpenDialog2 = true
            }

            btnClose.setOnClickListener {
                dialog.dismiss()
                checkOpenDialog2 = true
            }

            btnOk.setOnClickListener {
                checkOpenDialog2 = true
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
    }

    private fun deleteDatabase() {
        dataBase.user().updateDataUser(UserModel(id = 1, coin = Utils.BASE_COIN))
        dataBase.setting()
            .updateDataSetting(SettingModel(id = 1, playMusic = true, playVolume = true))
        val level = dataBase.levels().readDataLevel()
        level.forEach {
            if (it.id == 1) {
                dataBase.levels().updateDataLevel(
                    LevelModel(
                        id = it.id,
                        titleLevel = it.titleLevel,
                        isLockLevel = false,
                        question = it.question,
                        answer = it.answer,
                        sizeAnswer = it.sizeAnswer,
                        listAnswer = it.listAnswer,
                        letters = it.letters,
                        isFinishedLevel = false
                    )
                )
            }
            if (it.id != 1) {
                dataBase.levels().updateDataLevel(
                    LevelModel(
                        id = it.id,
                        titleLevel = it.titleLevel,
                        isLockLevel = true,
                        question = it.question,
                        answer = it.answer,
                        sizeAnswer = it.sizeAnswer,
                        listAnswer = it.listAnswer,
                        letters = it.letters,
                        isFinishedLevel = false
                    )
                )
            }
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
        dialogShop.showDialog(requireActivity().activityResultRegistry)
    }

    private fun clickBtnAboutUs() {
        binding.btnAboutUs.setOnClickListener { dialogAboutUs() }
    }

    private fun dialogAboutUs() {
        if (checkOpenDialog) {
            checkOpenDialog = false

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
                checkOpenDialog = true
                dialog.dismiss()
            }
            val btnEmail = dialog.findViewById<View>(R.id.btn_email)

            btnEmail.setOnClickListener {
                checkOpenDialog = true
                sendProblemWithEmail()
            }

            dialog.setOnDismissListener {
                checkOpenDialog = true
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun sendProblemWithEmail() {
        val uri = Uri.parse("mailto:" + "saraaasaber77@gmail.com")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
        intent.putExtra(Intent.EXTRA_TEXT, "your_text");
        startActivity(intent)
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

    private lateinit var sharedPreferencesGame: SharedPreferencesGame

    private fun checkEnterToAppForFirst(): Boolean {
        sharedPreferencesGame = SharedPreferencesGame(requireContext())
        val result = sharedPreferencesGame.readStatusFirst()
        return result
    }

    private fun saveEnterToAppForFirst() {
        sharedPreferencesGame = SharedPreferencesGame(requireContext())
        sharedPreferencesGame.saveStatusFirst(true)
        btnScoringApp()
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

                if (!checkEnterToAppForFirst()) {
                    saveEnterToAppForFirst()
                }

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