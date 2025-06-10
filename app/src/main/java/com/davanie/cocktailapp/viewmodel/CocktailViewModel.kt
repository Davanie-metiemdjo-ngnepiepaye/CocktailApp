package com.davanie.cocktailapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davanie.cocktailapp.data.model.Category
import com.davanie.cocktailapp.data.model.Cocktail
import com.davanie.cocktailapp.data.repository.CocktailRepository
import com.davanie.cocktailapp.data.local.FavoriteCocktail
import com.davanie.cocktailapp.data.local.FavoriteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val repository: CocktailRepository,
    private val favoriteDao: FavoriteDao
) : ViewModel() {

    private val _randomCocktail = MutableStateFlow<Cocktail?>(null)
    val randomCocktail: StateFlow<Cocktail?> get() = _randomCocktail

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    private val _cocktails = MutableStateFlow<List<Cocktail>>(emptyList())
    val cocktails: StateFlow<List<Cocktail>> get() = _cocktails

    private val _selectedCocktail = MutableStateFlow<Cocktail?>(null)
    val selectedCocktail: StateFlow<Cocktail?> get() = _selectedCocktail

    private val _favorites = MutableStateFlow<List<FavoriteCocktail>>(emptyList())
    val favorites: StateFlow<List<FavoriteCocktail>> get() = _favorites

    init {
        // Charger les favoris depuis la base de données au démarrage
        viewModelScope.launch {
            favoriteDao.getAllFavorites().collect {
                _favorites.value = it
            }
        }
    }

    // === API Calls ===

    fun fetchRandomCocktail() {
        viewModelScope.launch {
            try {
                _randomCocktail.value = repository.getRandomCocktail().drinks.firstOrNull()
            } catch (e: Exception) {
                e.printStackTrace()
                _randomCocktail.value = null
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                _categories.value = repository.getCategories().drinks
            } catch (e: Exception) {
                e.printStackTrace()
                _categories.value = emptyList()
            }
        }
    }

    fun fetchCocktailsByCategory(category: String) {
        viewModelScope.launch {
            try {
                _cocktails.value = repository.getCocktailsByCategory(category).drinks
            } catch (e: Exception) {
                e.printStackTrace()
                _cocktails.value = emptyList()
            }
        }
    }

    fun fetchCocktailById(id: String) {
        viewModelScope.launch {
            try {
                _selectedCocktail.value = repository.getCocktailById(id).drinks.firstOrNull()
            } catch (e: Exception) {
                e.printStackTrace()
                _selectedCocktail.value = null
            }
        }
    }

    // === Gestion des favoris ===

    fun addToFavorites(cocktail: Cocktail): Unit {
        viewModelScope.launch {
            try {
                val favorite = FavoriteCocktail(
                    id = cocktail.idDrink,
                    name = cocktail.strDrink,
                    image = cocktail.strDrinkThumb
                )
                favoriteDao.insertFavorite(favorite)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeFromFavorites(id: String): Unit {
        viewModelScope.launch {
            try {
                favoriteDao.deleteFavoriteById(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun isFavorite(id: String): Boolean =
        _favorites.value.any { it.id == id }
}
