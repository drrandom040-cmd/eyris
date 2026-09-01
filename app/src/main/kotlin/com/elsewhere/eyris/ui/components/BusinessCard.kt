package com.elsewhere.eyris.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.elsewhere.eyris.R
import com.elsewhere.eyris.domain.model.Business

@Composable
fun BusinessCard(
    business: Business,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val clickLabel = stringResource(R.string.business_card_click_label)
    val instagramDesc = stringResource(R.string.social_instagram)
    val facebookDesc = stringResource(R.string.social_facebook)
    val tiktokDesc = stringResource(R.string.social_tiktok)
    val whatsappDesc = stringResource(R.string.social_whatsapp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF16213E))
            .clickable(onClickLabel = clickLabel, onClick = onClick)
            .padding(16.dp)
    ) {
        Column {
            // Cover Image
            if (!business.coverImageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = business.coverImageUrl,
                    contentDescription = business.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Business Name
            Text(
                text = business.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF1F5F9),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Category and Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = business.category,
                    fontSize = 12.sp,
                    color = Color(0xFF94A3B8)
                )
                Spacer(modifier = Modifier.width(8.dp))

                if (business.rating != null) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFF7C3AED),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${business.rating} (${business.reviewCount})",
                        fontSize = 12.sp,
                        color = Color(0xFF94A3B8)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Address
            Text(
                text = business.address,
                fontSize = 12.sp,
                color = Color(0xFF94A3B8),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Phone
            if (!business.phone.isNullOrEmpty()) {
                val phoneDesc = stringResource(R.string.social_phone, business.phone)
                Text(
                    text = "📞 ${business.phone}",
                    fontSize = 12.sp,
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.clearAndSetSemantics { contentDescription = phoneDesc }
                )
            }

            // Social Handles
            if (business.instagram != null || business.facebook != null || 
                business.tiktok != null || business.whatsapp != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    if (!business.instagram.isNullOrEmpty()) {
                        Text(
                            "📷",
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clearAndSetSemantics { contentDescription = instagramDesc }
                        )
                    }
                    if (!business.facebook.isNullOrEmpty()) {
                        Text(
                            "f",
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clearAndSetSemantics { contentDescription = facebookDesc }
                        )
                    }
                    if (!business.tiktok.isNullOrEmpty()) {
                        Text(
                            "🎵",
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clearAndSetSemantics { contentDescription = tiktokDesc }
                        )
                    }
                    if (!business.whatsapp.isNullOrEmpty()) {
                        Text(
                            "💬",
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clearAndSetSemantics { contentDescription = whatsappDesc }
                        )
                    }
                }
            }
        }
    }
}
