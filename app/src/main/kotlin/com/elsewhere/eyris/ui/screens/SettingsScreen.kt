package com.elsewhere.eyris.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    userEmail: String? = null,
    onLogout: () -> Unit,
    onExport: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
            .padding(16.dp)
    ) {
        // Header
        item {
            Text(
                text = "Settings",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF1F5F9),
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // Account Section
        item {
            Text(
                text = "Account",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF94A3B8),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF16213E)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Email",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = userEmail ?: "Not signed in",
                            fontSize = 14.sp,
                            color = Color(0xFFF1F5F9),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Data Section
        item {
            Text(
                text = "Data",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF94A3B8),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        item {
            SettingsItem(
                icon = Icons.Filled.Settings,
                title = "Export Data",
                subtitle = "Export leads and contacted businesses",
                onClick = onExport
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        // App Section
        item {
            Text(
                text = "App",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF94A3B8),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        item {
            SettingsItem(
                icon = Icons.Filled.Settings,
                title = "About",
                subtitle = "Eyris v1.0.0",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(12.dp))

            SettingsItem(
                icon = Icons.Filled.Settings,
                title = "Privacy Policy",
                subtitle = "View our privacy policy",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Logout Button
        item {
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF4444)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout",
                    tint = Color(0xFFF1F5F9),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text("Logout", color = Color(0xFFF1F5F9))
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF16213E)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFF7C3AED),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.size(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = Color(0xFFF1F5F9),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF94A3B8)
                )
            }

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
