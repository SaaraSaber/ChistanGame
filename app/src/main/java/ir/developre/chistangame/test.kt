package ir.developre.chistangame


/*    private fun createDynamicEditText(count: Int) {
        val typeface =
            Typeface.createFromAsset(requireActivity().assets, "fonts/lalezar_regular.ttf")

        for (i in 1..count) {
            val editText = EditText(requireContext())
            editText.layoutParams =
                LayoutParams(170, 170)
            editText.setBackgroundResource(R.drawable.simple_shape_background_recycler_view_enter_letter)
            editText.maxLines = 1
            editText.typeface = typeface
            editText.textSize = 28f
            editText.layoutDirection = View.LAYOUT_DIRECTION_RTL
            editText.textDirection = View.TEXT_DIRECTION_RTL
            editText.isFocusableInTouchMode = false
            editText.isAllCaps = true
            editText.isCursorVisible = false
            editText.gravity = Gravity.CENTER
            editText.setTextColor(requireContext().getColor(R.color.brightMango))
            editText.setPadding(4, 0, 4, 0)

            editText.setOnClickListener {
                if (i - 1 == currentEditTextIndex + 1) {
                    val removedLetter = editText.text.toString().firstOrNull()
                    editText.setText("")
                    currentEditTextIndex++


                    listSelectedLetter.forEachIndexed { index, selectedLetter ->

                    }
                    listLetterAdapter.find { it.letter == removedLetter }?.isShow = true

//                    adapterLetter.notifyItemChanged (index)

                }
            }

            //set edittext in linearlayout
            binding.layoutAnswer.addView(editText)

            editTexts.add(editText)

            //set margin
            val margin: ViewGroup.MarginLayoutParams =
                editText.layoutParams as ViewGroup.MarginLayoutParams
            margin.setMargins(4, 0, 4, 0)
        }
    }*/

/*
    private fun checkAnswer() {
        val allText = StringBuilder()
        for (editText in editTexts) {
            allText.append(editText.text.toString())
        }

        val answerUser = allText.reverse().toString()
        Toast.makeText(requireContext(), answerUser, Toast.LENGTH_SHORT).show()

        if (answer == answerUser) {
            Toast.makeText(requireContext(), "آفرین!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "اشتباه!", Toast.LENGTH_SHORT).show()
        }
    }*/