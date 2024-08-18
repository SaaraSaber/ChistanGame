package ir.developre.chistangame.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ir.cafebazaar.poolakey.Connection
import ir.cafebazaar.poolakey.ConnectionState
import ir.cafebazaar.poolakey.Payment
import ir.cafebazaar.poolakey.config.PaymentConfiguration
import ir.cafebazaar.poolakey.config.SecurityCheck
import ir.cafebazaar.poolakey.exception.DynamicPriceNotSupportedException
import ir.cafebazaar.poolakey.request.PurchaseRequest
import ir.developre.chistangame.R
import ir.developre.chistangame.adapter.AnswerAdapter
import ir.developre.chistangame.adapter.LetterAdapter
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.FragmentGameBinding
import ir.developre.chistangame.global.CheckNetworkConnection
import ir.developre.chistangame.global.CustomToast
import ir.developre.chistangame.global.DialogShop
import ir.developre.chistangame.global.TapsellShop
import ir.developre.chistangame.global.TapsellWinStage
import ir.developre.chistangame.global.Utils
import ir.developre.chistangame.model.AnswerModel
import ir.developre.chistangame.model.LetterModel
import ir.developre.chistangame.model.LevelModel
import ir.developre.chistangame.model.UserModel
import ir.developre.chistangame.my_interface.on_click.ClickOnAnswer
import ir.developre.chistangame.my_interface.on_click.ClickOnLetter


class GameFragment : Fragment(), ClickOnLetter, ClickOnAnswer {
    private lateinit var binding: FragmentGameBinding
    private lateinit var dataBase: AppDataBase
    private lateinit var readDatabaseLevel: List<LevelModel>
    private lateinit var answer: String
    private var sizeAnswer: Int = 0
    private lateinit var listLetterAdapter: ArrayList<LetterModel>
    private lateinit var listAnswerAdapter: ArrayList<AnswerModel>
    private lateinit var listLetter: ArrayList<Char>
    private lateinit var listAnswer: ArrayList<Char>
    private val listAnswerUser by lazy { ArrayList<Char>() }
    private var currentTextIndex = 0
    private lateinit var adapterLetter: LetterAdapter
    private lateinit var adapterAnswer: AnswerAdapter
    private val customToast by lazy { CustomToast(requireContext()) }
    private lateinit var tapsellWinStage: TapsellWinStage
    private var checkOpenDialog = true
    private lateinit var soundPool: SoundPool
    private lateinit var dialogCustomShop: Dialog
    private lateinit var tapsellShop: TapsellShop
    private val paymentConfiguration = PaymentConfiguration(
        localSecurityCheck = SecurityCheck.Enable(rsaPublicKey = Utils.rsaPublicKey)
    )
    private val payment by lazy(LazyThreadSafetyMode.NONE) {
        Payment(context = requireActivity(), config = paymentConfiguration)
    }
    private lateinit var paymentConnection: Connection
    private var coin50 = false
    private var coin80 = false
    private var coin100 = false
    private var coin150 = false
    private var coin300 = false
    private var isFinishedLevel = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textNumberRubyHelp.text = Utils.NUMBER_OF_COIN_FOR_HELP.toString()
        dataBase = AppDataBase.getDatabase(requireActivity())

        Utils.isAllEditTextsFilled = false

        binding.textViewLevel.text = "مرحله ${Utils.currentLevel}"

        binding.layoutIncreaseRuby.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.layoutIncreaseRuby.btnBuyRuby.setOnClickListener { dialogShop() }
        binding.layoutIncreaseRuby.layoutBuyRuby.setOnClickListener { dialogShop() }

        readDataFromDatabaseAndFillFields()

        getCoinFromDatabase()
        fillListAnswer()
        fillListLetterAdapter()
        createRecyclerViewAnswer()
        setDataToRecyclerViewLetter()
        help()
        initializerSoundPool()

    }

    private var soundIdClickLetter = 1
    private var soundIdWinStage = 2
    private var soundIdClickAnswer = 3
    private var soundIdFinalWin = 4
    private var soundIdWrongAnswer = 5
    private fun initializerSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()

        soundIdClickLetter = soundPool.load(requireContext(), R.raw.sound_effect_click_letter, 1)
        soundIdWinStage = soundPool.load(requireContext(), R.raw.sound_effect_win_stage, 1)
        soundIdClickAnswer = soundPool.load(requireContext(), R.raw.sound_effect_click_answer, 1)
        soundIdFinalWin = soundPool.load(requireContext(), R.raw.sound_effect_final_win, 1)
        soundIdWrongAnswer = soundPool.load(requireContext(), R.raw.sound_effect_wrong_answer, 1)
    }

    private fun playSoundEffectClickLetter() {
        if (Utils.playVolume)
            soundPool.play(soundIdClickLetter, 1F, 1F, 0, 0, 1F)
    }

    private fun playSoundEffectWinStage() {
        if (Utils.playVolume)
            soundPool.play(soundIdWinStage, 1F, 1F, 0, 0, 1F)
    }

    private fun playSoundEffectFinalWin() {
        if (Utils.playVolume)
            soundPool.play(soundIdFinalWin, 1F, 1F, 0, 0, 1F)
    }

    private fun playSoundEffectClickAnswer() {
        if (Utils.playVolume)
            soundPool.play(soundIdClickAnswer, 1F, 1F, 0, 0, 1F)
    }

    private fun playSoundEffectWrongAnswer() {
        if (Utils.playVolume)
            soundPool.play(soundIdWrongAnswer, 1F, 1F, 0, 0, 1F)
    }

    private lateinit var dialogShop: DialogShop

    private fun dialogShop() {
        dialogShop = DialogShop(requireActivity())
        dialogShop.showDialog(requireActivity().activityResultRegistry)
    }

    private fun getCoinFromDatabase() {
        val dataUser = dataBase.user().readDataUser()
        binding.layoutIncreaseRuby.textCoin.text = dataUser.coin.toString()
    }

    private fun fillListLetterAdapter() {
        listLetterAdapter = ArrayList()
        var id = 1
        var position = 0

        listLetter.forEach {
            listLetterAdapter.add(
                LetterModel(
                    id = id,
                    index = position,
                    it,
                    isShow = true
                )
            )
            id++
            position++
        }
    }

    private fun readDataFromDatabaseAndFillFields() {
        readDatabaseLevel = dataBase.levels().readDataLevel()

        readDatabaseLevel.forEach {
            if (Utils.currentLevel == it.titleLevel) {
                binding.textQuestion.text = it.question
                answer = it.answer
                sizeAnswer = it.sizeAnswer
                listAnswer = it.listAnswer
                listLetter = it.letters
                isFinishedLevel = it.isFinishedLevel
                sizeAnswer--

            }
        }
    }

    private fun fillListAnswer() {
        listAnswerAdapter = ArrayList()

        listAnswer.forEachIndexed { index, answer ->
            listAnswerAdapter.add(
                AnswerModel(
                    id = index,
                    index = index,
                    isShow = false,
                    characterHelp = answer
                )
            )
        }
    }

    private fun createRecyclerViewAnswer() {

        val displayMetrics = resources.displayMetrics
        val screenWith =
            displayMetrics.widthPixels - (resources.getDimension(com.intuit.sdp.R.dimen._15sdp) * 2) - (resources.getDimension(
                com.intuit.sdp.R.dimen._15sdp
            ) * 2)
        val itemWidth = (screenWith / 6).toInt()

        adapterAnswer = AnswerAdapter(listAnswerAdapter, this, itemWith = itemWidth)
        binding.layoutAnswer.apply {
            if (sizeAnswer + 1 < 6) {
                layoutManager =
                    GridLayoutManager(
                        requireContext(),
                        sizeAnswer + 1,
                        GridLayoutManager.VERTICAL,
                        false
                    )
            } else {
                GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false)
            }
            adapter = adapterAnswer
        }
    }

    private fun setDataToRecyclerViewLetter() {
        val displayMetrics = resources.displayMetrics
        val screenWith =
            displayMetrics.widthPixels - (resources.getDimension(com.intuit.sdp.R.dimen._15sdp) * 2) - (resources.getDimension(
                com.intuit.sdp.R.dimen._8sdp
            ) * 2)
        val itemWith = (screenWith / 6).toInt()

        adapterLetter = LetterAdapter(listLetterAdapter, this, itemWith = itemWith)
        binding.recyclerViewLetter.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false)
            adapter = adapterLetter
        }

    }

    private fun changeOnRecyclerViewAnswer(index: Int) {
        listLetterAdapter.find { it.index == index }?.isShow = false

        adapterLetter.notifyItemChanged(index)
    }

    private lateinit var dialogWin: Dialog
    private lateinit var answerUser: String
    private fun checkAnswer() {
        answerUser = listAnswerUser.joinToString("")
        val newAnswer = answer.replace(" ", "")
        if (newAnswer.trim() == answerUser) {
            if (Utils.currentLevel != Utils.LAST_LEVEL) {
                if (isFinishedLevel) {
                    showDialogStageThatHasAlreadyBeenWon()
                } else {
                    showDialogWin()
                }
                playSoundEffectWinStage()

            } else {
                if (isFinishedLevel) {

                } else {

                    showDialogFinalWin()
                }

                playSoundEffectFinalWin()
            }

        } else {

            val currentCoin = readCoin()

            if (currentCoin >= Utils.NUMBER_OF_COIN_FOR_WRONG_ANSWER) {

                val saveNewCoin = currentCoin - Utils.NUMBER_OF_COIN_FOR_WRONG_ANSWER
                saveNewCoinInDatabase(saveNewCoin)

                dialogWrongAnswer()
                playSoundEffectWrongAnswer()

            } else {
                dialogWrongAnswer()
                playSoundEffectWrongAnswer()
//                dialogNotEnoughCoin()
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showDialogStageThatHasAlreadyBeenWon() {
        dialogWin = Dialog(requireContext())
        dialogWin.apply {
            setContentView(R.layout.layout_dialog_stage_that_has_already_been_won)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.setGravity(Gravity.CENTER)
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            val lp = window!!.attributes
            lp.dimAmount = 0.7f
            dialogWin.setCancelable(false)

            val btnNextLevel = findViewById<View>(R.id.btn_next_level)
            val btnReturn = findViewById<View>(R.id.btn_return)
            val btnClose = findViewById<View>(R.id.btn_close_win)

            btnReturn.setOnClickListener {
                dialogWin.dismiss()
                findNavController().popBackStack()
            }

            btnNextLevel.setOnClickListener {
                refreshFragment()
                dialogWin.dismiss()
            }

            btnClose.setOnClickListener {
                dialogWin.dismiss()
                findNavController().popBackStack()
            }

            show()
        }
    }

    private lateinit var dialogWrongAnswer: Dialog
    private fun dialogWrongAnswer() {
        dialogWrongAnswer = Dialog(requireContext())
        dialogWrongAnswer.setContentView(R.layout.layout_dialog_wrong_answer)
        dialogWrongAnswer.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogWrongAnswer.window!!.setGravity(Gravity.CENTER)
        dialogWrongAnswer.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
        val lp = dialogWrongAnswer.window!!.attributes
        lp.dimAmount = 0.8f
        dialogWrongAnswer.setCancelable(false)

        val textAnswerUser = dialogWrongAnswer.findViewById<TextView>(R.id.text_wrong_answer)
        val btnClose = dialogWrongAnswer.findViewById<View>(R.id.btn_close_wrong_answer)
        val btnFinish = dialogWrongAnswer.findViewById<View>(R.id.btn_finish)
        val btnAgain = dialogWrongAnswer.findViewById<View>(R.id.btn_again)

        textAnswerUser.text = answerUser
        btnClose.setOnClickListener {
            dialogWrongAnswer.dismiss()
            findNavController().popBackStack()
        }
        btnAgain.setOnClickListener {
            val coin = dataBase.user().readDataUser().coin
            if (coin >= Utils.ENOUGH_COIN_FOR_CONTINUE_GAME) {
                //refresh fragment
                val id = findNavController().currentDestination?.id
                findNavController().popBackStack(id!!, true)
                findNavController().navigate(id)
            } else {
                customDialogNotEnoughCoinForContinueGame()
            }

            dialogWrongAnswer.dismiss()
        }
        btnFinish.setOnClickListener {
            dialogWrongAnswer.dismiss()
            findNavController().popBackStack()
        }

        dialogWrongAnswer.show()
    }

    private fun customDialogNotEnoughCoinForContinueGame() {
        dialogNotEnoughCoin = Dialog(requireContext())
        dialogNotEnoughCoin.setContentView(R.layout.layout_dialog_ruby_not_enough)
        dialogNotEnoughCoin.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogNotEnoughCoin.window!!.setGravity(Gravity.CENTER)
        dialogNotEnoughCoin.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        dialogNotEnoughCoin.setCancelable(false)
        val textDes = dialogNotEnoughCoin.findViewById<TextView>(R.id.text_description)
        val btnBuyCoin = dialogNotEnoughCoin.findViewById<View>(R.id.btn_buy_coin)
        val btnClose = dialogNotEnoughCoin.findViewById<View>(R.id.btn_close)
        textDes.text = requireContext().getString(R.string.w_not_enough_coin_for_continue_game)
        btnClose.setOnClickListener {
            dialogNotEnoughCoin.dismiss()
            findNavController().popBackStack()
        }
        btnBuyCoin.setOnClickListener {
            dialogNotEnoughCoin.dismiss()
            customDialogShop()
        }

        dialogNotEnoughCoin.show()
    }

    private fun customDialogShop() {
        dialogCustomShop = Dialog(requireActivity())
        startPaymentConnection()
        dialogCustomShop.setContentView(R.layout.layout_dialog_shop)
        dialogCustomShop.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogCustomShop.window!!.setGravity(Gravity.CENTER)
        dialogCustomShop.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        dialogCustomShop.setCancelable(false)
        val btnClose = dialogCustomShop.findViewById<View>(R.id.btn_close_shop)
        val btnSeeAds = dialogCustomShop.findViewById<View>(R.id.btn_see_ad)
        val btnBuyCoinOne = dialogCustomShop.findViewById<View>(R.id.btn_buy_coin_one)
        val btnBuyCoinTwo = dialogCustomShop.findViewById<View>(R.id.btn_buy_coin_two)
        val btnBuyCoinThree = dialogCustomShop.findViewById<View>(R.id.btn_buy_coin_three)
        val btnBuyCoinFour = dialogCustomShop.findViewById<View>(R.id.btn_buy_coin_four)
        val btnBuyCoinFive = dialogCustomShop.findViewById<View>(R.id.btn_buy_coin_five)

        btnClose.setOnClickListener {
            val coin = dataBase.user().readDataUser().coin
            if (coin >= Utils.ENOUGH_COIN_FOR_CONTINUE_GAME) {
                paymentConnection.disconnect()
                dialogCustomShop.dismiss()
            } else {
                paymentConnection.disconnect()
                dialogCustomShop.dismiss()
                findNavController().popBackStack()
            }
        }

        btnSeeAds.setOnClickListener {
            if (CheckNetworkConnection.isOnline(requireActivity())) {
                tapsellShop = TapsellShop(context as Activity)
                tapsellShop.connectToTapsell()
                tapsellShop.requestAdGift()

            } else {
                customToast.customToast(
                    colorBackground = R.drawable.simple_shape_background_toast_error,
                    img = R.drawable.vector_close_circle,
                    message = requireActivity().getString(R.string.w_no_access_to_internet)
                )
            }
        }

        btnBuyCoinOne.setOnClickListener {
            if (CheckNetworkConnection.isOnline(requireActivity())) {
                if (paymentConnection.getState() == ConnectionState.Connected) {
                    purchaseProduct(
                        productId = Utils.productId_50,
                        payload = "payload",
                        ""
                    )
                    coin50 = true
                }
            } else {
                customToast.customToast(
                    R.drawable.simple_shape_background_toast_error,
                    R.drawable.vector_close_circle,
                    requireActivity().getString(R.string.w_no_access_to_internet)
                )
            }
        }

        btnBuyCoinTwo.setOnClickListener {
            if (CheckNetworkConnection.isOnline(requireActivity())) {
                if (paymentConnection.getState() == ConnectionState.Connected) {
                    purchaseProduct(
                        productId = Utils.productId_80,
                        payload = "payload",
                        ""
                    )
                    coin80 = true
                }
            } else {
                customToast.customToast(
                    R.drawable.simple_shape_background_toast_error,
                    R.drawable.vector_close_circle,
                    requireActivity().getString(R.string.w_no_access_to_internet)
                )
            }
        }

        btnBuyCoinThree.setOnClickListener {
            if (CheckNetworkConnection.isOnline(requireActivity())) {
                if (paymentConnection.getState() == ConnectionState.Connected) {
                    purchaseProduct(
                        productId = Utils.productId_100,
                        payload = "payload",
                        ""
                    )
                    coin100 = true
                }
            } else {
                customToast.customToast(
                    R.drawable.simple_shape_background_toast_error,
                    R.drawable.vector_close_circle,
                    requireActivity().getString(R.string.w_no_access_to_internet)
                )
            }
        }

        btnBuyCoinFour.setOnClickListener {
            if (CheckNetworkConnection.isOnline(requireActivity())) {
                if (paymentConnection.getState() == ConnectionState.Connected) {
                    purchaseProduct(
                        productId = Utils.productId_150,
                        payload = "payload",
                        ""
                    )
                    coin150 = true
                }
            } else {
                customToast.customToast(
                    R.drawable.simple_shape_background_toast_error,
                    R.drawable.vector_close_circle,
                    requireActivity().getString(R.string.w_no_access_to_internet)
                )
            }
        }

        btnBuyCoinFive.setOnClickListener {
            if (CheckNetworkConnection.isOnline(requireActivity())) {
                if (paymentConnection.getState() == ConnectionState.Connected) {
                    purchaseProduct(
                        productId = Utils.productId_300,
                        payload = "payload",
                        ""
                    )
                    coin300 = true
                }
            } else {
                customToast.customToast(
                    R.drawable.simple_shape_background_toast_error,
                    R.drawable.vector_close_circle,
                    requireActivity().getString(R.string.w_no_access_to_internet)
                )
            }
        }

        dialogCustomShop.show()
    }

    private fun showDialogFinalWin() {
        dialogWin = Dialog(requireContext())
        dialogWin.setContentView(R.layout.layout_dialog_final_win)
        dialogWin.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogWin.window!!.setGravity(Gravity.CENTER)
        dialogWin.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
        val lp = dialogWin.window!!.attributes
        lp.dimAmount = 0.8f
        dialogWin.setCancelable(false)

        val btnClose = dialogWin.findViewById<View>(R.id.btn_close)
        val btnFinish = dialogWin.findViewById<View>(R.id.btn_finish)

        btnFinish.setOnClickListener {
            dialogWin.dismiss()
            findNavController().popBackStack()
        }

        btnClose.setOnClickListener {
            dialogWin.dismiss()
            findNavController().popBackStack()
        }
        dialogWin.show()
    }

    private fun showDialogWin() {
        dialogWin = Dialog(requireContext())
        dialogWin.setContentView(R.layout.layout_dialog_win_stage)
        dialogWin.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogWin.window!!.setGravity(Gravity.CENTER)
        dialogWin.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
        val lp = dialogWin.window!!.attributes
        lp.dimAmount = 0.7f
        dialogWin.setCancelable(false)

        val btnNextLevel = dialogWin.findViewById<View>(R.id.btn_next_level)
        val btnSeeAd = dialogWin.findViewById<View>(R.id.btn_see_ad)
        val btnClose = dialogWin.findViewById<View>(R.id.btn_close_win)
        val textAnswer = dialogWin.findViewById<TextView>(R.id.text_answer)
        val textSeeAd = dialogWin.findViewById<TextView>(R.id.text_see_ad)

        textAnswer.text = answer
        textSeeAd.text ="شما ${Utils.NUMBER_OF_COIN_FOR_CORRECT_ANSWER} یاقوت به دست آوردهاید! با تماشای یک تبلیغ، ${Utils.NUMBER_OF_COIN_FOR_CORRECT_ANSWER_AND_SEEING_ADS} یاقوت کسب کنید."

        btnSeeAd.setOnClickListener {
            if (CheckNetworkConnection.isOnline(requireActivity())) {
                tapsellWinStage = TapsellWinStage(requireActivity())
                tapsellWinStage.connectToTapsell()
                tapsellWinStage.requestAdGift()
            } else {
                customToast.customToast(
                    colorBackground = R.drawable.simple_shape_background_toast_error,
                    img = R.drawable.vector_close_circle,
                    message = requireContext().getString(R.string.w_no_access_to_internet)
                )
            }
        }

        btnNextLevel.setOnClickListener {
            saveNewCoin()
            refreshFragment()
            dialogWin.dismiss()
        }

        btnClose.setOnClickListener {
            saveNewCoin()
            updateData()
            dialogWin.dismiss()
            findNavController().popBackStack()
        }

        dialogWin.show()
    }

    private fun saveNewCoin() {
        customToast.customToast(
            R.drawable.simple_shape_background_toast_info,
            R.drawable.vector_info_circle,
            "${Utils.NUMBER_OF_COIN_FOR_CORRECT_ANSWER} یاقوت به شما اضافه شد."
        )

        val newCreditUser = readCoin() + Utils.NUMBER_OF_COIN_FOR_CORRECT_ANSWER
        dataBase.user().updateDataUser(UserModel(id = 1, coin = newCreditUser))
    }

    private fun refreshFragment() {
        updateData()

        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!, true)
        findNavController().navigate(id)
    }

    private fun updateData() {
        Utils.currentLevel += 1
        //update database
        readDatabaseLevel.forEach {
            if (it.id == Utils.currentLevel) {
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
                        isFinishedLevel = it.isFinishedLevel
                    )
                )
            }
            if (it.id == Utils.currentLevel - 1) {
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
                        isFinishedLevel = true
                    )
                )
            }
        }
    }

    private fun help() {
        binding.layoutHelp.setOnClickListener {
            playSoundEffectClickLetter()
            val currentCoin = readCoin()
            if (currentCoin >= Utils.NUMBER_OF_COIN_FOR_HELP) {

                val newCoin = currentCoin - Utils.NUMBER_OF_COIN_FOR_HELP

                if (newCoin >= Utils.ENOUGH_COIN_FOR_CONTINUE_GAME) {

                    val saveNewCoin = currentCoin - Utils.NUMBER_OF_COIN_FOR_HELP
                    saveNewCoinInDatabase(saveNewCoin)

                    customToast.customToast(
                        R.drawable.simple_shape_background_toast_info,
                        R.drawable.vector_info_circle,
                        "${Utils.NUMBER_OF_COIN_FOR_HELP} یاقوت کم شد."
                    )

                    listAnswerAdapter.find { it.index == currentTextIndex }?.isHelp = true
                    listAnswerAdapter.find { it.index == currentTextIndex }?.isShow = true
                    listAnswerAdapter.find { it.index == currentTextIndex }?.isClick = true

                    listAnswerAdapter.forEach {
                        if (it.index != currentTextIndex)
                            it.isClick = false
                    }
                    adapterAnswer.notifyItemChanged(currentTextIndex)
                    listAnswerAdapter.forEach {
                        if (it.index == currentTextIndex)
                            listAnswerUser.add(it.characterHelp)
                    }
                    currentTextIndex++

                    if (currentTextIndex == sizeAnswer + 1) {
                        Utils.isAllEditTextsFilled = true
                        checkAnswer()
                    }
                } else {
                    dialogNotEnoughCoinForContinueGame()
                }
            } else {
                dialogNotEnoughCoin()
            }
        }
    }

    private var checkOpenDialog1 = true

    private fun dialogNotEnoughCoinForContinueGame() {
        if (checkOpenDialog1) {
            checkOpenDialog1 = false
            dialogNotEnoughCoin = Dialog(requireContext())
            dialogNotEnoughCoin.setContentView(R.layout.layout_dialog_ruby_not_enough)
            dialogNotEnoughCoin.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogNotEnoughCoin.window!!.setGravity(Gravity.CENTER)
            dialogNotEnoughCoin.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            dialogNotEnoughCoin.setCancelable(false)
            val textDes = dialogNotEnoughCoin.findViewById<TextView>(R.id.text_description)
            val btnBuyCoin = dialogNotEnoughCoin.findViewById<View>(R.id.btn_buy_coin)
            val btnClose = dialogNotEnoughCoin.findViewById<View>(R.id.btn_close)
            textDes.text = requireContext().getString(R.string.w_not_enough_coin_for_continue_game)
            btnClose.setOnClickListener {
                checkOpenDialog1 = true
                dialogNotEnoughCoin.dismiss()
                findNavController().popBackStack()
            }
            btnBuyCoin.setOnClickListener {
                checkOpenDialog1 = true
                dialogNotEnoughCoin.dismiss()
                dialogShop()
            }
            dialogNotEnoughCoin.setOnDismissListener {
                checkOpenDialog1 = true
                dialogNotEnoughCoin.dismiss()
            }
            dialogNotEnoughCoin.show()
        }

    }

    private fun saveNewCoinInDatabase(saveNewCoin: Int) {
        dataBase.user().updateDataUser(UserModel(id = 1, coin = saveNewCoin))
        binding.layoutIncreaseRuby.textCoin.text = saveNewCoin.toString()
    }

    private lateinit var dialogNotEnoughCoin: Dialog
    private fun dialogNotEnoughCoin() {
        if (checkOpenDialog) {
            checkOpenDialog = false
            dialogNotEnoughCoin = Dialog(requireContext())
            dialogNotEnoughCoin.setContentView(R.layout.layout_dialog_ruby_not_enough)
            dialogNotEnoughCoin.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogNotEnoughCoin.window!!.setGravity(Gravity.CENTER)
            dialogNotEnoughCoin.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            val textDes = dialogNotEnoughCoin.findViewById<TextView>(R.id.text_description)
            val btnBuy = dialogNotEnoughCoin.findViewById<View>(R.id.btn_buy_coin)
            val btnClose = dialogNotEnoughCoin.findViewById<View>(R.id.btn_close)
            textDes.text = requireContext().getString(R.string.txt_ruby_is_not_enough)

            btnBuy.setOnClickListener {
                dialogShop()
                checkOpenDialog = true
                dialogNotEnoughCoin.dismiss()
            }
            btnClose.setOnClickListener {
                checkOpenDialog = true
                dialogNotEnoughCoin.dismiss()
            }

            dialogNotEnoughCoin.setOnDismissListener {
                checkOpenDialog = true
                dialogNotEnoughCoin.dismiss()
            }

            dialogNotEnoughCoin.show()
        }

    }

    private fun readCoin(): Int {
        val coin = dataBase.user().readDataUser().coin
        return coin
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
    ) {

        val purchaseRequest = PurchaseRequest(
            productId = productId,
            payload = payload,
            dynamicPriceToken = dynamicPriceToken,
        )

        payment.purchaseProduct(
            registry = requireActivity().activityResultRegistry,
            request = purchaseRequest
        ) {
            purchaseFlowBegan {
                Log.i("pay", "purchaseProduct: purchaseFlowBegan")
            }
            failedToBeginFlow { throwable ->
                if (throwable is DynamicPriceNotSupportedException) {

                    Log.i("pay", "purchaseProduct: failedToBeginFlow ${throwable.message}")

                    purchaseProduct(productId, payload, null)
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

    private lateinit var txtRuby: TextView

    private fun updateDatabaseUser(ruby: Int) {
        txtRuby = requireActivity().findViewById(R.id.text_coin)

        dataBase = AppDataBase.getDatabase(requireActivity())
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

    override fun clickOnAnswer(
        index: Int, letter: Char?, positionLetter: Int?, isHelp: Boolean, isClick: Boolean
    ) {
        if (!isHelp && isClick != false) {
            if (index == currentTextIndex - 1) {
                playSoundEffectClickAnswer()
                listAnswerAdapter.find { it.index == index }?.letter = null
                listAnswerAdapter.find { it.index == index }?.positionLetter = null
                listAnswerAdapter.find { it.index == index }?.isShow = false
                adapterAnswer.notifyItemChanged(index)
                currentTextIndex--

                listLetterAdapter.find { it.index == positionLetter && it.letter == letter }?.isShow =
                    true
                listAnswerAdapter.find { it.index == currentTextIndex - 1 }?.isClick = true
                adapterLetter.notifyItemChanged(positionLetter!!)

                listAnswerUser.removeAt(index)
            }
        }
    }

    override fun clickOnHelp(
        index: Int, isHelp: Boolean, isClick: Boolean
    ) {
        if (isHelp && isClick != false) {
            playSoundEffectClickAnswer()
            currentTextIndex--
            listAnswerUser.removeAt(index)
            listAnswerAdapter.find { it.index == currentTextIndex }?.isHelp = false
            listAnswerAdapter.find { it.index == index }?.isShow = false
            listAnswerAdapter.find { it.index == currentTextIndex - 1 }?.isClick = true
            adapterAnswer.notifyItemChanged(index)
        }
    }

    override fun clickOnLetter(index: Int, letter: Char, linearLayout: LinearLayout) {
        if (currentTextIndex <= sizeAnswer) {
            playSoundEffectClickLetter()
            listAnswerUser.add(letter)
            if (currentTextIndex == sizeAnswer) {
                Utils.isAllEditTextsFilled = true
                checkAnswer()
            }

            listAnswerAdapter.find { it.index == currentTextIndex }?.letter = letter
            listAnswerAdapter.find { it.index == currentTextIndex }?.positionLetter = index
            listAnswerAdapter.find { it.index == currentTextIndex }?.isShow = true
            listAnswerAdapter.find { it.index == currentTextIndex }?.isClick = true

            listAnswerAdapter.forEach {
                if (it.index != currentTextIndex)
                    it.isClick = false
            }

            adapterAnswer.notifyItemChanged(currentTextIndex)

            currentTextIndex++
            changeOnRecyclerViewAnswer(index)

        }
    }

    override fun onResume() {
        super.onResume()

        if (Utils.back_from_tapsell) {
            refreshFragment()
            dialogWin.dismiss()
            Utils.back_from_tapsell = false
        }
    }

}
