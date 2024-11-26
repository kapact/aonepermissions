package com.aone.permissions

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun rememberPermissionRequester(
    permission: String
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
                }
            } else {
                viewModel.setIsReRequestDialogVisible(true)
            }
        }

    val permissionRequester = object : PermissionRequester {
        override fun requestAndExecute(method: () -> Unit) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) {
                viewModel.methodToExecute = method
                permissionLauncher.launch(permission)
            } else {
                method()
            }
        }
    }

    if (isReRequestDialogVisible) {
        PermissionDialog(
            permissionTextProvider = MyPermissionTextProvider(),
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