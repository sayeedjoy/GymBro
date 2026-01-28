package com.sayeedjoy.gymbro.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable

private fun getGreeting(): String {
    val hour = LocalTime.now().hour
    return when {
        hour < 12 -> "Good Morning"
        hour < 17 -> "Good Afternoon"
        else -> "Good Evening"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = viewModel(),
    navController: NavController,
    onSettingsClick: () -> Unit = {}
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

    val isAllComplete = total > 0 && done == total

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 12.dp,
                bottom = 0.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                GreetingSection(
                    greeting = getGreeting(),
                    onSettingsClick = onSettingsClick
                )
            }

            item {
                TodayBanner(
                    title = "Today's Workout",
                    subtitle = "$currentDay, $formattedDate",
                    done = done,
                    total = total,
                    progress = progress,
                    isAllComplete = isAllComplete
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your Exercises",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "$total exercises",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (workoutList.isEmpty()) {
                item {
                    EmptyStateCard()
                }
            } else {
                itemsIndexed(workoutList) { index, workout ->
                    WorkoutItemCard(
                        workout = workout,
                        index = index + 1,
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
private fun GreetingSection(
    greeting: String,
    onSettingsClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = greeting,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Let's crush your workout today!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .clickable { onSettingsClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.settings),
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(22.dp)
            )
        }
    }
    Spacer(Modifier.height(20.dp))
}

@Composable
private fun TodayBanner(
    title: String,
    subtitle: String,
    done: Int,
    total: Int,
    progress: Float,
    isAllComplete: Boolean
) {
    val gradient = Brush.linearGradient(
        colors = if (isAllComplete) {
            listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.12f)
            )
        } else {
            listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
            )
        }
    )

    val cardScale by animateFloatAsState(
        targetValue = if (isAllComplete) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cardScale"
    )

    Card(
        modifier = Modifier.scale(cardScale),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isAllComplete) 4.dp else 2.dp)
    ) {
        Column(
            modifier = Modifier
                .background(gradient, RoundedCornerShape(28.dp))
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    AnimatedContent(
                        targetState = isAllComplete,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(300)) + scaleIn() togetherWith
                                fadeOut(animationSpec = tween(300)) + scaleOut()
                        },
                        label = "titleAnimation"
                    ) { complete ->
                        Text(
                            text = if (complete) "All Done!" else title,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = if (complete) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                }

                CircularProgressRing(
                    progress = progress,
                    done = done,
                    total = total,
                    isComplete = isAllComplete
                )
            }

            Spacer(Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CapsuleStat(
                    text = "$done/$total completed",
                    isHighlighted = isAllComplete
                )
                AnimatedVisibility(visible = total > 0) {
                    CapsuleStat(
                        text = "${(progress * 100).toInt()}%",
                        isHighlighted = isAllComplete
                    )
                }
            }

            AnimatedVisibility(
                visible = isAllComplete,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Column {
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Great job! You crushed it!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CircularProgressRing(
    progress: Float,
    done: Int,
    total: Int,
    isComplete: Boolean
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "circularProgress"
    )

    val ringColor by animateColorAsState(
        targetValue = if (isComplete) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary,
        animationSpec = tween(300),
        label = "ringColor"
    )

    val ringScale by animateFloatAsState(
        targetValue = if (isComplete) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "ringScale"
    )

    Box(
        modifier = Modifier
            .size(72.dp)
            .scale(ringScale),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 8.dp,
            trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            strokeCap = StrokeCap.Round,
            color = MaterialTheme.colorScheme.surfaceContainerHighest
        )
        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 8.dp,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Round,
            color = ringColor
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$done",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "of $total",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CapsuleStat(
    text: String,
    isHighlighted: Boolean = false
) {
    val bgColor by animateColorAsState(
        targetValue = if (isHighlighted)
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
        else
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
        animationSpec = tween(300),
        label = "capsuleBg"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
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
    index: Int = 0,
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
            checked -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
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

    val cardScale by animateFloatAsState(
        targetValue = if (checked) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardScale"
    )

    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
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
            width = if (checked) 1.5.dp else 1.dp,
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
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(
                            if (checked) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceContainerHigh
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        targetState = checked,
                        transitionSpec = {
                            scaleIn(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) togetherWith
                                scaleOut()
                        },
                        label = "checkAnimation"
                    ) { isChecked ->
                        if (isChecked) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text(
                                text = "$index",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                Spacer(Modifier.width(14.dp))
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

            Checkbox(
                checked = checked,
                onCheckedChange = onCheckChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                )
            )
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
                .padding(40.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.dumbbell),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.height(20.dp))
            Text(
                "No workouts yet",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Add exercises to start tracking",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
