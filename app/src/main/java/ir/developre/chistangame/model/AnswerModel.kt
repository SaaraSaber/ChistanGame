package ir.developre.chistangame.model

data class AnswerModel(
    val id: Int,
    val index: Int,
    var letter: Char? = null,
    var positionLetter: Int? = null,
    var isShow: Boolean
)
