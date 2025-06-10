package com.davanie.cocktailapp.data.model

data class CategoryResponse(
    val drinks: List<Category>
)

data class Category(
    val strCategory: String
)
