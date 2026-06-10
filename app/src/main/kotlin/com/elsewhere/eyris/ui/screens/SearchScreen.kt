package com.elsewhere.eyris.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elsewhere.eyris.R
import com.elsewhere.eyris.domain.model.Business
import com.elsewhere.eyris.ui.components.BusinessCard
import com.elsewhere.eyris.ui.viewmodel.SearchState

@Composable
fun SearchScreen(
    query: String,
    location: String,
    category: String,
    searchState: SearchState,
    results: List<Business>,
    onQueryChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onBusinessClick: (Business) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
            .padding(16.dp)
    ) {
        // Header
        item {
            Text(
                text = "Search Businesses",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF1F5F9),
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // Search Inputs
        item {
            Column {
                // Query Input
                OutlinedTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    label = { Text("Business Type") },
                    placeholder = { Text("e.g., Restaurant, Salon") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF7C3AED),
                        unfocusedBorderColor = Color(0xFF16213E),
                        focusedTextColor = Color(0xFFF1F5F9),
                        unfocusedTextColor = Color(0xFFF1F5F9),
                        focusedLabelColor = Color(0xFF7C3AED),
                        unfocusedLabelColor = Color(0xFF94A3B8)
                    ),
                    singleLine = true,
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { onQueryChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = stringResource(R.string.search_clear),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Location Input
                OutlinedTextField(
                    value = location,
                    onValueChange = onLocationChange,
                    label = { Text("Location") },
                    placeholder = { Text("e.g., New York, London") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF7C3AED),
                        unfocusedBorderColor = Color(0xFF16213E),
                        focusedTextColor = Color(0xFFF1F5F9),
                        unfocusedTextColor = Color(0xFFF1F5F9),
                        focusedLabelColor = Color(0xFF7C3AED),
                        unfocusedLabelColor = Color(0xFF94A3B8)
                    ),
                    singleLine = true,
                    trailingIcon = {
                        if (location.isNotEmpty()) {
                            IconButton(onClick = { onLocationChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = stringResource(R.string.search_clear),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Category Input
                OutlinedTextField(
                    value = category,
                    onValueChange = onCategoryChange,
                    label = { Text("Category (Optional)") },
                    placeholder = { Text("e.g., Food & Drink") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF7C3AED),
                        unfocusedBorderColor = Color(0xFF16213E),
                        focusedTextColor = Color(0xFFF1F5F9),
                        unfocusedTextColor = Color(0xFFF1F5F9),
                        focusedLabelColor = Color(0xFF7C3AED),
                        unfocusedLabelColor = Color(0xFF94A3B8)
                    ),
                    singleLine = true,
                    trailingIcon = {
                        if (category.isNotEmpty()) {
                            IconButton(onClick = { onCategoryChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = stringResource(R.string.search_clear),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                            onSearch()
                        }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Search Button
                Button(
                    onClick = {
                        keyboardController?.hide()
                        onSearch()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7C3AED)
                    ),
                    enabled = searchState !is SearchState.Loading
                ) {
                    if (searchState is SearchState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.height(20.dp),
                            color = Color(0xFFF1F5F9),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color(0xFFF1F5F9),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Search", color = Color(0xFFF1F5F9))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Status Messages
        when (searchState) {
            is SearchState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                color = Color(0xFF7C3AED),
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Searching businesses...",
                                color = Color(0xFF94A3B8),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            is SearchState.Error -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFEF4444).copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = searchState.message,
                            color = Color(0xFFEF4444),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            is SearchState.Success -> {
                item {
                    Text(
                        text = "Found ${searchState.result.totalCount} businesses",
                        fontSize = 14.sp,
                        color = Color(0xFF94A3B8),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }

            SearchState.Idle -> {}
        }

        // Results
        items(results) { business ->
            BusinessCard(
                business = business,
                onClick = { onBusinessClick(business) },
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // Empty State
        if (results.isEmpty() && searchState is SearchState.Success) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No businesses found",
                        color = Color(0xFF94A3B8),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
