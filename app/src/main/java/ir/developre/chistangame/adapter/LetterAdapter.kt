package ir.developre.chistangame.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.developre.chistangame.my_interface.on_click.ClickOnLetter
import ir.developre.chistangame.databinding.LayoutRecyclerViewSelectLettersBinding

class LetterAdapter(
    private val listLetter: ArrayList<Char>,
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

        holder.title.text = item.toString()
        holder.layout.setOnClickListener { clickOnLetter.clickOnLetter(holder.adapterPosition) }
    }
}