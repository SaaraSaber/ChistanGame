package ir.developre.chistangame.model

data class AnswerModel(
    val id: Int,
    val index: Int,
    var letter: Char? = null,
    var positionLetter: Int? = null,
    var isShow: Boolean,
    var isHelp: Boolean = false,
    var characterHelp: Char,
    var isClick: Boolean = true,
//    var isFinishLevel: Boolean
)
