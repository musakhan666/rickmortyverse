package com.company.rickmortyverse.di

import android.content.Context
import androidx.room.Room
import com.company.rickmortyverse.data.RickAndMortyApi
import com.company.rickmortyverse.data.repository.CharacterRepository
import com.company.rickmortyverse.data.room.AppDatabase
import com.company.rickmortyverse.data.room.CharacterDao
import com.company.rickmortyverse.utills.MAIN_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideCharacterDao(db: AppDatabase): CharacterDao = db.characterDao()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideRickAndMortyApi(retrofit: Retrofit): RickAndMortyApi =
        retrofit.create(RickAndMortyApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(
        api: RickAndMortyApi,
        dao: CharacterDao
    ): CharacterRepository = CharacterRepository(api, dao)
}
