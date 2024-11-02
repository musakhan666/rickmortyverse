package com.company.rickmortyverse.presentation.localCharacterScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.company.rickmortyverse.data.room.CharacterEntity
import com.company.rickmortyverse.presentation.characters.CharacterViewModel
import com.company.rickmortyverse.R

@Composable
fun UserCharacterScreen(viewModel: CharacterViewModel = hiltViewModel()) {
    val localCharacters = viewModel.localCharacters.observeAsState(emptyList())

    if (localCharacters.value.isEmpty()) {

        // Show text when no characters are available

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.no_characters_message),
                fontSize = 18.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }

    } else {
        // Show character list when characters are available
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxSize()
        ) {
            items(localCharacters.value.size) { index ->
                val character = localCharacters.value[index]
                CharacterCard(character) {
                    viewModel.deleteCharacter(it)
                }
            }
        }
    }
}


@Composable
fun CharacterCard(
    character: CharacterEntity,
    onDelete: (CharacterEntity) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color(0xFF2D2D2D),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .height(160.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // Character Image
                character.imageUri?.let {
                    Image(
                        painter = rememberImagePainter(data = it),
                        contentDescription = "${character.name} Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .fillMaxHeight()
                            .align(Alignment.CenterVertically)
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.ic_placeholder),
                    contentDescription = stringResource(R.string.character_image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Character Info
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = character.name,
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Species: ${character.species}",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Gender: ${character.gender}",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Last known location
                    character.location?.let {
                        Text(
                            text = "Last known location: $it",
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )
                    }
                }
            }

            // Delete Icon Button
            IconButton(
                onClick = { onDelete(character) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    tint = Color.Red
                )
            }
        }
    }
}