package com.example.aonepermissions

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aone.permissions.hasPermission
import com.aone.permissions.ui.rememberPermissionRequester
import com.example.aonepermissions.ui.theme.AOnePermissionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AOnePermissionsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            revokeSelfPermissionOnKill(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

@Preview
@Composable
fun Main(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val permissionRequester = rememberPermissionRequester(
        Manifest.permission.POST_NOTIFICATIONS
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Execute code with required permission.
        Button(
            onClick = {
                permissionRequester.safeExecute {
                    // Do your work with required permission
                    Toast.makeText(context, "Do your work now", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Do work")
        }

        // Just request permission.
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                permissionRequester.request()
            }
        ) {
            Text("Request permission")
        }

        // Show content only when permission in granted.
        Spacer(Modifier.height(24.dp))
        if (permissionRequester.hasPermission()){
            Text("You have the permission")
        }
//        SafeBox(permissionRequester) {
//            Text("You have the permission")
//        }
    }
}