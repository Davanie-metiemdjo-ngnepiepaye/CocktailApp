package com.davanie.cocktailapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.davanie.cocktailapp.ui.screens.RandomCocktailScreen
import com.davanie.cocktailapp.ui.screens.CategoryListScreen
import com.davanie.cocktailapp.ui.screens.CategoryCocktailsScreen
import com.davanie.cocktailapp.ui.screens.CocktailDetailScreen
import com.davanie.cocktailapp.ui.screens.FavoriteListScreen
import com.davanie.cocktailapp.viewmodel.CocktailViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Random.route,
        modifier = modifier
    ) {
        // Onglet 1 : Cocktail Aléatoire
        composable(BottomNavItem.Random.route) {
            RandomCocktailScreen()
        }

        // Onglet 2 : Liste des Catégories
        composable(BottomNavItem.Categories.route) {
            CategoryListScreen(navController)
        }

        // Liste des cocktails d'une catégorie
        composable("category/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")?.replace("_", " ") ?: ""
            val viewModel: CocktailViewModel = hiltViewModel()

            LaunchedEffect(category) {
                viewModel.fetchCocktailsByCategory(category)
            }

            val cocktails by viewModel.cocktails.collectAsState()
            CategoryCocktailsScreen(cocktails, navController)
        }

        // Détail d’un cocktail
        composable("cocktail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val viewModel: CocktailViewModel = hiltViewModel()

            LaunchedEffect(id) {
                viewModel.fetchCocktailById(id)
            }

            val cocktail by viewModel.selectedCocktail.collectAsState()
            CocktailDetailScreen(cocktail, navController)
        }

        // Onglet 3 : Liste des Favoris
        composable(BottomNavItem.Favorites.route) {
            FavoriteListScreen(navController)
        }
    }
}
