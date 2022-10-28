package com.dmitriy.instagramclone.app.screens.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitriy.instagramclone.app.model.ExceptionInfo

open class BaseViewModel : ViewModel() {

    private val _exceptionInfoEvent = MutableLiveData<ExceptionInfo?>()
    val showExceptionInfoEvent: MutableLiveData<ExceptionInfo?> = _exceptionInfoEvent

    protected fun showExceptionInfo(
        code: Int?,
        message: String?,
        cause: Throwable?,
        messageForUser: String?
    ) {
        val exceptionInfo = ExceptionInfo(code, message, cause, messageForUser)
        _exceptionInfoEvent.value = exceptionInfo
    }

    protected fun hideExceptionInfo() { _exceptionInfoEvent.value = null }
}