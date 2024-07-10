package ir.developre.chistangame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ir.developre.chistangame.adapter.AnswerAdapter
import ir.developre.chistangame.adapter.LetterAdapter
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.FragmentGameBinding
import ir.developre.chistangame.model.LevelModel
import ir.developre.chistangame.my_interface.on_click.ClickOnAnswer
import ir.developre.chistangame.my_interface.on_click.ClickOnLetter

class GameFragment : Fragment(), ClickOnLetter, ClickOnAnswer {
    private lateinit var binding: FragmentGameBinding
    private lateinit var dataBase: AppDataBase
    private lateinit var readData: List<LevelModel>
    private lateinit var listAnswer: ArrayList<Char>
    private lateinit var listLetter: ArrayList<Char>
    private lateinit var answerAdapter: AnswerAdapter
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

        binding.textViewLevel.text = "مرحله $level"

        dataBase = AppDataBase.getDatabase(requireActivity())
        readData = dataBase.levels().readDataLevel()

        readData.forEach {
            if (level == it.titleLevel) {
                binding.textQuestion.text = it.question
                listAnswer = it.answer
                listLetter = it.letters
            }
        }

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        setDataToRecyclerViewAnswer()
        setDataToRecyclerViewLetter()

    }

    private fun setDataToRecyclerViewLetter() {
        adapterLetter = LetterAdapter(listLetter, this)
        binding.recyclerViewLetter.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false)
            adapter = adapterLetter
        }
    }

    private fun setDataToRecyclerViewAnswer() {
        answerAdapter = AnswerAdapter(listAnswer, this)
        binding.recyclerViewAnswer.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false)
            adapter = answerAdapter
        }
    }

    override fun clickOnLetter(index: Int) {
        Toast.makeText(requireContext(), index.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun clickOnAnswer(index: Int) {
        Toast.makeText(requireContext(), index.toString(), Toast.LENGTH_SHORT).show()
    }

}