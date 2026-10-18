package com.elsewhere.eyris.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elsewhere.eyris.ui.screens.DashboardScreen
import com.elsewhere.eyris.ui.screens.SearchScreen
import com.elsewhere.eyris.ui.screens.LeadsScreen
import com.elsewhere.eyris.ui.screens.AuthScreen
import com.elsewhere.eyris.ui.viewmodel.AuthViewModel
import com.elsewhere.eyris.ui.viewmodel.AuthState
import com.elsewhere.eyris.ui.viewmodel.SearchViewModel
import com.elsewhere.eyris.ui.viewmodel.LeadsViewModel

@Composable
fun EyrisApp(
    authViewModel: AuthViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
    leadsViewModel: LeadsViewModel = hiltViewModel()
) {
    val authState = authViewModel.authState.value
    val isLoading = authViewModel.isLoading.value
    val errorMessage = authViewModel.errorMessage.value

    when (authState) {
        is AuthState.Checking -> {
            // Show splash/loading screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1A1A2E))
            )
        }

        is AuthState.Unauthenticated -> {
            AuthScreen(
                isLoading = isLoading,
                errorMessage = errorMessage,
                onGoogleSignIn = {
                    // Handle Google sign-in
                    authViewModel.signInAnonymously()
                },
                onAnonymousSignIn = {
                    authViewModel.signInAnonymously()
                }
            )
        }

        is AuthState.Authenticated -> {
            MainApp(
                searchViewModel = searchViewModel,
                leadsViewModel = leadsViewModel,
                authViewModel = authViewModel
            )
        }
    }
}

@Composable
fun MainApp(
    searchViewModel: SearchViewModel,
    leadsViewModel: LeadsViewModel,
    authViewModel: AuthViewModel
) {
    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
    ) {
        // Content
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (selectedTab) {
                0 -> DashboardScreen(
                    leads = leadsViewModel.leads.value,
                    contacted = leadsViewModel.contactedBusinesses.value
                )

                1 -> SearchScreen(
                    query = searchViewModel.searchQuery.value,
                    location = searchViewModel.location.value,
                    category = searchViewModel.category.value,
                    searchState = searchViewModel.searchState.value,
                    results = searchViewModel.searchResults.value,
                    onQueryChange = { searchViewModel.updateQuery(it) },
                    onLocationChange = { searchViewModel.updateLocation(it) },
                    onCategoryChange = { searchViewModel.updateCategory(it) },
                    onSearch = { searchViewModel.search(0.0, 0.0) },
                    onBusinessClick = { /* Navigate to detail */ }
                )

                2 -> LeadsScreen(
                    leads = leadsViewModel.leads.value,
                    contacted = leadsViewModel.contactedBusinesses.value,
                    selectedTab = leadsViewModel.selectedTab.value,
                    selectedStatus = leadsViewModel.selectedStatus.value,
                    onTabSelected = { leadsViewModel.selectTab(it) },
                    onStatusFilter = { leadsViewModel.filterByStatus(it) },
                    onBusinessClick = { /* Navigate to detail */ }
                )
            }
        }

        // Bottom Navigation Bar
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            containerColor = Color(0xFF16213E)
        ) {
            val items = listOf(
                BottomNavItem("Dashboard", Icons.Filled.Dashboard, 0),
                BottomNavItem("Search", Icons.Filled.Search, 1),
                BottomNavItem("Leads", Icons.Filled.Folder, 2)
            )

            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 10.sp
                        )
                    },
                    selected = selectedTab == item.index,
                    onClick = { selectedTab = item.index },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF7C3AED),
                        selectedTextColor = Color(0xFF7C3AED),
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8),
                        indicatorColor = Color(0xFF16213E)
                    )
                )
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val index: Int
)
