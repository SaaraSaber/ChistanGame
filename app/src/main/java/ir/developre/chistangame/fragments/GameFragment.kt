package ir.developre.chistangame.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ir.developre.chistangame.R
import ir.developre.chistangame.adapter.AnswerAdapter
import ir.developre.chistangame.adapter.LetterAdapter
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.FragmentGameBinding
import ir.developre.chistangame.global.Utils
import ir.developre.chistangame.model.AnswerModel
import ir.developre.chistangame.model.LetterModel
import ir.developre.chistangame.model.LevelModel
import ir.developre.chistangame.my_interface.on_click.ClickOnAnswer
import ir.developre.chistangame.my_interface.on_click.ClickOnLetter


class GameFragment : Fragment(), ClickOnLetter, ClickOnAnswer {
    private lateinit var binding: FragmentGameBinding
    private lateinit var dataBase: AppDataBase
    private lateinit var readData: List<LevelModel>
    private lateinit var answer: String
    private var sizeAnswer: Int = 0
    private lateinit var listLetterAdapter: ArrayList<LetterModel>
    private lateinit var listAnswerAdapter: ArrayList<AnswerModel>
    private lateinit var listLetter: ArrayList<Char>
    private lateinit var listAnswer: ArrayList<Char>
    private val listAnswerUser by lazy { ArrayList<Char>() }
    private var currentEditTextIndex = 0

    //    private var level = 0
    private lateinit var adapterLetter: LetterAdapter
    private lateinit var adapterAnswer: AnswerAdapter

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

//        level = requireArguments().getInt("level")
        dataBase = AppDataBase.getDatabase(requireActivity())

        Utils.isAllEditTextsFilled = false

        binding.textViewLevel.text = "مرحله ${Utils.currentLevel}"

        readDataFromDatabaseAndFillFilds()
        getCoinFromDatabase()
        fillListAnswer()

        fillListLetterAdapter()

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        createDynamicEditText()

        setDataToRecyclerViewLetter()

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

    private fun readDataFromDatabaseAndFillFilds() {
        readData = dataBase.levels().readDataLevel()

        readData.forEach {
            if (Utils.currentLevel == it.titleLevel) {
                binding.textQuestion.text = it.question
                answer = it.answer
                sizeAnswer = it.sizeAnswer
                listAnswer = it.listAnswer
                listLetter = it.letters

                sizeAnswer--

            }
        }
    }

    private fun fillListAnswer() {
        listAnswerAdapter = ArrayList()

        listAnswer.forEachIndexed { index, _ ->
            listAnswerAdapter.add(
                AnswerModel(
                    id = index,
                    index = index,
                    letter = null,
                    isShow = false
                )
            )
        }
    }


    private fun createDynamicEditText() {

        adapterAnswer = AnswerAdapter(listAnswerAdapter, this)
        binding.layoutAnswer.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false)
            adapter = adapterAnswer
        }
    }


    private fun setDataToRecyclerViewLetter() {
        adapterLetter = LetterAdapter(listLetterAdapter, this)
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
    private fun checkAnswer() {
        val answerUser = listAnswerUser.joinToString("")

        if (answer == answerUser) {

            showDialogWin()

        } else {
            Toast.makeText(requireContext(), "اشتباه!", Toast.LENGTH_SHORT).show()
        }
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

        btnSeeAd.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "در دست ساخت..!",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnNextLevel.setOnClickListener {
            refreshFragment()
            dialogWin.dismiss()
        }

        btnClose.setOnClickListener {
            updateData()
            dialogWin.dismiss()
            findNavController().popBackStack()
        }

        dialogWin.show()
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
        readData.forEach {
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
                        letters = it.letters
                    )
                )
            }
        }
    }


    override fun clickOnAnswer(index: Int, letter: Char, positionLetter: Int) {

        if (index == currentEditTextIndex - 1) {
            listAnswerAdapter.find { it.index == index }?.letter = null
            listAnswerAdapter.find { it.index == index }?.positionLetter = null
            listAnswerAdapter.find { it.index == index }?.isShow = false
            adapterAnswer.notifyItemChanged(index)
            currentEditTextIndex--

            listLetterAdapter.find { it.index == positionLetter && it.letter == letter }?.isShow =
                true
            adapterLetter.notifyItemChanged(positionLetter)

            listAnswerUser.removeAt(index)
        }
    }

    override fun clickOnLetter(index: Int, letter: Char, linearLayout: LinearLayout) {
        if (currentEditTextIndex <= sizeAnswer) {
            listAnswerUser.add(letter)
            if (currentEditTextIndex == sizeAnswer) {
                Utils.isAllEditTextsFilled = true
                checkAnswer()
            }

            listAnswerAdapter.find { it.index == currentEditTextIndex }?.letter = letter
            listAnswerAdapter.find { it.index == currentEditTextIndex }?.positionLetter = index
            listAnswerAdapter.find { it.index == currentEditTextIndex }?.isShow = true
            adapterAnswer.notifyItemChanged(currentEditTextIndex)

            currentEditTextIndex++
            changeOnRecyclerViewAnswer(index)

        }
    }

}