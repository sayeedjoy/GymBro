package com.sayeedjoy.gymbro.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayeedjoy.gymbro.data.getWorkoutsForToday
import com.sayeedjoy.gymbro.model.Workout
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutScreen() {
    val workoutList = getWorkoutsForToday()
    val currentDay = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val today = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM", Locale.ENGLISH)
    val formattedDate = today.format(dateFormatter)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "App Icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "GymBro",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Today's Workout",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "$currentDay, $formattedDate",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Spacer(modifier = Modifier.padding( top = 16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(workoutList) { workout ->
                    WorkoutItemCard(workout = workout) {
                        workout.checked = it
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutItemCard(workout: Workout, onCheckChange: (Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = workout.sets,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Checkbox(
                checked = workout.checked,
                onCheckedChange = onCheckChange
            )
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun WorkOutPreview(){
    WorkoutScreen()
}