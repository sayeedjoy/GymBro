package com.sayeedjoy.gymbro.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddWeightScreen(
    onSave: (dateEpochDay: Long, weightKg: Double) -> Unit,
    onCancel: () -> Unit
) {
    var dateText by remember { mutableStateOf(LocalDate.now().toString()) }
    var weightText by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text("Add Weight Entry", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = dateText,
            onValueChange = { dateText = it },
            label = { Text("Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = weightText,
            onValueChange = { weightText = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onCancel) { Text("Cancel") }

            Button(onClick = {
                val kg = weightText.toDoubleOrNull()
                if (kg == null || kg <= 0) {
                    error = "Enter a valid weight (e.g., 72.5)"
                    return@Button
                }

                val parsedDate = try {
                    LocalDate.parse(dateText.trim())
                } catch (e: Exception) {
                    error = "Date must be like 2026-01-28"
                    return@Button
                }

                onSave(parsedDate.toEpochDay(), kg)
            }) {
                Text("Save")
            }
        }
    }
}
