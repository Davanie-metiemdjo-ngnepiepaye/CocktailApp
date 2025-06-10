package com.davanie.cocktailapp.data.api

import com.davanie.cocktailapp.data.model.CategoryResponse
import com.davanie.cocktailapp.data.model.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse

    @GET("list.php?c=list")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getCocktailsByCategory(@Query("c") category: String): CocktailResponse

    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") id: String): CocktailResponse

}
