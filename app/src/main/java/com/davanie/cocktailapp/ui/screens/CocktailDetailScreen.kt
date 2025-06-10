package com.davanie.cocktailapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.davanie.cocktailapp.data.model.Cocktail
import com.davanie.cocktailapp.viewmodel.CocktailViewModel
import kotlinx.coroutines.launch

@Composable
fun CocktailDetailScreen(
    cocktail: Cocktail?,
    navController: NavController,
    viewModel: CocktailViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    if (cocktail == null) {
        Text("Chargement...", fontSize = 20.sp)
        return
    }

    val ingredients = getIngredients(cocktail)

    Column(modifier = Modifier.padding(16.dp)) {

        // Bouton retour
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("← Retour")
        }

        Image(
            painter = rememberAsyncImagePainter(cocktail.strDrinkThumb),
            contentDescription = cocktail.strDrink,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text("Nom : ${cocktail.strDrink}", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Instructions :", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(cocktail.strInstructions ?: "Aucune instruction", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(12.dp))
        Text("Ingrédients :", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))

        Column {
            ingredients.forEach { ingredient ->
                Text("• $ingredient", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            coroutineScope.launch {
                viewModel.addToFavorites(cocktail)
            }
        }) {
            Text("❤️ Ajouter aux favoris")
        }
    }
}

// Fonction getIngredients identique à celle que tu as déjà
fun getIngredients(cocktail: Cocktail): List<String> {
    val ingredients = mutableListOf<String>()

    fun combine(ingredient: String?, measure: String?): String? {
        if (ingredient.isNullOrBlank()) return null
        return if (!measure.isNullOrBlank()) "$measure $ingredient" else ingredient
    }

    listOf(
        combine(cocktail.strIngredient1, cocktail.strMeasure1),
        combine(cocktail.strIngredient2, cocktail.strMeasure2),
        combine(cocktail.strIngredient3, cocktail.strMeasure3),
        combine(cocktail.strIngredient4, cocktail.strMeasure4),
        combine(cocktail.strIngredient5, cocktail.strMeasure5),
        combine(cocktail.strIngredient6, cocktail.strMeasure6),
        combine(cocktail.strIngredient7, cocktail.strMeasure7),
        combine(cocktail.strIngredient8, cocktail.strMeasure8),
        combine(cocktail.strIngredient9, cocktail.strMeasure9),
        combine(cocktail.strIngredient10, cocktail.strMeasure10),
        combine(cocktail.strIngredient11, cocktail.strMeasure11),
        combine(cocktail.strIngredient12, cocktail.strMeasure12),
        combine(cocktail.strIngredient13, cocktail.strMeasure13),
        combine(cocktail.strIngredient14, cocktail.strMeasure14),
        combine(cocktail.strIngredient15, cocktail.strMeasure15)
    ).forEach { line ->
        line?.let { ingredients.add(it) }
    }

    return ingredients
}
