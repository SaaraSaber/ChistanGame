package ir.developre.chistangame.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ir.developre.chistangame.R
import ir.developre.chistangame.adapter.LetterAdapter
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.FragmentGameBinding
import ir.developre.chistangame.global.Utils
import ir.developre.chistangame.model.LevelModel
import ir.developre.chistangame.my_interface.on_click.ClickOnLetter


class GameFragment : Fragment(), ClickOnLetter {
    private lateinit var binding: FragmentGameBinding
    private lateinit var dataBase: AppDataBase
    private lateinit var readData: List<LevelModel>
    private lateinit var answer: String
    private var sizeAnswer: Int = 0
    private lateinit var listLetter: ArrayList<Char>
    private var currentEditTextIndex = 0
    private val editTexts = mutableListOf<EditText>()
    private lateinit var adapterLetter: LetterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val level = requireArguments().getInt("level")
        Utils.isAllEditTextsFilled = false

        binding.textViewLevel.text = "مرحله $level"

        dataBase = AppDataBase.getDatabase(requireActivity())
        readData = dataBase.levels().readDataLevel()

        readData.forEach {
            if (level == it.titleLevel) {
                binding.textQuestion.text = it.question
                answer = it.answer
                sizeAnswer = it.sizeAnswer
                listLetter = it.letters
            }
        }

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        val numberOfEditTexts = sizeAnswer
        createDynamicEditText(numberOfEditTexts)
        currentEditTextIndex = numberOfEditTexts - 1

        setDataToRecyclerViewLetter()

    }


    private fun createDynamicEditText(count: Int) {
        val typeface =
            Typeface.createFromAsset(requireActivity().assets, "fonts/lalezar_regular.ttf")

        for (i in 1..count) {
            val editText = EditText(requireContext())
            editText.layoutParams =
                LayoutParams(170, 170)
            editText.setBackgroundResource(R.drawable.simple_shape_background_recycler_view_enter_letter)
            editText.maxLines = 1
            editText.typeface = typeface
            editText.textSize = 28f
            editText.layoutDirection = View.LAYOUT_DIRECTION_RTL
            editText.textDirection = View.TEXT_DIRECTION_RTL
            editText.isFocusableInTouchMode = false
            editText.isAllCaps = true
            editText.isCursorVisible = false
            editText.gravity = Gravity.CENTER
            editText.setTextColor(requireContext().getColor(R.color.brightMango))
            editText.setPadding(4, 0, 4, 0)

            binding.recyclerViewAnswer.addView(editText)

            editTexts.add(editText)

            val margin: ViewGroup.MarginLayoutParams =
                editText.layoutParams as ViewGroup.MarginLayoutParams
            margin.setMargins(4, 0, 4, 0)
        }
    }


    private fun setDataToRecyclerViewLetter() {
        adapterLetter = LetterAdapter(listLetter, this)
        binding.recyclerViewLetter.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false)
            adapter = adapterLetter
        }
    }

    override fun clickOnLetter(index: Int, letter: Char, linearLayout: LinearLayout) {

        if (currentEditTextIndex >= 0) {

            editTexts[currentEditTextIndex].setText(letter.toString())
            if (currentEditTextIndex == 0) {
                Utils.isAllEditTextsFilled = true
                checkAnswer()
            }
            currentEditTextIndex--
        }
    }

    private fun checkAnswer() {
        val allText = StringBuilder()
        for (editText in editTexts) {
            allText.append(editText.text.toString())
        }
        Toast.makeText(requireContext(), allText.reverse().toString(), Toast.LENGTH_LONG).show()
//        if (answer=="انسان"){
//
//        }
    }

}