package com.aone.permissions

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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


/** Composable helper function to get live state of permission */
@Composable
fun PermissionRequester.hasPermission(): Boolean {
    return hasPermission.collectAsStateWithLifecycle().value
}