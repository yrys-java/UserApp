package com.example.userapp.core.ui.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.userapp.R
import com.example.userapp.databinding.WidgetTitledTextBinding

class TitledTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = WidgetTitledTextBinding.inflate(
        LayoutInflater.from(context),
        this,
    )

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitledTextView)
            binding.titleView.text = typedArray.getString(R.styleable.TitledTextView_title)

            typedArray.recycle()
        }
        orientation = VERTICAL
    }

    var title: String
        get() = binding.titleView.text.toString()
        set(value) {
            binding.titleView.text = value
        }

    var text: String
        get() = binding.textView.text.toString()
        set(value) {
            binding.textView.text = value
        }
}