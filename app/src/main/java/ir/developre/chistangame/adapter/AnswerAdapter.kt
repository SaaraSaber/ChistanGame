package ir.developre.chistangame.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.developre.chistangame.databinding.LayoutRecyclerViewEnterLetterBinding
import ir.developre.chistangame.model.AnswerModel
import ir.developre.chistangame.my_interface.on_click.ClickOnAnswer

class AnswerAdapter(
    private val listLetter: ArrayList<AnswerModel>,
    private val clickOnLetter: ClickOnAnswer,
    private val itemWith: Int
) :
    RecyclerView.Adapter<AnswerAdapter.ViewHolderLevel>() {

    private lateinit var binding: LayoutRecyclerViewEnterLetterBinding

    inner class ViewHolderLevel : RecyclerView.ViewHolder(binding.root) {
        var title = binding.textAnswer
        var layout = binding.layoutAnswer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLevel {
        binding = LayoutRecyclerViewEnterLetterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderLevel()
    }

    override fun getItemCount(): Int = listLetter.size

    override fun onBindViewHolder(holder: ViewHolderLevel, position: Int) {

        val item = listLetter[holder.adapterPosition]

        holder.layout.layoutParams.width = itemWith
        holder.layout.layoutParams.height = itemWith

        if (item.isHelp) {
            holder.title.text = item.characterHelp.toString()
        } else {
            holder.title.text = item.letter.toString()
        }

        if (!item.isShow) {
            holder.title.visibility = View.INVISIBLE
        } else {
            holder.title.visibility = View.VISIBLE
        }


        if (item.isHelp) {
            holder.layout.setOnClickListener {
                clickOnLetter.clickOnHelp(
                    holder.adapterPosition,
                    item.isHelp,item.isClick
                )
            }
        } else if (!item.isHelp && item.letter != null) {
            holder.layout.setOnClickListener {
                clickOnLetter.clickOnAnswer(
                    holder.adapterPosition,
                    item.letter!!,
                    item.positionLetter!!,
                    item.isHelp,item.isClick
                )
            }
        }


    }
}
