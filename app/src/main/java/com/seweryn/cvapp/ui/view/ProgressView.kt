package com.seweryn.cvapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.seweryn.cvapp.R
import kotlinx.android.synthetic.main.view_progress.view.*

class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_progress, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ProgressView, 0, 0)
            val message = resources.getText(
                typedArray.getResourceId(
                    R.styleable.ProgressView_progress_message,
                    R.string.progress_default_message
                )
            )
            progress_message.text = message
        }
    }
}