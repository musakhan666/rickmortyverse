package com.company.rickmortyverse.presentation.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.company.rickmortyverse.data.MainCharacter


@Composable
fun HomeScreen(viewModel: CharacterViewModel = hiltViewModel()) {
    val isLoading = viewModel.isLoading.collectAsState() // Assume loading state
    val characters = viewModel.characters.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1E1E1E) // Dark background for the entire screen
    ) {
        if (isLoading.value) {
            // Show loading indicator
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            // Display characters in a grid-like fashion
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(characters.value.size) { index ->
                    val mainCharacter = characters.value[index]
                    CharacterCard(mainCharacter)
                }
                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
fun CharacterCard(character: MainCharacter) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2D2D2D)), // Dark gray background for the card
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .height(120.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Character Image
            Image(
                painter = rememberImagePainter(data = character.image),
                contentDescription = "${character.name} Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Character Info
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = character.name ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Circle,
                        contentDescription = null,
                        tint = if (character.status == "Alive") Color.Green else Color.Red,
                        modifier = Modifier.size(10.dp)
                    )
                    Text(
                        text = "${character.status} - ${character.species}",
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Last known location:",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = character.location?.name ?: "-",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
