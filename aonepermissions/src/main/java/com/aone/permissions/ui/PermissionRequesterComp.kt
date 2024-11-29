package com.aone.permissions.ui

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import com.aone.permissions.PermissionRequester
import com.aone.permissions.util.findActivity
import com.aone.permissions.util.openAppSettings
import com.aone.permissions.viewmodel.PermViewModel

@Composable
fun rememberPermissionRequester(
    permission: String,
    permissionRational: PermissionRationalProvider = PermissionRequesterDefaults.permissionRational()
): PermissionRequester {

    val context = LocalContext.current
    val viewModel: PermViewModel = remember {
        PermViewModel()
    }
    val isReRequestDialogVisible by viewModel.isReRequestDialogVisible.collectAsState()

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                viewModel.methodToExecute?.let { func ->
                    func()
                    viewModel.methodToExecute = null
                }
            } else {
                viewModel.setIsReRequestDialogVisible(true)
            }
        }

    val permissionRequester = remember {
        object : PermissionRequester() {
            override fun request() {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED

                if (!hasPermission) {
                    viewModel.methodToExecute = {
                        setHasPermission(true)
                    }
                    permissionLauncher.launch(permission)
                } else {
                    setHasPermission(true)
                }
            }

            override fun safeExecute(method: () -> Unit) {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED

                if (!hasPermission) {
                    viewModel.methodToExecute = {
                        setHasPermission(true)
                        method()
                    }
                    permissionLauncher.launch(permission)
                } else {
                    setHasPermission(true)
                    method()
                }
            }
        }
    }

    val checkAndSetPermission = {
        permissionRequester.setHasPermission(
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    LaunchedEffect(Unit) {
        checkAndSetPermission()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.RESUMED -> {
                checkAndSetPermission()
            }

            else -> Unit
        }
    }

    if (isReRequestDialogVisible) {
        PermissionDialog(
            permissionRationalProvider = permissionRational,
            isPermanentlyDeclined = !(context.findActivity()
                ?.shouldShowRequestPermissionRationale(permission) ?: false),
            onDismiss = { viewModel.setIsReRequestDialogVisible(false) },
            onOkClick = {
                viewModel.setIsReRequestDialogVisible(false)
                permissionLauncher.launch(permission)
            },
            onGoToAppSettingsClick = {
                viewModel.setIsReRequestDialogVisible(false)
                context.findActivity()?.openAppSettings()
            }
        )
    }

    return permissionRequester
}