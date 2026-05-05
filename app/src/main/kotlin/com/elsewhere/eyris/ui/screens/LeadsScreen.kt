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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elsewhere.eyris.domain.model.Business
import com.elsewhere.eyris.domain.model.ContactStatus
import com.elsewhere.eyris.ui.components.BusinessCard

@Composable
fun LeadsScreen(
    leads: List<Business> = emptyList(),
    contacted: List<Business> = emptyList(),
    selectedTab: Int = 0,
    selectedStatus: ContactStatus? = null,
    onTabSelected: (Int) -> Unit,
    onStatusFilter: (ContactStatus?) -> Unit,
    onBusinessClick: (Business) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
    ) {
        // Header
        Text(
            text = if (selectedTab == 0) "Leads" else "Contacted",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF1F5F9),
            modifier = Modifier.padding(16.dp)
        )

        // Tab Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFF16213E))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabButton(
                text = "Leads",
                isSelected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TabButton(
                text = "Contacted",
                isSelected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                modifier = Modifier.weight(1f)
            )
        }

        // Status Filters (only for Contacted tab)
        if (selectedTab == 1) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                item {
                    FilterChip(
                        text = "All",
                        isSelected = selectedStatus == null,
                        onClick = { onStatusFilter(null) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                items(ContactStatus.values()) { status ->
                    FilterChip(
                        text = status.name,
                        isSelected = selectedStatus == status,
                        onClick = { onStatusFilter(status) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        // Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            val displayList = if (selectedTab == 0) leads else contacted

            if (displayList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (selectedTab == 0) "No leads yet" else "No contacted businesses",
                            color = Color(0xFF94A3B8),
                            fontSize = 16.sp
                        )
                    }
                }
            } else {
                items(displayList) { business ->
                    BusinessCard(
                        business = business,
                        onClick = { onBusinessClick(business) },
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onClick)
            .background(
                if (isSelected) Color(0xFF7C3AED) else Color.Transparent,
                RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFFF1F5F9) else Color(0xFF94A3B8),
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}

@Composable
fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(
                if (isSelected) Color(0xFF7C3AED) else Color(0xFF16213E),
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFFF1F5F9) else Color(0xFF94A3B8),
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}
