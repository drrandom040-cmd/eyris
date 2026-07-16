package com.elsewhere.eyris.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
    val focusManager = LocalFocusManager.current
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
            val searchFields = remember(query, location, category, onQueryChange, onLocationChange, onCategoryChange) {
                listOf(
                    Triple(query, onQueryChange, R.string.search_query_label to R.string.search_query_hint),
                    Triple(location, onLocationChange, R.string.search_location_label to R.string.search_location_hint),
                    Triple(category, onCategoryChange, R.string.search_category_label to R.string.search_category_hint)
                )
            }

            Column {
                // Search Input Fields
                searchFields.forEachIndexed { index, (value, onChange, labels) ->
                    val isLast = index == 2
                    OutlinedTextField(
                        value = value,
                        onValueChange = onChange,
                        label = { Text(stringResource(labels.first)) },
                        placeholder = { Text(stringResource(labels.second)) },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            if (value.isNotEmpty()) {
                                IconButton(onClick = { onChange("") }) {
                                    Icon(Icons.Filled.Clear, stringResource(R.string.search_clear), tint = Color(0xFF94A3B8))
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = if (isLast) ImeAction.Search else ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Next) },
                            onSearch = {
                                onSearch()
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        ),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF7C3AED),
                            unfocusedBorderColor = Color(0xFF16213E),
                            focusedTextColor = Color(0xFFF1F5F9),
                            unfocusedTextColor = Color(0xFFF1F5F9),
                            focusedLabelColor = Color(0xFF7C3AED),
                            unfocusedLabelColor = Color(0xFF94A3B8)
                        ),
                        singleLine = true
                    )
                    if (!isLast) Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Search Button
                Button(
                    onClick = {
                        onSearch()
                        keyboardController?.hide()
                        focusManager.clearFocus()
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
