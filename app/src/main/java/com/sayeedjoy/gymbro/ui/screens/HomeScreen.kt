package com.sayeedjoy.gymbro.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sayeedjoy.gymbro.R
import com.sayeedjoy.gymbro.model.Workout
import com.sayeedjoy.gymbro.model.WorkoutViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GymBroTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.dumbbell),
            modifier = Modifier.size(32.dp),
            contentDescription = "App Icon",
            tint = Color.Unspecified
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = "GymBro",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = viewModel(),
    navController: NavController
) {
    val workoutList = viewModel.workoutList
    val today = LocalDate.now()
    val currentDay = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM", Locale.ENGLISH)
    val formattedDate = today.format(dateFormatter)

    val total = workoutList.size
    val done = workoutList.count { it.checked }
    val progress by animateFloatAsState(
        targetValue = if (total == 0) 0f else done.toFloat() / total,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "progress"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GymBroTopBar()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),

            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 12.dp,
                bottom = 0.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                TodayBanner(
                    title = "Today's Workout",
                    subtitle = "$currentDay, $formattedDate",
                    done = done,
                    total = total,
                    progress = progress
                )
            }

            if (workoutList.isEmpty()) {
                item {
                    EmptyStateCard()
                }
            } else {
                itemsIndexed(workoutList) { index, workout ->
                    WorkoutItemCard(
                        workout = workout,
                        onCheckChange = { isChecked ->
                            viewModel.toggleChecked(workout, isChecked)
                        },
                        onClick = {
                            viewModel.toggleChecked(workout, !workout.checked)
                        },
                        showCheckbox = true
                    )
                }
                item { Spacer(Modifier.height(72.dp)) }
            }
        }
    }
}

@Composable
private fun TodayBanner(
    title: String,
    subtitle: String,
    done: Int,
    total: Int,
    progress: Float
) {
    val gradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
        )
    )

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .background(gradient, RoundedCornerShape(24.dp))
                .padding(22.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(18.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CapsuleStat(text = "$done/$total completed")
                AnimatedVisibility(visible = total > 0) {
                    CapsuleStat(text = "${(progress * 100).toInt()}%")
                }
            }

            Spacer(Modifier.height(14.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(99.dp)),
                trackColor = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.5f),
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun CapsuleStat(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun WorkoutItemCard(
    workout: Workout,
    onCheckChange: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
    showCheckbox: Boolean = true
) {
    val checked = workout.checked
    val isDarkTheme = isSystemInDarkTheme()

    val containerColor by animateColorAsState(
        targetValue = when {
            checked && isDarkTheme -> MaterialTheme.colorScheme.surfaceContainerHighest
            checked && !isDarkTheme -> MaterialTheme.colorScheme.surfaceContainer
            else -> MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(350),
        label = "cardColor"
    )

    val borderColor by animateColorAsState(
        targetValue = when {
            checked -> MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            isDarkTheme -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        },
        animationSpec = tween(350),
        label = "borderColor"
    )

    val textAlpha by animateFloatAsState(
        targetValue = if (checked) 0.5f else 1f,
        animationSpec = tween(350),
        label = "textAlpha"
    )

    val badgeBackground by animateColorAsState(
        targetValue = when {
            checked -> MaterialTheme.colorScheme.surfaceContainerHigh
            else -> MaterialTheme.colorScheme.secondaryContainer
        },
        animationSpec = tween(350),
        label = "badgeBackground"
    )

    val badgeTextColor by animateColorAsState(
        targetValue = when {
            checked -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            else -> MaterialTheme.colorScheme.onSecondaryContainer
        },
        animationSpec = tween(350),
        label = "badgeTextColor"
    )

    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (checked) 0.dp else 2.dp
        ),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showCheckbox) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = onCheckChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Spacer(Modifier.width(12.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = textAlpha)
                )
                Spacer(Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(badgeBackground)
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = workout.sets,
                        style = MaterialTheme.typography.labelLarge,
                        color = badgeTextColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyStateCard() {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.dumbbell),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "No workouts yet",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
