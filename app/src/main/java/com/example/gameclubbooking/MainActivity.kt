package com.example.gameclubbooking

import CreateAccountPart1Screen
import CreateAccountPart2Screen
import ForgowPasPage
import OnboardingScreen
import WelcomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "onboarding"
            ) {
                composable("onboarding") {
                    OnboardingScreen(navController)
                }
                composable("welcome") {
                    WelcomeScreen(navController)
                }
                composable("create_account1") {
                    CreateAccountPart1Screen(navController)
                }
                composable("main") {
                    MainScreen()
                }
                composable("forgot_password") {
                    ForgowPasPage(navController)
                }

                composable("create_account2") {
                    CreateAccountPart2Screen(navController)
                }
            }
        }
    }
}
