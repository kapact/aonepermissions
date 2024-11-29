package com.aone.permissions.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
internal fun PermissionDialog(
    permissionRationalProvider: PermissionRationalProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = if (isPermanentlyDeclined) {
                        "Grant permission"
                    } else {
                        "OK"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        },
        title = {
            Text(
                text = "Permission required",
                fontWeight = FontWeight.W500
            )
        },
        text = {
            Text(
                text = permissionRationalProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                ),
                fontWeight = FontWeight.W400
            )
        },
        modifier = modifier,
        shape = RoundedCornerShape(10.dp)
    )
}

interface PermissionRationalProvider {
    fun getTitle(isPermanentlyDeclined: Boolean): String
    fun getDescription(isPermanentlyDeclined: Boolean): String
}