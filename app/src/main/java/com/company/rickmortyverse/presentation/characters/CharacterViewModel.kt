package com.company.rickmortyverse.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.company.rickmortyverse.data.MainCharacter
import com.company.rickmortyverse.data.repository.CharacterRepository
import com.company.rickmortyverse.data.room.CharacterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characters = MutableStateFlow<List<MainCharacter>>(emptyList())
    val characters: StateFlow<List<MainCharacter>> = _characters
    val localCharacters = repository.getLocalCharacters().asLiveData()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            repository.getApiCharacters()
                .onStart { _isLoading.value = true }
                .catch { e ->
                    _isLoading.value = false
                    _errorMessage.value = e.message
                }
                .collect { result ->
                    _isLoading.value = false
                    result.onSuccess { _characters.value = it }
                    result.onFailure { _errorMessage.value = it.message }
                }
        }
    }

    fun saveCharacter(character: CharacterEntity) {
        viewModelScope.launch {
            repository.saveCharacter(character)
        }
    }

    // Function to delete a character
    fun deleteCharacter(character: CharacterEntity) {
        viewModelScope.launch {
            repository.deleteCharacter(character)
        }
    }
}
