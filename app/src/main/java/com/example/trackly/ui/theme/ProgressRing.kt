package com.example.trackly.ui.theme

// file: GradientCircularProgress.kt


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun GradientCircularProgress(
    progress: Float,                       // 0f..1f
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    strokeWidth: Dp = 12.dp,
    gradientColors: List<Color> = listOf(Color(0xFF8A4FFF), Color(0xFF9B57F1), Color(0xFFB981FF)),
    backgroundColor: Color = Color(0xFFE6E6ED), // muted ring
    showPercentage: Boolean = true,
    animationDurationMs: Int = 800
) {
    // clamp progress
    val clamped = progress.coerceIn(0f, 1f)

    // animate progress swing
    val animatedProgress by animateFloatAsState(
        targetValue = clamped,
        animationSpec = tween(durationMillis = animationDurationMs, easing = FastOutSlowInEasing)
    )

    // rotating gradient animation (infinite)
    val infinite = rememberInfiniteTransition()
    val rotationDeg by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing), // slower rotation
            repeatMode = RepeatMode.Restart
        )
    )

    // convert dp to px for drawing math
    val strokePx = with(LocalDensity.current) { strokeWidth.toPx() }
    val sizePx = with(LocalDensity.current) { size.toPx() }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // calculate drawing bounds (leave padding for stroke)
            val diameter = min(sizePx, sizePx)
            val radius = diameter / 2f
            val inset = strokePx / 2f
            val arcSize = Size(diameter - strokePx, diameter - strokePx)
            val topLeft = Offset(inset, inset)

            // Background ring
            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            // Foreground gradient arc:
            // Create a sweep gradient centered at circle center
            val center = Offset(radius, radius)
            val brush = Brush.sweepGradient(colors = gradientColors, center = center)

            // rotate the drawing coordinate system for animated gradient movement
            rotate(degrees = rotationDeg, pivot = center) {
                // sweep angle according to animatedProgress
                val sweep = animatedProgress * 360f
                drawArc(
                    brush = brush,
                    startAngle = -90f,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokePx, cap = StrokeCap.Round)
                )
            }
        }

        if (showPercentage) {
            val percent = (animatedProgress * 100).toInt()
            Text(
                text = "$percent%",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}
