package com.aone.permissions.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aone.permissions.PermissionRequester


/**
 *  Use this Box composable to show content which require a particular
 *  permission. Content inside this box will only appear when app is
 *  granted permission defined in [PermissionRequester] passed
 *  as @param to this composable.
 *
 *  @param permissionRequester: Same [PermissionRequester] used to request permission
 *  @param modifier The modifier to be applied to the layout.
 *  @param contentAlignment The default alignment inside the Box.
 *  @param propagateMinConstraints Whether the incoming min constraints should be passed to content.
 *  @param content The content of the [Box].
 *
 */
@Composable
fun SafeBox(
    permissionRequester: PermissionRequester,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {

    val hasPermission by permissionRequester.hasPermission.collectAsState()

    if (hasPermission) {
        Box(
            modifier = modifier,
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints,
            content = content
        )
    }
}