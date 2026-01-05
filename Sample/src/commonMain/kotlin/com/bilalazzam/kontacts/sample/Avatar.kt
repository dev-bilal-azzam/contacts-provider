package com.bilalazzam.kontacts.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.bilalazzam.kontacts.core.ContactAvatar
import kotlin.math.abs


@Composable
fun Avatar(
    avatar: ContactAvatar,
    initials: String,
    size: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(
                if (avatar != ContactAvatar.None) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    generateColorFromString(initials)
                }
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        when (avatar) {
            is ContactAvatar.AvatarBitmap -> {
                Image(
                    bitmap = avatar.bitmap,
                    contentDescription = "Contact photo"
                )
            }

            is ContactAvatar.AvatarUri -> {
                AsyncImage(
                    model = avatar.uri,
                    contentDescription = "Contact photo"
                )
            }

            ContactAvatar.None -> {
                Text(
                    text = initials,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = (size * 0.4).sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}


private fun generateColorFromString(input: String): Color {
    val colors = listOf(
        Color(0xFFE57373), // Red
        Color(0xFF64B5F6), // Blue
        Color(0xFF81C784), // Green
        Color(0xFFFFB74D), // Orange
        Color(0xFFBA68C8), // Purple
        Color(0xFF4DB6AC), // Teal
        Color(0xFFFF8A65), // Deep Orange
        Color(0xFF9575CD), // Deep Purple
        Color(0xFF4FC3F7), // Light Blue
        Color(0xFFAED581), // Light Green
    )

    val hash = input.hashCode()
    return colors[abs(hash) % colors.size]
}
