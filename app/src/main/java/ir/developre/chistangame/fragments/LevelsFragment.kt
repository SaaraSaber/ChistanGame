package ir.developre.chistangame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ir.developre.chistangame.R
import ir.developre.chistangame.adapter.LevelAdapter
import ir.developre.chistangame.database.AppDataBase
import ir.developre.chistangame.databinding.FragmentLevelsBinding
import ir.developre.chistangame.global.Utils
import ir.developre.chistangame.model.LevelModel
import ir.developre.chistangame.my_interface.on_click.ClickOnLevel

class LevelsFragment : Fragment(), ClickOnLevel {
    private lateinit var binding: FragmentLevelsBinding
    private lateinit var adapterLevel: LevelAdapter
    private lateinit var listLevel: ArrayList<LevelModel>
    private lateinit var dataBaseLevel: AppDataBase
    private lateinit var dataLevel: List<LevelModel>

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
            displayMetrics.widthPixels - (resources.getDimension(com.intuit.sdp.R.dimen._15sdp) * 2) - (resources.getDimension(
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
//            val bundle = Bundle()
//            bundle.putInt("level", index + 1)
            Utils.currentLevel = index + 1
            findNavController().navigate(R.id.action_levelsFragment_to_gameFragment)
        } else {
            Toast.makeText(requireContext(), "section is lock", Toast.LENGTH_SHORT).show()
        }
    }

}