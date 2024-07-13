//package ir.developre.chistangame.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import ir.developre.chistangame.databinding.LayoutRecyclerViewEnterLetterBinding
//import ir.developre.chistangame.my_interface.on_click.ClickOnAnswer
//
//class AnswerAdapter(
//    private val listAnswer: ArrayList<Char>,
//    private val clickOnAnswer: ClickOnAnswer,
//) :
//    RecyclerView.Adapter<AnswerAdapter.ViewHolderLevel>() {
//
//    private lateinit var binding: LayoutRecyclerViewEnterLetterBinding
//
//    inner class ViewHolderLevel : RecyclerView.ViewHolder(binding.root) {
//
//        var title = binding.textAnswer
//        var layout = binding.textAnswer
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLevel {
//        binding = LayoutRecyclerViewEnterLetterBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return ViewHolderLevel()
//    }
//
//    override fun getItemCount(): Int = listAnswer.size
//
//    override fun onBindViewHolder(holder: ViewHolderLevel, position: Int) {
//        val item = listAnswer[holder.adapterPosition]
//
////        holder.title.text = item.toString()
//        holder.layout.setOnClickListener {
//            clickOnAnswer.clickOnAnswer(
//                holder.adapterPosition,
//                item
//            )
//        }
//    }
//}