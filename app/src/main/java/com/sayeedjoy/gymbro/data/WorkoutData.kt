package com.sayeedjoy.gymbro.data
import com.sayeedjoy.gymbro.model.Workout
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun getWorkoutsForToday(): List<Workout> {
    val today = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

    return when (today) {
        "Saturday" -> listOf(
            Workout("Cycling", "10 Min"),
            Workout("Warm-up", "10 Min"),
            Workout("Floor Pushups", "4×10"),
            Workout("Stand Pushups", "4×10"),
            Workout("Bar Dips", "4×10"),
            Workout("Back Dips", "4×10"),
            Workout("Chin Ups", "4×10"),
            Workout("Parallel Bar", "3×10"),
            Workout("Lat Pull down Back + Front", "3×10"),
            Workout("Seated Cable Row", "3×10"),
            Workout("Incline T-bar", "3×10"),
            Workout("Free Squat + Sit-up Bench", "3×10")
        )
        "Sunday" -> listOf(
                Workout("Floor Pushup", "4×10"),
                Workout("Stand Pushup", "4×10"),
                Workout("Bar Dips", "4×10"),
                Workout("Back Dips", "4×10"),
                Workout("Chin Ups", "4×10"),
                Workout("Flat Bench Press", "3×10"),
                Workout("Incline Bench Press", "3×10"),
                Workout("Decline Bench Press", "3×10"),
                Workout("Incline Dumbbell Press", "3×10"),
                Workout("Dumbbell Bench Flies", "2×10"),
                Workout("Dumbbell Pullover", "3×10"),
                Workout("Free Squat + Sit-up Bench", "3×10")
            )
        "Monday" -> listOf(
                Workout("Floor Pushup", "4×10"),
                Workout("Stand Pushup", "4×10"),
                Workout("Bar Dips", "4×10"),
                Workout("Back Dips", "4×10"),
                Workout("Barbell Curls", "3×10"),
                Workout("Overhead Barbell Extension Triceps", "3×10"),
                Workout("Dumbbell Bicep Curl", "3×10"),
                Workout("One Arm Triceps Extension (Both arms)", "4×10"),
                Workout("Machine Preacher Curl", "3×10"),
                Workout("Triceps Rope Pushdown", "3×10"),
                Workout("Cable Bicep Curls", "3×10"),
                Workout("Free Squat Bench Belly", "3×10")
            )
        "Tuesday" -> listOf(
                Workout("Walking Lunges", "3×10"),
                Workout("Dumbbell Sumo Squat", "3×10"),
                Workout("Barbell Squat", "3×10"),
                Workout("Leg Extension", "3×10"),
                Workout("Leg Press", "3×10"),
                Workout("Leg Curl", "3×10"),
                Workout("Deadlift (Legs)", "3×10"),
                Workout("Calf Raises", "3×10")
            )
        "Wednesday" -> listOf(
                Workout("Barbell Shoulder Press (Front + Back)", "4×10"),
                Workout("Dumbbell Shoulder Press", "3×10"),
                Workout("Dumbbell Lateral Raise", "3×10"),
                Workout("Dumbbell Front Raise", "3×10"),
                Workout("Barbell Front Raise", "3×10"),
                Workout("Upright Row", "3×10"),
                Workout("Barbell Shrugs", "3×10"),
                Workout("Parallel Bar", "3×10"),
                Workout("Barbell Wrist Curl", "3×10"),
                Workout("Floor Pushup", "3×10")
            )
        "Thursday" -> listOf(
                Workout("Cycling", "10 Min"),
                Workout("Warm-ups", "10 Min"),
                Workout("Floor Pushup", "4×10"),
                Workout("Stand Pushup", "4×10"),
                Workout("Bar Dips", "4×10"),
                Workout("Back Dips", "4×10"),
                Workout("Chin-ups", "4×10"),
                Workout("Parallel Bar", "4×10"),
                Workout("Free Squat", "4×10"),
                Workout("Leg Raise", "4×10"),
                Workout("Barbell Wrist Curl", "4×10"),
                Workout("Sit-up Bench", "4×10")
            )
        else -> listOf(
            Workout("Rest Day", "Enjoy Madafaka!")
        )
    }
}

fun getWeeklyWorkoutSchedule(): Map<String, List<Workout>> {
    return mapOf(
        "Saturday" to listOf(
            Workout("Cycling", "10 Min"),
            Workout("Warm-up", "10 Min"),
            Workout("Floor Pushups", "4×10"),
            Workout("Stand Pushups", "4×10"),
            Workout("Bar Dips", "4×10"),
            Workout("Back Dips", "4×10"),
            Workout("Chin Ups", "4×10"),
            Workout("Parallel Bar", "3×10"),
            Workout("Lat Pull down Back + Front", "3×10"),
            Workout("Seated Cable Row", "3×10"),
            Workout("Incline T-bar", "3×10"),
            Workout("Free Squat + Sit-up Bench", "3×10")
        ),
        "Sunday" to listOf(
            Workout("Floor Pushup", "4×10"),
            Workout("Stand Pushup", "4×10"),
            Workout("Bar Dips", "4×10"),
            Workout("Back Dips", "4×10"),
            Workout("Chin Ups", "4×10"),
            Workout("Flat Bench Press", "3×10"),
            Workout("Incline Bench Press", "3×10"),
            Workout("Decline Bench Press", "3×10"),
            Workout("Incline Dumbbell Press", "3×10"),
            Workout("Dumbbell Bench Flies", "2×10"),
            Workout("Dumbbell Pullover", "3×10"),
            Workout("Free Squat + Sit-up Bench", "3×10")
        ),
        "Monday" to listOf(
            Workout("Floor Pushup", "4×10"),
            Workout("Stand Pushup", "4×10"),
            Workout("Bar Dips", "4×10"),
            Workout("Back Dips", "4×10"),
            Workout("Barbell Curls", "3×10"),
            Workout("Overhead Barbell Extension Triceps", "3×10"),
            Workout("Dumbbell Bicep Curl", "3×10"),
            Workout("One Arm Triceps Extension (Both arms)", "4×10"),
            Workout("Machine Preacher Curl", "3×10"),
            Workout("Triceps Rope Pushdown", "3×10"),
            Workout("Cable Bicep Curls", "3×10"),
            Workout("Free Squat Bench Belly", "3×10")
        ),
        "Tuesday" to listOf(
            Workout("Walking Lunges", "3×10"),
            Workout("Dumbbell Sumo Squat", "3×10"),
            Workout("Barbell Squat", "3×10"),
            Workout("Leg Extension", "3×10"),
            Workout("Leg Press", "3×10"),
            Workout("Leg Curl", "3×10"),
            Workout("Deadlift (Legs)", "3×10"),
            Workout("Calf Raises", "3×10")
        ),
        "Wednesday" to listOf(
            Workout("Barbell Shoulder Press (Front + Back)", "4×10"),
            Workout("Dumbbell Shoulder Press", "3×10"),
            Workout("Dumbbell Lateral Raise", "3×10"),
            Workout("Dumbbell Front Raise", "3×10"),
            Workout("Barbell Front Raise", "3×10"),
            Workout("Upright Row", "3×10"),
            Workout("Barbell Shrugs", "3×10"),
            Workout("Parallel Bar", "3×10"),
            Workout("Barbell Wrist Curl", "3×10"),
            Workout("Floor Pushup", "3×10")
        ),
        "Thursday" to listOf(
            Workout("Cycling", "10 Min"),
            Workout("Warm-ups", "10 Min"),
            Workout("Floor Pushup", "4×10"),
            Workout("Stand Pushup", "4×10"),
            Workout("Bar Dips", "4×10"),
            Workout("Back Dips", "4×10"),
            Workout("Chin-ups", "4×10"),
            Workout("Parallel Bar", "4×10"),
            Workout("Free Squat", "4×10"),
            Workout("Leg Raise", "4×10"),
            Workout("Barbell Wrist Curl", "4×10"),
            Workout("Sit-up Bench", "4×10")
        ),
        "Friday" to listOf(
            Workout("Rest Day", "Enjoy Madafaka!")
        )
    )
}