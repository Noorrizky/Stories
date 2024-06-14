package com.example.myapplication.view.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class CustomEdit @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs), View.OnTouchListener {
    init {
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){
                val input = s.toString()
                val currentInputType = inputType

                when(currentInputType){
                    InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_CLASS_TEXT -> {
                        val Valid = android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
                        if(!Valid){
                            setError("Invalid Email", null)
                        }else{
                            error = null
                        }
                    }

                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT -> {
                        if(input.length < 8){
                            setError("Password must be at least 8 characters", null)
                        } else if (input.contains(" ")) {
                            setError("Password cannot contain spaces", null)
                        }else{
                            error = null
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setPadding(15,0,15,0)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return false
    }
}