package com.elsewhere.eyris.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elsewhere.eyris.domain.model.Business

@Composable
fun DashboardScreen(
    leads: List<Business> = emptyList(),
    contacted: List<Business> = emptyList(),
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
                text = "Dashboard",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF1F5F9),
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // Pipeline Stats
        item {
            PipelineStatsCard(
                totalLeads = leads.size,
                totalContacted = contacted.size,
                conversionRate = if (leads.isNotEmpty()) {
                    (contacted.size.toDouble() / (leads.size + contacted.size) * 100).toInt()
                } else {
                    0
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Top Leads by Category
        item {
            Text(
                text = "Top Leads by Category",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFF1F5F9),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        items(leads.take(5)) { business ->
            TopLeadCard(business)
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Recent Activity
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Recent Activity",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFF1F5F9),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        items(contacted.take(5)) { business ->
            RecentActivityCard(business)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun PipelineStatsCard(
    totalLeads: Int,
    totalContacted: Int,
    conversionRate: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF16213E)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Leads Stat
            StatBox(
                label = "Leads",
                value = totalLeads.toString(),
                color = Color(0xFF7C3AED),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Contacted Stat
            StatBox(
                label = "Contacted",
                value = totalContacted.toString(),
                color = Color(0xFF22C55E),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Conversion Rate Stat
            StatBox(
                label = "Conversion",
                value = "$conversionRate%",
                color = Color(0xFF3B82F6),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatBox(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color(0xFF0F3460), RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF94A3B8)
        )
    }
}

@Composable
fun TopLeadCard(business: Business) {
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
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.TrendingUp,
                contentDescription = null,
                tint = Color(0xFF7C3AED),
                modifier = Modifier.width(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = business.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFF1F5F9),
                    maxLines = 1
                )
                Text(
                    text = business.category,
                    fontSize = 12.sp,
                    color = Color(0xFF94A3B8)
                )
            }

            if (business.rating != null) {
                Text(
                    text = "⭐ ${business.rating}",
                    fontSize = 12.sp,
                    color = Color(0xFF7C3AED),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun RecentActivityCard(business: Business) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF16213E)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(40.dp)
                    .background(Color(0xFF22C55E), RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = business.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFF1F5F9),
                    maxLines = 1
                )
                Text(
                    text = "Contacted • ${business.category}",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8)
                )
            }

            Text(
                text = "✓",
                fontSize = 16.sp,
                color = Color(0xFF22C55E),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
