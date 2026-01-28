package com.sayeedjoy.gymbro.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddWeightScreen(
    onSave: (dateEpochDay: Long, weightKg: Double) -> Unit,
    onCancel: () -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var weightText by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val displayFormatter = remember {
        DateTimeFormatter.ofPattern("EEEE, dd-MM-yyyy", Locale.getDefault())
    }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text("Add Weight Entry", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = selectedDate.format(displayFormatter),
            onValueChange = {},
            readOnly = true,
            label = { Text("Date") },
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Filled.DateRange, contentDescription = "Pick date")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = weightText,
            onValueChange = { input ->
                val cleaned = input
                    .replace(',', '.') // if user types comma, treat it as dot
                val valid = cleaned.count { it == '.' } <= 1 && cleaned.all { it.isDigit() || it == '.' }
                if (valid) weightText = cleaned
            },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
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
                error = null
                onSave(selectedDate.toEpochDay(), kg)
            }) {
                Text("Save")
            }
        }
    }
}
