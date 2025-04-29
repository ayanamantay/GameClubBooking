package com.example.gameclubbooking

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object MyOrders : Screen("orders")
    object LikedClubs : Screen("liked")
    object Tournaments : Screen("events")
    object Profile : Screen("profile")

    // Additional pages
    object YourProfile : Screen("your_profile")
    object PaymentMethods : Screen("payment_methods")
    object MyClubs : Screen("my_clubs")
    object Settings : Screen("settings")
    object HelpCenter : Screen("help_center")
    object PrivacyPolicy : Screen("privacy_policy")
    object InviteFriends : Screen("invite_friends")
    object Notifications : Screen("notifications")
    object PasswordManager : Screen("password_manager")

    object ClubDetails : Screen("club_details/{clubId}") {
        fun passId(clubId: Int): String = "club_details/$clubId"
    }

}

    data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, Screen.Home),
        BottomNavItem("Orders", Icons.Default.List, Screen.MyOrders),
        BottomNavItem("Liked", Icons.Default.Favorite, Screen.LikedClubs),
        BottomNavItem("Events", Icons.Default.Build, Screen.Tournaments),
        BottomNavItem("Profile", Icons.Default.Person, Screen.Profile)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.route == item.screen.route,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                        label = { Text(item.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.MyOrders.route) { MyOrdersScreen(navController) }
            composable(Screen.LikedClubs.route) { LikedClubsScreen(navController) }
            composable(Screen.Tournaments.route) { TournamentsScreen(navController) }
            composable(Screen.Profile.route) { ProfileScreen(navController) }

            // Additional pages from profile
            composable(Screen.Settings.route) { SettingsScreen(navController) }
            composable(Screen.Notifications.route) { NotificationScreen(navController) }
            composable(Screen.PasswordManager.route) { PasswordManagerScreen(navController) }
            composable(Screen.YourProfile.route) { YourProfileScreen(navController) }
            composable(Screen.PaymentMethods.route) { PaymentMethodsScreen(navController) }
            composable(Screen.MyClubs.route) { MyClubsScreen(navController) }
            composable(Screen.HelpCenter.route) { HelpCenterScreen(navController) }
            composable(Screen.PrivacyPolicy.route) { PrivacyPolicyScreen(navController) }
            composable(Screen.InviteFriends.route) { InviteFriendsScreen(navController) }


            // Club Details (with argument)
            composable(
                route = Screen.ClubDetails.route,
                arguments = listOf(navArgument("clubId") { type = NavType.IntType })
            ) { backStackEntry ->
                val clubId = backStackEntry.arguments?.getInt("clubId") ?: -1
                ClubDetailsScreen(clubId = clubId, navController = navController)
            }
        }
    }
}

@Composable
fun InviteFriendsScreen(navController: NavHostController) {
    TODO("Not yet implemented")
}

@Composable
fun PrivacyPolicyScreen(navController: NavHostController) {
    TODO("Not yet implemented")
}

@Composable
fun MyClubsScreen(navController: NavHostController) {
    TODO("Not yet implemented")
}

@Composable
fun PaymentMethodsScreen(navController: NavHostController) {
    TODO("Not yet implemented")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Spacer(modifier = Modifier.height(20.dp))
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.photo_2025_04_27_00_55_03),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        "UNIGAME",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { /* Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* Profile */ }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .height(140.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                ) {
                    Text(
                        "NAMING COMMUNITY",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(54.dp)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sampleClubs) { club ->
                    ClubItem(club = club) {
                        navController.navigate(Screen.ClubDetails.passId(club.id))
                    }
                }
            }
        }
    }
}

@Composable
fun ClubItem(club: GameClub, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() }  // Add click support
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
    ) {
        Column {
            Image(
                painter = painterResource(id = club.imageRes),
                contentDescription = club.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = club.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    IconButton(onClick = { /* Like */ }) {
                        Icon(Icons.Default.Favorite, contentDescription = "Like")
                    }
                    IconButton(onClick = { /* Share */ }) {
                        Icon(Icons.Default.Email, contentDescription = "Comments")
                    }
                    IconButton(onClick = { /* Send */ }) {
                        Icon(Icons.Default.Send, contentDescription = "Send")
                    }
                }
                Button(onClick = { /* Book Now */ }) {
                    Text(text = "Book Now")
                }
            }
        }
    }
}



@Composable
fun LikedClubsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Liked Clubs Page")
    }
}

@Composable
fun TournamentsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Tournaments Page")
    }
}

