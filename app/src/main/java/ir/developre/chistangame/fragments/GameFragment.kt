package ir.developre.chistangame.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ir.developre.chistangame.adapter.AnswerAdapter
import ir.developre.chistangame.adapter.LetterAdapter
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.FragmentGameBinding
import ir.developre.chistangame.global.Utils
import ir.developre.chistangame.model.AnswerModel
import ir.developre.chistangame.model.LetterModel
import ir.developre.chistangame.model.LevelModel
import ir.developre.chistangame.model.SelectedLetter
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
    private var currentEditTextIndex = 0
    private var level = 0
    private val editTexts = mutableListOf<EditText>()
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

        level = requireArguments().getInt("level")

        Utils.isAllEditTextsFilled = false

        binding.textViewLevel.text = "مرحله $level"

        readDataFromDatabaseAndFillFilds()

        fillListAnswer()

        fillListLetterAdapter()

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

//        val numberOfEditTexts = sizeAnswer
        createDynamicEditText()
//        currentEditTextIndex = numberOfEditTexts - 1
//        currentEditTextIndex = sizeAnswer
        setDataToRecyclerViewLetter()

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
        dataBase = AppDataBase.getDatabase(requireActivity())
        readData = dataBase.levels().readDataLevel()

        readData.forEach {
            if (level == it.titleLevel) {
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

    private val listSelectedLetter by lazy { ArrayList<SelectedLetter>() }

    private fun changeOnRecyclerViewAnswer(index: Int) {
        listLetterAdapter.find { it.index == index }?.isShow = false

        adapterLetter.notifyItemChanged(index)

    }

    private fun checkAnswer() {
        val allText = StringBuilder()
        for (editText in editTexts) {
            allText.append(editText.text.toString())
        }

        val answerUser = allText.reverse().toString()
        Toast.makeText(requireContext(), answerUser, Toast.LENGTH_SHORT).show()

        if (answer == answerUser) {
            Toast.makeText(requireContext(), "آفرین!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "اشتباه!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun clickOnAnswer(index: Int, letter: Char, positionLetter: Int) {

        if (index == currentEditTextIndex - 1) {
            listAnswerAdapter.find { it.index == index }?.letter = null
            listAnswerAdapter.find { it.index == index }?.positionLetter = null
            listAnswerAdapter.find { it.index == index }?.isShow = false
            adapterAnswer.notifyItemChanged(index)
            currentEditTextIndex--

            listSelectedLetter.forEach { selectedLetter ->
                if (selectedLetter.position == positionLetter) {
                    listLetterAdapter.find { it.index == selectedLetter.position && it.letter == letter }?.isShow = true
                    adapterLetter.notifyItemChanged(selectedLetter.position)
                }
            }
        }
    }

    override fun clickOnLetter(index: Int, letter: Char, linearLayout: LinearLayout) {
        if (currentEditTextIndex <= sizeAnswer) {
//            editTexts[currentEditTextIndex].setText(letter.toString())
            if (currentEditTextIndex == sizeAnswer) {
                Utils.isAllEditTextsFilled = true
//                checkAnswer()
            }

            listAnswerAdapter.find { it.index == currentEditTextIndex }?.letter = letter
            listAnswerAdapter.find { it.index == currentEditTextIndex }?.positionLetter = index
            listAnswerAdapter.find { it.index == currentEditTextIndex }?.isShow = true
            adapterAnswer.notifyItemChanged(currentEditTextIndex)


            currentEditTextIndex++
            changeOnRecyclerViewAnswer(index)

            listSelectedLetter.add(SelectedLetter(position = index, letter = letter))

        }
    }

}


//    private fun createDynamicEditText(count: Int) {
//        val typeface =
//            Typeface.createFromAsset(requireActivity().assets, "fonts/lalezar_regular.ttf")
//
//        for (i in 1..count) {
//            val editText = EditText(requireContext())
//            editText.layoutParams =
//                LayoutParams(170, 170)
//            editText.setBackgroundResource(R.drawable.simple_shape_background_recycler_view_enter_letter)
//            editText.maxLines = 1
//            editText.typeface = typeface
//            editText.textSize = 28f
//            editText.layoutDirection = View.LAYOUT_DIRECTION_RTL
//            editText.textDirection = View.TEXT_DIRECTION_RTL
//            editText.isFocusableInTouchMode = false
//            editText.isAllCaps = true
//            editText.isCursorVisible = false
//            editText.gravity = Gravity.CENTER
//            editText.setTextColor(requireContext().getColor(R.color.brightMango))
//            editText.setPadding(4, 0, 4, 0)
//
//            editText.setOnClickListener {
//                if (i - 1 == currentEditTextIndex + 1) {
//                    val removedLetter = editText.text.toString().firstOrNull()
//                    editText.setText("")
//                    currentEditTextIndex++
//
//
//                    listSelectedLetter.forEachIndexed { index, selectedLetter ->
//
//                    }
//                    listLetterAdapter.find { it.letter == removedLetter }?.isShow = true
//
////                    adapterLetter.notifyItemChanged (index)
//
//                }
//            }
//
//            //set edittext in linearlayout
//            binding.layoutAnswer.addView(editText)
//
//            editTexts.add(editText)
//
//            //set margin
//            val margin: ViewGroup.MarginLayoutParams =
//                editText.layoutParams as ViewGroup.MarginLayoutParams
//            margin.setMargins(4, 0, 4, 0)
//        }
//    }