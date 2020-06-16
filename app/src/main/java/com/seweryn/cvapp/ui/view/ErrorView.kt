package com.seweryn.cvapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.seweryn.cvapp.R
import com.seweryn.cvapp.ui.extensions.showConditionally
import com.seweryn.cvapp.viewmodel.liveDataModels.Error
import com.seweryn.cvapp.viewmodel.liveDataModels.ErrorAction
import kotlinx.android.synthetic.main.view_error.view.*

class ErrorView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_error, this, true)
    }

    fun setError(error: Error) {
        when (error) {
            is Error.ConnectionError -> {
                error_message.text = resources.getString(R.string.no_internet_error_message)
                error_image.setImageResource(R.drawable.ic_error_connection)
            }
            else -> {
                error_message.text = resources.getString(R.string.generic_error_message)
                error_image.setImageResource(R.drawable.ic_error_general)
            }
        }
        error_action.showConditionally(error.errorAction != null)
        error.errorAction?.let { action ->
            when (action) {
                is ErrorAction.Retry -> error_action.text =
                    resources.getString(R.string.action_retry)
            }
            error_action.setOnClickListener { action.action.invoke() }
        }
    }

}