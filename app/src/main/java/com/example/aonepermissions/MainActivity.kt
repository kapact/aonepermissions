package com.example.aonepermissions

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.aone.permissions.rememberPermissionRequester
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

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Button(
            onClick = {
                permissionRequester.requestAndExecute {
                    // Do your work with required permission
                    Toast.makeText(context, "Do your work now", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Do work")
        }
    }
}