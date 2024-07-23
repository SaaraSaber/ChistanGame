package ir.developre.chistangame.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.Rect
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
import androidx.recyclerview.widget.RecyclerView
import ir.developre.chistangame.R
import ir.developre.chistangame.adapter.AnswerAdapter
import ir.developre.chistangame.adapter.LetterAdapter
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.FragmentGameBinding
import ir.developre.chistangame.global.CustomToast
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
    private var currentTextIndex = 0
    private lateinit var adapterLetter: LetterAdapter
    private lateinit var adapterAnswer: AnswerAdapter
    private val customToast by lazy { CustomToast(requireActivity()) }

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

        dataBase = AppDataBase.getDatabase(requireActivity())

        Utils.isAllEditTextsFilled = false

        binding.textViewLevel.text = "مرحله ${Utils.currentLevel}"

        binding.layoutIncreaseRuby.btnBack.setOnClickListener { findNavController().popBackStack() }

        readDataFromDatabaseAndFillFilds()
        getCoinFromDatabase()
        fillListAnswer()
        fillListLetterAdapter()
        createRecyclerViewAnswer()
        setDataToRecyclerViewLetter()
        help()
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
            layoutManager =
                GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false)
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
    private fun checkAnswer() {
        val answerUser = listAnswerUser.joinToString("")

        if (answer == answerUser) {

            showDialogWin()

        } else {

            customToast.customToast(
                R.drawable.simple_shape_background_toast_error,
                R.drawable.vector_close_circle,
                requireContext().getString(R.string.wrong_answer)
            )

            //refresh fragment
            val id = findNavController().currentDestination?.id
            findNavController().popBackStack(id!!, true)
            findNavController().navigate(id)
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

    private fun help() {
        binding.layoutHelp.setOnClickListener {

            listAnswerAdapter.find { it.index == currentTextIndex }?.isHelp = true
            listAnswerAdapter.find { it.index == currentTextIndex }?.isShow = true
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
        }
    }

    override fun clickOnAnswer(index: Int, letter: Char?, positionLetter: Int?, isHelp: Boolean) {
        if (!isHelp) {
            if (index == currentTextIndex - 1) {
                listAnswerAdapter.find { it.index == index }?.letter = null
                listAnswerAdapter.find { it.index == index }?.positionLetter = null
                listAnswerAdapter.find { it.index == index }?.isShow = false
                adapterAnswer.notifyItemChanged(index)
                currentTextIndex--

                listLetterAdapter.find { it.index == positionLetter && it.letter == letter }?.isShow =
                    true
                adapterLetter.notifyItemChanged(positionLetter!!)

                listAnswerUser.removeAt(index)
            }
        }
    }

    override fun clickOnHelp(index: Int, isHelp: Boolean) {
        if (isHelp) {
            currentTextIndex--
            listAnswerUser.removeAt(index)
            listAnswerAdapter.find { it.index == currentTextIndex }?.isHelp = false
            listAnswerAdapter.find { it.index == index }?.isShow = false
            adapterAnswer.notifyItemChanged(index)
        }
    }

    override fun clickOnLetter(index: Int, letter: Char, linearLayout: LinearLayout) {
        if (currentTextIndex <= sizeAnswer) {
            listAnswerUser.add(letter)
            if (currentTextIndex == sizeAnswer) {
                Utils.isAllEditTextsFilled = true
                checkAnswer()
            }

            listAnswerAdapter.find { it.index == currentTextIndex }?.letter = letter
            listAnswerAdapter.find { it.index == currentTextIndex }?.positionLetter = index
            listAnswerAdapter.find { it.index == currentTextIndex }?.isShow = true
            adapterAnswer.notifyItemChanged(currentTextIndex)

            currentTextIndex++
            changeOnRecyclerViewAnswer(index)

        }
    }

}


class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount

        if (position < spanCount) { // top edge
            outRect.top = spacing
        }
        outRect.bottom = spacing // item bottom
    }
}