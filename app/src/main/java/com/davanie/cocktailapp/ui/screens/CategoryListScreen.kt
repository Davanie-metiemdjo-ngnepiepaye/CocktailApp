package com.davanie.cocktailapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.davanie.cocktailapp.viewmodel.CocktailViewModel

@Composable
fun CategoryListScreen(
    navController: NavHostController,
    viewModel: CocktailViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    var searchText by remember { mutableStateOf("") }

    val filteredCategories = categories.filter {
        it.strCategory.contains(searchText, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "🍹 Catégories",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("🔍 Rechercher une catégorie...") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredCategories) { category ->
                CategoryItem(category = category.strCategory) {
                    val encoded = category.strCategory.replace(" ", "_")
                    navController.navigate("category/$encoded")
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp)
        ) {
            // Emoji utilisé directement à la place d'une icône vectorielle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFFFC107), shape = MaterialTheme.shapes.small),
                contentAlignment = Alignment.Center
            ) {
                Text("🍸", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = category,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
