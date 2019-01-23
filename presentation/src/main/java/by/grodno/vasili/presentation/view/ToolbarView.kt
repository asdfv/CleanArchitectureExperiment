package by.grodno.vasili.presentation.view

import android.app.Activity
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import by.grodno.vasili.presentation.R
import by.grodno.vasili.presentation.databinding.ToolbarViewBinding

/**
 * Top toolbar with customizable title, ok and cancel handlers. </br>
 * Optional attributes for component in XML:
 *
 *  * app:title - title for toolbar (default: empty) </br>
 *  * app:onOkClick - handler for click on check-icon (default: close activity) </br>
 *  * app:onCancelClick - handler for click on close-icon (default: close activity) </br>
 *
 */
class ToolbarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        ConstraintLayout(context, attrs, defStyle) {

    private var binding: ToolbarViewBinding =
            DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.toolbar_view, this, true)

    init {
        setDefaultListeners(context as Activity, binding)
    }

    private fun setDefaultListeners(context: Activity, binding: ToolbarViewBinding) {
        binding.checkImage.setOnClickListener { context.finish() }
        binding.closeImage.setOnClickListener { context.finish() }
    }

    /**
     * Setter for optional attribute app:title - title for toolbar
     */
    @Suppress("unused")
    fun setTitle(title: String) {
        binding.toolbarTitle.text = title
    }

    /**
     * Setter for optional attribute app:onOkClick
     *
     * @param okHandler handler for check-icon pressing
     */
    @Suppress("unused")
    fun setOnOkClick(okHandler: Runnable) {
        binding.checkImage.setOnClickListener { okHandler.run() }
    }

    /**
     * Setter for optional attribute app:onCancelClick
     *
     * @param cancelHandler handler for close-icon pressing
     */
    @Suppress("unused")
    fun setOnCancelClick(cancelHandler: Runnable) {
        binding.closeImage.setOnClickListener { cancelHandler.run() }
    }
}
