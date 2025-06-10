package com.davanie.cocktailapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.davanie.cocktailapp.data.model.Cocktail

@Composable
fun CategoryCocktailsScreen(
    cocktails: List<Cocktail>,
    navController: NavController
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Cocktails", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(cocktails) { cocktail ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // 🔗 Navigation vers l’écran de détail du cocktail
                            navController.navigate("cocktail/${cocktail.idDrink}")
                        }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(cocktail.strDrinkThumb),
                        contentDescription = cocktail.strDrink,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(end = 12.dp)
                    )
                    Text(
                        text = cocktail.strDrink,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
