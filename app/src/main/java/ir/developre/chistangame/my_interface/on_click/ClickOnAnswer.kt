package ir.developre.chistangame.my_interface.on_click

interface ClickOnAnswer {
    fun clickOnAnswer(
        index: Int,
        letter: Char? = null,
        positionLetter: Int? = null,
        isHelp: Boolean,
        isClick:Boolean
    )

    fun clickOnHelp(
        index: Int,
        isHelp: Boolean,
        isClick:Boolean
    )
}