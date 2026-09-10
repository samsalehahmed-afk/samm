package com.salati.alsaghira.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.salati.alsaghira.navigation.Routes
import com.salati.alsaghira.ui.components.BigMenuButton
import com.salati.alsaghira.ui.components.HomeProgressSection
import com.salati.alsaghira.viewmodel.ProgressViewModel
import com.salati.alsaghira.viewmodel.TOTAL_MVP_LESSONS

@Composable
fun HomeScreen(progressViewModel: ProgressViewModel, onNavigate: (String) -> Unit) {
    val progress by progressViewModel.progress.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("السلام عليكم 👋", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(
                "هيا نتعلم الصلاة!",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(24.dp))

            BigMenuButton("تعلم الوضوء", "🧼") { onNavigate(Routes.WUDU) }
            Spacer(Modifier.height(12.dp))
            BigMenuButton("تعلم الصلاة", "🕌") { onNavigate(Routes.PRAYER) }
            Spacer(Modifier.height(12.dp))
            BigMenuButton("الفاتحة والأذكار", "📖") { onNavigate(Routes.FATIHA_ADHKAR) }
            Spacer(Modifier.height(12.dp))
            BigMenuButton("اختبر نفسك", "🎮") { onNavigate(Routes.QUIZ) }
            Spacer(Modifier.height(12.dp))
            BigMenuButton("إنجازاتي", "⭐") { onNavigate(Routes.ACHIEVEMENTS) }
            Spacer(Modifier.height(12.dp))
            BigMenuButton("اسأل معلّم الصلاة", "🤖") { onNavigate(Routes.ASSISTANT) }
            Spacer(Modifier.height(12.dp))
            BigMenuButton("الإعدادات", "⚙️") { onNavigate(Routes.SETTINGS) }
            Spacer(Modifier.height(12.dp))
            BigMenuButton("وضع الوالدين", "👪") { onNavigate(Routes.PARENT_GATE) }

            Spacer(Modifier.height(28.dp))
            HomeProgressSection(percent = progress.completionPercent(TOTAL_MVP_LESSONS))
        }
    }
}
