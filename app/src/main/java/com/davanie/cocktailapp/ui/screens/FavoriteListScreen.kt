package com.davanie.cocktailapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.davanie.cocktailapp.data.local.FavoriteCocktail
import com.davanie.cocktailapp.viewmodel.CocktailViewModel

@Composable
fun FavoriteListScreen(
    navController: NavController,
    viewModel: CocktailViewModel = hiltViewModel()
) {
    val favorites by viewModel.favorites.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "❤️ Mes favoris",
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (favorites.isEmpty()) {
            Text(
                "Aucun favori pour l’instant.",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(favorites) { cocktail ->
                    FavoriteCard(
                        cocktail = cocktail,
                        onClick = { navController.navigate("cocktail/${cocktail.id}") },
                        onDelete = { viewModel.removeFromFavorites(cocktail.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteCard(
    cocktail: FavoriteCocktail,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(cocktail.image),
                    contentDescription = cocktail.name,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(cocktail.name, fontSize = 18.sp)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer")
            }
        }
    }
}
