package com.company.rickmortyverse.presentation.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.rickmortyverse.presentation.characters.CharacterCard
import com.company.rickmortyverse.presentation.characters.CharacterViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.rickmortyverse.R

@Composable
fun ExploreScreen(viewModel: CharacterViewModel = hiltViewModel()) {
    var filterText by remember { mutableStateOf("") }
    val apiCharacters = viewModel.characters.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        Text(
            text = stringResource(R.string.explore_characters),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Filter TextField with Clear Button
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = filterText,
                onValueChange = { filterText = it },
                label = { Text(stringResource(R.string.filter_by_name)) },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (filterText.isNotEmpty()) {
                Button(onClick = { filterText = "" }) {
                    Text(text = stringResource(R.string.clear))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Loading Indicator
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (isError?.isNotEmpty() == true) {
            // Error Message
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error_loading_characters),
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        } else {
            // Filtered Character List or Empty State
            val characters = apiCharacters.value.filter {
                it.name?.contains(filterText, ignoreCase = true) == true
            }

            if (characters.isEmpty()) {
                // Show message if no characters match the filter
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_characters_found),
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            } else {
                // Show character list
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(characters.size) { index ->
                        val character = characters[index]
                        CharacterCard(character)
                    }
                }
            }
        }
    }
}
