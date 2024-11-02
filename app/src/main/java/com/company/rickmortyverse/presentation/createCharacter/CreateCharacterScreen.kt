import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.company.rickmortyverse.data.room.CharacterEntity
import com.company.rickmortyverse.presentation.characters.CharacterViewModel
import com.company.rickmortyverse.R
import com.company.rickmortyverse.ui.theme.Purple500

@Composable
fun CreateCharacterScreen(viewModel: CharacterViewModel = hiltViewModel()) {
    var name by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") } // New field for last known location
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showError by remember { mutableStateOf(false) }
    val context = LocalContext.current // Get the context for Toast

    // Image picker launcher
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.new_character),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Purple500
        )

        // Name Input Field
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.name)) },
            isError = showError && name.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )

        // Species Input Field
        TextField(
            value = species,
            onValueChange = { species = it },
            label = { Text(stringResource(R.string.species)) },
            isError = showError && species.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )

        // Gender Input Field
        TextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text(stringResource(R.string.gender)) },
            isError = showError && gender.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )

        // Last Known Location Input Field
        TextField(
            value = location,
            onValueChange = { location = it },
            label = { Text(stringResource(R.string.last_known_location)) },
            isError = showError && location.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )

        // Image Picker Button
        Button(
            onClick = { launcher.launch("image/*") },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple500),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.select_image), color = Color.White, fontSize = 16.sp
            )
        }

        // Image Preview
        imageUri?.let {
            Image(
                painter = rememberImagePainter(data = it),
                contentDescription = stringResource(R.string.character_image_description),
                modifier = Modifier
                    .size(150.dp)
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        // Error Message for Required Fields
        if (showError && (name.isBlank() || species.isBlank() || gender.isBlank() || location.isBlank())) {
            Text(
                text = stringResource(R.string.error_required_fields),
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save Character Button
        Button(
            onClick = {
                if (name.isNotBlank() && species.isNotBlank() && gender.isNotBlank() && location.isNotBlank()) {
                    viewModel.saveCharacter(
                        CharacterEntity(
                            name = name,
                            species = species,
                            gender = gender,
                            location = location, // Save the location
                            imageUri = imageUri?.toString()
                        )
                    )
                    // Display success Toast message
                    Toast.makeText(
                        context,
                        context.getString(R.string.character_saved_successfully),
                        Toast.LENGTH_SHORT
                    ).show()

                    // Reset fields after saving
                    name = ""
                    species = ""
                    gender = ""
                    location = ""
                    imageUri = null
                    showError = false
                } else {
                    showError = true // Show error if fields are missing
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple500),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(50.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.save_character),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
