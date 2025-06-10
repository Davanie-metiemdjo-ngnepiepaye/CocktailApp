package com.davanie.cocktailapp.di

import com.davanie.cocktailapp.data.api.CocktailApi
import com.davanie.cocktailapp.data.repository.CocktailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCocktailApi(retrofit: Retrofit): CocktailApi =
        retrofit.create(CocktailApi::class.java)

    @Provides
    @Singleton
    fun provideCocktailRepository(api: CocktailApi): CocktailRepository =
        CocktailRepository(api)
}
