package ir.developre.chistangame.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.developre.chistangame.databinding.LayoutRecyclerViewSelectLettersBinding
import ir.developre.chistangame.model.LetterModel
import ir.developre.chistangame.my_interface.on_click.ClickOnLetter

class LetterAdapter(
    private val listLetter: ArrayList<LetterModel>,
    private val clickOnLetter: ClickOnLetter,
) :
    RecyclerView.Adapter<LetterAdapter.ViewHolderLevel>() {

    private lateinit var binding: LayoutRecyclerViewSelectLettersBinding

    inner class ViewHolderLevel : RecyclerView.ViewHolder(binding.root) {
        var title = binding.textLetter
        var layout = binding.layoutLetter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLevel {
        binding = LayoutRecyclerViewSelectLettersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderLevel()
    }

    override fun getItemCount(): Int = listLetter.size

    override fun onBindViewHolder(holder: ViewHolderLevel, position: Int) {

        val item = listLetter[holder.adapterPosition]

        holder.title.text = item.letter.toString()

        if (!item.isShow) {
            holder.layout.visibility = View.INVISIBLE
        } else {
            holder.layout.visibility = View.VISIBLE
        }

        holder.itemView.setOnClickListener {

            clickOnLetter.clickOnLetter(
                holder.adapterPosition,
                item.letter, holder.layout
            )

//            if (!Utils.isAllEditTextsFilled) {
//                holder.layout.visibility = View.INVISIBLE
//            }
        }

    }

    fun showLetter(letter: Char) {

    }
}


//        val position = listLetter.indexOf(letter)
//        if (position >= 0) {
//            binding.layoutLetter.visibility = View.VISIBLE
//            notifyItemChanged(position)
//        }