package ir.developre.chistangame.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.developre.chistangame.ClickOnLevel
import ir.developre.chistangame.databinding.LayoutRecycleViewLevelsBinding
import ir.developre.chistangame.model.LevelModel

class LevelAdapter(
    private val listLevel: ArrayList<LevelModel>,
    private val clickOnLevel: ClickOnLevel,
    private val itemWith: Int
) :
    RecyclerView.Adapter<LevelAdapter.ViewHolderLevel>() {

    private lateinit var binding: LayoutRecycleViewLevelsBinding

    inner class ViewHolderLevel : RecyclerView.ViewHolder(binding.root) {
        var textLevel = binding.textLevel
        var layoutLevel = binding.layoutLevel
        var imageLock = binding.imageLock
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLevel {
        binding = LayoutRecycleViewLevelsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderLevel()
    }

    override fun getItemCount(): Int = listLevel.size

    override fun onBindViewHolder(holder: ViewHolderLevel, position: Int) {
        val item = listLevel[position]

        holder.layoutLevel.layoutParams.width = itemWith
        holder.layoutLevel.layoutParams.height = itemWith
        holder.layoutLevel
        if (item.imageLock) {
            holder.imageLock.visibility = View.VISIBLE
            holder.textLevel.visibility = View.GONE
        } else {
            holder.imageLock.visibility = View.GONE
            holder.textLevel.visibility = View.VISIBLE
        }

        holder.textLevel.text = item.textNum.toString()

        holder.layoutLevel.setOnClickListener {
            clickOnLevel.clickOnLevel(
                position,
                item.imageLock
            )
        }

    }
}