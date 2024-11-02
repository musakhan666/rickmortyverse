package com.company.rickmortyverse.data.repository

import com.company.rickmortyverse.data.MainCharacter
import com.company.rickmortyverse.data.RickAndMortyApi
import com.company.rickmortyverse.data.room.CharacterDao
import com.company.rickmortyverse.data.room.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApi,
    private val dao: CharacterDao
) {
    // Function to fetch characters from the API with error handling and loading state
    fun getApiCharacters(): Flow<Result<List<MainCharacter>>> = flow {
        try {
            val response = api.getCharacters()
            if (response.isSuccessful) {
                val characters = response.body()?.results ?: emptyList()
                emit(Result.success(characters))
            } else {
                emit(Result.failure(Exception("API Error: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.onStart {
        // Emitting a loading state as an empty success or using a loading status in the ViewModel
        emit(Result.success(emptyList()))
    }

    fun getLocalCharacters() = dao.getAllCharacters()

    suspend fun saveCharacter(character: CharacterEntity) {
        dao.insertCharacter(character)
    }

    suspend fun deleteCharacter(character: CharacterEntity) {
        dao.deleteCharacter(character)
    }
}
