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
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "onboarding" // Start from onboarding
            ) {
                // Onboarding Screen
                composable("onboarding") {
                    OnboardingScreen(navController) // This is where the user starts
                }

                // Welcome (Login) Screen
                composable("welcome") {
                    WelcomeScreen(navController) // Your Login screen
                }

                // Create Account Screen
                composable("create_account") {
                    CreateAccountPart1Screen(navController)
                }

                // Main Screen
                composable("main") {
                    MainScreen()
                }

                // Forgot Password Screen
                composable("forgot_password") {
                    ForgowPasPage(navController)
                }

                // Search Screen (Optional)
                composable("search") {
                    SearchClubScreen(navController)
                }

                // E-Receipt Screen (Optional)
                composable("ereceipt") {
                    EReceiptScreen(navController)
                }
            }
        }
    }
}

//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            val navController = rememberNavController()
//
//            NavHost(
//                navController = navController,
//                startDestination = "onboarding"
//            ) {
//                composable("onboarding") {
//                    OnboardingScreen(navController)
//                }
//                composable("welcome") {
//                    WelcomeScreen(navController)
//                }
//                composable("create_account") {
//                    CreateAccountPart1Screen(navController)
//                }
//                composable("main") {
//                    MainScreen()
//                }
//                composable("create_account2") {
//                    CreateAccountPart2Screen(navController)
//                }
//                composable("forgot_password") {
//                    ForgowPasPage(navController)
//                }
//                composable("search") {
//                    SearchClubScreen(navController)
//                }
//                composable("ereceipt") {
//                    EReceiptScreen(navController)
//                }
//            }
//        }
//    }
//}



