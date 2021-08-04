package com.example.newsapplication.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.newsapplication.BR
import com.example.newsapplication.R
import com.example.newsapplication.utils.dismissLoadingDialog
import com.example.newsapplication.utils.showDialog
import com.example.newsapplication.utils.showLoadingDialog

abstract class BaseActivity<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> :
        AppCompatActivity() {

    protected lateinit var viewBinding: ViewBinding
    protected abstract val viewModel: ViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding.apply {
            setVariable(BR.viewModel, viewModel)
            viewBinding.lifecycleOwner = this@BaseActivity
            root.isClickable = true
            executePendingBindings()
        }
        observeErrorEvent()
    }

    private fun observeErrorEvent() {
        viewModel.apply {
            isLoading.observe(this@BaseActivity, {
                handleLoading(it == true)
            })
            errorMessage.observe(this@BaseActivity, {
                handleErrorMessage(it)
            })
            noInternetConnectionEvent.observe(this@BaseActivity, {
                handleErrorMessage(getString(R.string.no_internet_connection))
            })
            connectTimeoutEvent.observe(this@BaseActivity, {
                handleErrorMessage(getString(R.string.connect_timeout))
            })
            forceUpdateAppEvent.observe(this@BaseActivity, {
                handleErrorMessage(getString(R.string.force_update_app))
            })
            serverMaintainEvent.observe(this@BaseActivity, {
                handleErrorMessage(getString(R.string.server_maintain_message))
            })
            unknownErrorEvent.observe(this@BaseActivity, {
                handleErrorMessage(getString(R.string.unknown_error))
            })
        }
    }

    /**
     * override this if not use loading dialog (example progress bar)
     */
    protected open fun handleLoading(isLoading: Boolean){
        if (isLoading) showLoadingDialog() else dismissLoadingDialog()
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLoadingDialog()
        showDialog(message = message,
        textPositive = getString(R.string.ok))
    }

}