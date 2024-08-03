package ir.developre.chistangame.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ir.developre.chistangame.R
import ir.developre.chistangame.adapter.LevelAdapter
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.FragmentLevelsBinding
import ir.developre.chistangame.global.CustomToast
import ir.developre.chistangame.global.DialogShop
import ir.developre.chistangame.global.Utils
import ir.developre.chistangame.model.LevelModel
import ir.developre.chistangame.my_interface.on_click.ClickOnLevel

class LevelsFragment : Fragment(), ClickOnLevel {
    private lateinit var binding: FragmentLevelsBinding
    private lateinit var adapterLevel: LevelAdapter
    private lateinit var listLevel: ArrayList<LevelModel>
    private lateinit var dataBaseLevel: AppDataBase
    private lateinit var dataLevel: List<LevelModel>
    private val customToast by lazy { CustomToast(requireContext()) }
    private lateinit var dialogNotEnoughCoin: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLevelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readDataFromDb()
        getCoinFromDatabase()
        binding.layoutIncreaseRubyHome.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.layoutIncreaseRubyHome.btnBuyRuby.setOnClickListener { dialogShop() }
        binding.layoutIncreaseRubyHome.layoutBuyRuby.setOnClickListener { dialogShop() }

    }

    private lateinit var dialogShop: DialogShop

    private fun dialogShop() {
        dialogShop = DialogShop(requireActivity())
        dialogShop.showDialog(requireActivity().activityResultRegistry)
    }


    private fun getCoinFromDatabase() {
        val dataUser = dataBaseLevel.user().readDataUser()
        binding.layoutIncreaseRubyHome.textCoin.text = dataUser.coin.toString()
    }


    private fun readDataFromDb() {
        dataBaseLevel = AppDataBase.getDatabase(requireActivity())
        dataLevel = dataBaseLevel.levels().readDataLevel()

        addDataToList()
    }

    private fun addDataToList() {
        listLevel = ArrayList()
        dataLevel.forEach {
            listLevel.add(it)
        }

        setDataOnRecyclerView()
    }

    private fun setDataOnRecyclerView() {
        val displayMetrics = resources.displayMetrics
        val screenWith =
            displayMetrics.widthPixels - (resources.getDimension(com.intuit.sdp.R.dimen._25sdp) * 2) - (resources.getDimension(
                com.intuit.sdp.R.dimen._25sdp
            ) * 2)
        val itemWith = (screenWith / 3).toInt()

        adapterLevel = LevelAdapter(listLevel, this, itemWith = itemWith)
        binding.recyclerViewLevels.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = adapterLevel
        }
    }

    override fun clickOnLevel(index: Int, lockItemSelected: Boolean) {
        if (!lockItemSelected) {
            checkEnoughCoin(index)
        } else {
            customToast.customToast(
                colorBackground = R.drawable.simple_shape_background_toast_warning,
                img = R.drawable.vector_warning_circle,
                message = requireContext().getString(R.string.w_lock_level)
            )
        }
    }

    private fun checkEnoughCoin(index: Int) {
        val coin = dataBaseLevel.user().readDataUser().coin
        if (coin >= Utils.ENOUGH_COIN_FOR_CONTINUE_GAME) {
            Utils.currentLevel = index + 1
            findNavController().navigate(R.id.action_levelsFragment_to_gameFragment)
        } else {
            dialogNotEnoughCoinForContinueGame()
        }
    }

    private var checkOpenDialog = true
    private fun dialogNotEnoughCoinForContinueGame() {
        if (checkOpenDialog) {
            checkOpenDialog = false
            dialogNotEnoughCoin = Dialog(requireContext())
            dialogNotEnoughCoin.setContentView(R.layout.layout_dialog_ruby_not_enough)
            dialogNotEnoughCoin.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogNotEnoughCoin.window!!.setGravity(Gravity.CENTER)
            dialogNotEnoughCoin.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            val textDes = dialogNotEnoughCoin.findViewById<TextView>(R.id.text_description)
            val btnBuyCoin = dialogNotEnoughCoin.findViewById<View>(R.id.btn_buy_coin)
            val btnClose = dialogNotEnoughCoin.findViewById<View>(R.id.btn_close)
            textDes.text = requireContext().getString(R.string.w_not_enough_coin_for_continue_game)
            btnClose.setOnClickListener {
                checkOpenDialog = true
                dialogNotEnoughCoin.dismiss()
            }
            btnBuyCoin.setOnClickListener {
                checkOpenDialog = true
                dialogNotEnoughCoin.dismiss()
                dialogShop()
            }
            dialogNotEnoughCoin.setOnDismissListener {
                checkOpenDialog = true
                dialogNotEnoughCoin.dismiss()
            }
            dialogNotEnoughCoin.show()
        }

    }
}