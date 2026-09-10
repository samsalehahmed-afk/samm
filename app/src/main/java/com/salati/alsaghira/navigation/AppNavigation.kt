package com.salati.alsaghira.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.salati.alsaghira.ui.screens.HomeScreen
import com.salati.alsaghira.ui.screens.SplashScreen
import com.salati.alsaghira.ui.screens.achievements.AchievementsScreen
import com.salati.alsaghira.ui.screens.assistant.AssistantScreen
import com.salati.alsaghira.ui.screens.fatiha.FatihaAdhkarScreen
import com.salati.alsaghira.ui.screens.parent.ParentDashboardScreen
import com.salati.alsaghira.ui.screens.parent.ParentGateScreen
import com.salati.alsaghira.ui.screens.prayer.PrayerScreen
import com.salati.alsaghira.ui.screens.quiz.QuizScreen
import com.salati.alsaghira.ui.screens.settings.SettingsScreen
import com.salati.alsaghira.ui.screens.wudu.WuduScreen
import com.salati.alsaghira.viewmodel.AppViewModelFactory
import com.salati.alsaghira.viewmodel.ProgressViewModel

@Composable
fun AppNavigation(viewModelFactory: AppViewModelFactory) {
    val navController: NavHostController = rememberNavController()
    val progressViewModel: ProgressViewModel = viewModel(factory = viewModelFactory)

    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(onFinished = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            })
        }

        composable(Routes.HOME) {
            HomeScreen(
                progressViewModel = progressViewModel,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Routes.WUDU) {
            val vm = viewModel<com.salati.alsaghira.viewmodel.WuduViewModel>(factory = viewModelFactory)
            WuduScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
                onLessonCompleted = { progressViewModel.completeLesson("wudu") }
            )
        }

        composable(Routes.PRAYER) {
            val vm = viewModel<com.salati.alsaghira.viewmodel.PrayerViewModel>(factory = viewModelFactory)
            PrayerScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
                onLessonCompleted = { progressViewModel.completeLesson("prayer") }
            )
        }

        composable(Routes.FATIHA_ADHKAR) {
            val vm = viewModel<com.salati.alsaghira.viewmodel.PrayerViewModel>(factory = viewModelFactory)
            FatihaAdhkarScreen(viewModel = vm, onBack = { navController.popBackStack() })
        }

        composable(Routes.QUIZ) {
            val vm = viewModel<com.salati.alsaghira.viewmodel.QuizViewModel>(factory = viewModelFactory)
            QuizScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
                onQuizCompleted = { progressViewModel.completeQuiz("quiz_main") },
                onAnswer = { correct -> progressViewModel.recordQuizAnswer(correct) }
            )
        }

        composable(Routes.ACHIEVEMENTS) {
            AchievementsScreen(progressViewModel = progressViewModel, onBack = { navController.popBackStack() })
        }

        composable(Routes.ASSISTANT) {
            val vm = viewModel<com.salati.alsaghira.viewmodel.AssistantViewModel>(factory = viewModelFactory)
            AssistantScreen(viewModel = vm, onBack = { navController.popBackStack() })
        }

        composable(Routes.SETTINGS) {
            val vm = viewModel<com.salati.alsaghira.viewmodel.SettingsViewModel>(factory = viewModelFactory)
            SettingsScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
                onResetProgress = { progressViewModel.resetProgress() }
            )
        }

        composable(Routes.PARENT_GATE) {
            ParentGateScreen(
                onSuccess = {
                    navController.navigate(Routes.PARENT_DASHBOARD) {
                        popUpTo(Routes.PARENT_GATE) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PARENT_DASHBOARD) {
            ParentDashboardScreen(progressViewModel = progressViewModel, onBack = { navController.popBackStack() })
        }
    }
}
