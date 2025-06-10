package com.davanie.cocktailapp.data.repository

import com.davanie.cocktailapp.data.api.CocktailApi
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val api: CocktailApi
) {
    suspend fun getRandomCocktail() = api.getRandomCocktail()
    suspend fun getCategories() = api.getCategories()
    suspend fun getCocktailsByCategory(category: String) = api.getCocktailsByCategory(category)
    suspend fun getCocktailById(id: String) = api.getCocktailById(id)

}
