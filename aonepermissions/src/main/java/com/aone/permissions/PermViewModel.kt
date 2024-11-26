package com.aone.permissions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PermViewModel {
    private val _isReRequestDialogVisible = MutableStateFlow(false)
    val isReRequestDialogVisible = _isReRequestDialogVisible.asStateFlow()

    var methodToExecute: (() -> Unit)? = null

    fun setIsReRequestDialogVisible(visible: Boolean) {
        _isReRequestDialogVisible.update { visible }
    }
}