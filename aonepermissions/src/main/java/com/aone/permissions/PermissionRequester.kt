package com.aone.permissions

interface PermissionRequester {
    fun requestAndExecute(method: () -> Unit)
}