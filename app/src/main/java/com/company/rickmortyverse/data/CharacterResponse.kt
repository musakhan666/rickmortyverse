package com.company.rickmortyverse.data

// Main response data class
data class CharacterResponse(
    val info: Info? = null,
    val results: List<MainCharacter>? = null
)

// Info data class for pagination
data class Info(
    val count: Int? = null,
    val pages: Int? = null,
    val next: String? = null,
    val prev: String? = null
)

// Character data class
data class MainCharacter(
    val id: Int? = null,
    val name: String? = null,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null,
    val origin: Origin? = null,
    val location: Location? = null,
    val image: String? = null,
    val episode: List<String>? = null,
    val url: String? = null,
    val created: String? = null
)

// Origin data class
data class Origin(
    val name: String? = null,
    val url: String? = null
)

// Location data class
data class Location(
    val name: String? = null,
    val url: String? = null
)
