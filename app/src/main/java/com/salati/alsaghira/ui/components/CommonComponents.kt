package com.salati.alsaghira.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/** Large, thumb-friendly menu button used on the Home screen. */
@Composable
fun BigMenuButton(
    text: String,
    emoji: String,
    containerColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primaryContainer,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 72.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(emoji, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.width(16.dp))
            Text(text, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
    }
}

/** Progress section shown at the bottom of the Home screen. */
@Composable
fun HomeProgressSection(percent: Int) {
    Column(Modifier.fillMaxWidth()) {
        Text("تقدمك", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { percent / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(Modifier.height(4.dp))
        Text("$percent%", style = MaterialTheme.typography.bodyMedium)
    }
}

/** Simple back-enabled top bar reused on all inner screens. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopBar(title: String, onBack: (() -> Unit)?) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowForward, contentDescription = "رجوع")
                }
            }
        }
    )
}

/** Row of Previous / Listen / Next controls used by lesson-step screens. */
@Composable
fun StepNavigatorRow(
    isFirst: Boolean,
    isLast: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onListen: () -> Unit,
    nextLabel: String = "التالي"
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(onClick = onPrevious, enabled = !isFirst) {
            Text("السابق")
        }
        FilledIconButton(onClick = onListen, modifier = Modifier.size(56.dp)) {
            Icon(Icons.Filled.VolumeUp, contentDescription = "استمع")
        }
        Button(onClick = onNext) {
            Text(if (isLast) "إنهاء" else nextLabel)
        }
    }
}

@Composable
fun StarsBadge(stars: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("⭐", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.width(4.dp))
        Text("$stars", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
    }
}
