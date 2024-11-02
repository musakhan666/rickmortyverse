package com.company.rickmortyverse.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val species: String,
    val gender: String,
    val imageUri: String? = null,
    val location: String? = null

)

