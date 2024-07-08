package ir.developre.chistangame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ir.developre.chistangame.adapter.LevelAdapter
import ir.developre.chistangame.databinding.FragmentLevelsBinding
import ir.developre.chistangame.model.LevelModel

class LevelsFragment : Fragment(), ClickOnLevel {
    private lateinit var binding: FragmentLevelsBinding
    private lateinit var adapterLevel: LevelAdapter
    private lateinit var listLevel: ArrayList<LevelModel>

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


        listLevel = ArrayList()
        listLevel.add(LevelModel(1, false))
        listLevel.add(LevelModel(2, false))
        listLevel.add(LevelModel(3, false))
        listLevel.add(LevelModel(4, false))
        listLevel.add(LevelModel(5, false))
        listLevel.add(LevelModel(6, false))
        listLevel.add(LevelModel(7, false))
        listLevel.add(LevelModel(8, false))
        listLevel.add(LevelModel(9, false))
        listLevel.add(LevelModel(10, true))
        listLevel.add(LevelModel(11, true))
        listLevel.add(LevelModel(12, true))
        listLevel.add(LevelModel(13, true))
        listLevel.add(LevelModel(14, true))
        listLevel.add(LevelModel(15, true))
        listLevel.add(LevelModel(16, true))
        listLevel.add(LevelModel(17, true))
        listLevel.add(LevelModel(18, true))
        listLevel.add(LevelModel(19, true))
        listLevel.add(LevelModel(20, true))
        listLevel.add(LevelModel(21, true))
        listLevel.add(LevelModel(22, false))
        listLevel.add(LevelModel(23, false))
        listLevel.add(LevelModel(24, false))
        listLevel.add(LevelModel(25, false))
        listLevel.add(LevelModel(26, false))
        listLevel.add(LevelModel(27, false))
        listLevel.add(LevelModel(28, false))
        listLevel.add(LevelModel(29, false))
        listLevel.add(LevelModel(30, false))

        val displayMetrics = resources.displayMetrics
        val screenWith =
            displayMetrics.widthPixels - (resources.getDimension(com.intuit.sdp.R.dimen._15sdp) * 2) - (resources.getDimension(
                com.intuit.sdp.R.dimen._25sdp
            ) * 2)
        val itemWith = (screenWith / 3).toInt()

        adapterLevel = LevelAdapter(listLevel, this, itemWith)
        binding.recyclerViewLevels.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = adapterLevel
        }
    }

    override fun clickOnLevel(index: Int, lockItemSelected: Boolean) {

        if (!lockItemSelected) {
            Toast.makeText(requireContext(), index.toString(), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_levelsFragment_to_gameFragment)
        } else {
            Toast.makeText(requireContext(), "section is lock", Toast.LENGTH_SHORT).show()
        }
    }

}