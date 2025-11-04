package app.mobile.formapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import app.mobile.formapp.ui.theme.FormAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.sin

// Data class untuk state UI
data class FormUiState(
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val fullName: String = "",
    val isValidName: Boolean = true,
    val isValidPhone: Boolean = true,
    val isValidEmail: Boolean = true
)

// ViewModel untuk state dan validasi
class FormViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FormUiState())
    val uiState: StateFlow<FormUiState> = _uiState

    fun onFirstNameChange(newValue: String) {
        _uiState.update { it.copy(firstName = newValue) }
    }

    fun onLastNameChange(newValue: String) {
        _uiState.update { it.copy(lastName = newValue) }
    }

    fun onPhoneChange(newValue: String) {
        _uiState.update { it.copy(phone = newValue) }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun validateAndSubmit() {
        val state = _uiState.value
        val validName = state.firstName.isNotBlank() && state.lastName.isNotBlank()
        val validPhone = state.phone.isNotBlank()
        val validEmail = state.email.contains("@") && state.email.contains(".")
        _uiState.update {
            it.copy(
                isValidName = validName,
                isValidPhone = validPhone,
                isValidEmail = validEmail,
                fullName = if (validName) "${state.firstName} ${state.lastName}" else ""
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(formViewModel: FormViewModel = viewModel()) {
    val uiState by formViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4CAF50), // hijau muda
                        Color(0xFF81C784), // hijau terang
                        Color(0xFFB2DFDB)  // hijau kebiruan muda
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Form Input Data",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF2E7D32)
                )

                OutlinedTextField(
                    value = uiState.firstName,
                    onValueChange = formViewModel::onFirstNameChange,
                    label = { Text("Nama Depan") },
                    singleLine = true,
                    isError = !uiState.isValidName,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color.DarkGray,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                OutlinedTextField(
                    value = uiState.lastName,
                    onValueChange = formViewModel::onLastNameChange,
                    label = { Text("Nama Belakang") },
                    singleLine = true,
                    isError = !uiState.isValidName,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color.DarkGray,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                OutlinedTextField(
                    value = uiState.phone,
                    onValueChange = formViewModel::onPhoneChange,
                    label = { Text("Nomor Telepon")},
                    singleLine = true,
                    isError = !uiState.isValidPhone,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color.DarkGray,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = formViewModel::onEmailChange,
                    label = { Text("Email") },
                    singleLine = true,
                    isError = !uiState.isValidEmail,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color.DarkGray,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Button(
                    onClick = { formViewModel.validateAndSubmit() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Submit")
                }

                if (!uiState.isValidName)
                    Text("Nama depan dan belakang wajib diisi!", color = Color.Red)
                if (!uiState.isValidPhone)
                    Text("Nomor telepon wajib diisi!", color = Color.Red)
                if (!uiState.isValidEmail)
                    Text("Masukkan email yang valid!", color = Color.Red)

                if (uiState.fullName.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Divider(color = Color.LightGray)
                    Spacer(Modifier.height(8.dp))
                    Text("Nama Lengkap: ${uiState.fullName}", color = Color.Black)
                    Text("Nomor: ${uiState.phone}", color = Color.Black)
                    Text("Email: ${uiState.email}", color = Color.DarkGray)
                }
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FormAppTheme {
                AppScreen()
            }
        }
    }
}
