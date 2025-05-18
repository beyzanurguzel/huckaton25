package com.example.myapplication.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.viewmodel.RankingsViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingsScreen(
    navController: NavController,
    viewModel: RankingsViewModel = viewModel()
) {
    val scope by viewModel.scope.collectAsState()
    val list by viewModel.currentList.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sıralamalar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // 1) Kapsam Seçimi
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ScopeButton(
                    label = "Mahalle",
                    selected = scope == RankingsViewModel.Scope.NEIGHBORHOOD
                ) { viewModel.setScope(RankingsViewModel.Scope.NEIGHBORHOOD) }
                ScopeButton(
                    label = "Şehir",
                    selected = scope == RankingsViewModel.Scope.CITY
                ) { viewModel.setScope(RankingsViewModel.Scope.CITY) }
                ScopeButton(
                    label = "Ülke",
                    selected = scope == RankingsViewModel.Scope.COUNTRY
                ) { viewModel.setScope(RankingsViewModel.Scope.COUNTRY) }
            }

            Spacer(Modifier.height(16.dp))

            // 2) Sıralama Listesi
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(list) { index, entry ->
                    val isCurrentUser = entry.name.startsWith("Sen")
                    RankingRow(
                        position = index + 1,
                        name = entry.name,
                        score = entry.score,
                        highlight = isCurrentUser
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.ScopeButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = if (selected) {
            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        } else {
            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .weight(1f)
            .height(40.dp)
    ) {
        Text(
            text = label,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun RankingRow(position: Int, name: String, score: Int, highlight: Boolean) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (highlight) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("$position.", modifier = Modifier.width(32.dp), style = MaterialTheme.typography.bodyLarge)
            Text(name, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            Text("$score", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
