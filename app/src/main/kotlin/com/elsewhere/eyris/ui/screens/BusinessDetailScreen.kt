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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.elsewhere.eyris.domain.model.Business

@Composable
fun BusinessDetailScreen(
    business: Business,
    onBack: () -> Unit,
    onSocialHandleClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
    ) {
        // Header with back button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFFF1F5F9),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = Color(0xFF7C3AED),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Cover Image
        if (!business.coverImageUrl.isNullOrEmpty()) {
            item {
                AsyncImage(
                    model = business.coverImageUrl,
                    contentDescription = business.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
        }

        // Business Info
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                // Name
                Text(
                    text = business.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF1F5F9)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Category and Rating
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = business.category,
                        fontSize = 14.sp,
                        color = Color(0xFF94A3B8)
                    )

                    if (business.rating != null) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFF7C3AED),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${business.rating} (${business.reviewCount})",
                            fontSize = 14.sp,
                            color = Color(0xFF7C3AED),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Address
                DetailRow(label = "Address", value = business.address)

                // Phone
                if (!business.phone.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(label = "Phone", value = business.phone)
                }

                // Email
                if (!business.email.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(label = "Email", value = business.email)
                }

                // Website
                if (!business.website.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(label = "Website", value = business.website)
                }

                // Opening Hours
                if (!business.openingHours.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(label = "Hours", value = business.openingHours)
                }
            }
        }

        // Social Media Section
        if (business.instagram != null || business.facebook != null ||
            business.tiktok != null || business.whatsapp != null
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Connect",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    if (!business.instagram.isNullOrEmpty()) {
                        SocialButton(
                            icon = "📷",
                            label = "Instagram",
                            onClick = { onSocialHandleClick("instagram", business.instagram) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (!business.facebook.isNullOrEmpty()) {
                        SocialButton(
                            icon = "f",
                            label = "Facebook",
                            onClick = { onSocialHandleClick("facebook", business.facebook) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (!business.tiktok.isNullOrEmpty()) {
                        SocialButton(
                            icon = "🎵",
                            label = "TikTok",
                            onClick = { onSocialHandleClick("tiktok", business.tiktok) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (!business.whatsapp.isNullOrEmpty()) {
                        SocialButton(
                            icon = "💬",
                            label = "WhatsApp",
                            onClick = { onSocialHandleClick("whatsapp", business.whatsapp) }
                        )
                    }
                }
            }
        }

        // Call Button
        if (!business.phone.isNullOrEmpty()) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7C3AED)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "Call",
                        tint = Color(0xFFF1F5F9),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Call Business", color = Color(0xFFF1F5F9))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF94A3B8),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color(0xFFF1F5F9)
        )
    }
}

@Composable
fun SocialButton(
    icon: String,
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color(0xFF16213E), RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = icon,
                fontSize = 20.sp,
                modifier = Modifier.clearAndSetSemantics { }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color(0xFFF1F5F9),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
