package com.aone.permissions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class PermissionRequester {

    private val _hasPermission = MutableStateFlow(false)
    internal val hasPermission = _hasPermission.asStateFlow()

    abstract fun request()
    abstract fun safeExecute(method: () -> Unit)
    internal fun setHasPermission(hasPer: Boolean) {
        _hasPermission.value = hasPer
    }
}