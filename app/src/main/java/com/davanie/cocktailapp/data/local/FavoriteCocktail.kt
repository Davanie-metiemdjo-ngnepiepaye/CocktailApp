package com.davanie.cocktailapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteCocktail(
    @PrimaryKey val id: String,
    val name: String,
    val image: String
)
