package com.davanie.cocktailapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.davanie.cocktailapp.data.model.Cocktail
import com.davanie.cocktailapp.viewmodel.CocktailViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RandomCocktailScreen(
    viewModel: CocktailViewModel = hiltViewModel()
) {
    val cocktail by viewModel.randomCocktail.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRandomCocktail()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (cocktail != null) {
            CocktailContent(cocktail!!)
        } else {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CocktailContent(cocktail: Cocktail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(cocktail.strDrinkThumb),
            contentDescription = cocktail.strDrink,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Nom : ${cocktail.strDrink}", fontSize = 20.sp)
        cocktail.strInstructions?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Instructions :", fontSize = 18.sp)
            Text(it, fontSize = 16.sp)
        }
    }
}
