package com.salati.alsaghira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.salati.alsaghira.navigation.AppNavigation
import com.salati.alsaghira.ui.theme.SalatiAlSaghiraTheme
import com.salati.alsaghira.viewmodel.AppViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SalatiApp()
        }
    }
}

@Composable
fun SalatiApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val viewModelFactory = remember(context) { AppViewModelFactory(context) }

    SalatiAlSaghiraTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavigation(viewModelFactory = viewModelFactory)
        }
    }
}
