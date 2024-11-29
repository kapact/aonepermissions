package com.aone.permissions.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class PermViewModel {
    private val _isReRequestDialogVisible = MutableStateFlow(false)
    internal val isReRequestDialogVisible = _isReRequestDialogVisible.asStateFlow()

    internal var methodToExecute: (() -> Unit)? = null

    internal fun setIsReRequestDialogVisible(visible: Boolean) {
        _isReRequestDialogVisible.update { visible }
    }
}