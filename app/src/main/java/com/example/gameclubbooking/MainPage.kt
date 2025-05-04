package com.example.gameclubbooking

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gameclubbooking.ui.theme.PoppinsFontFamily
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.ExperimentalPagerApi



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    MainScreen()

}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object MyOrders : Screen("orders")
    object LikedClubs : Screen("liked")
    object Tournaments : Screen("events")
    object Profile : Screen("profile")

    // Additional pages
    object YourProfile : Screen("your_profile")
    object AddCard : Screen("addCard")
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

val TextWhite = Color.White


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
        containerColor = Color(0xFF101828), // Example dark theme background

        bottomBar = {
            CustomBottomNavigationBar(navController = navController, items = items)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }

            composable(Screen.MyOrders.route) { MyOrdersScreen(navController) }
            composable("liked") { backStackEntry ->
                val navController = rememberNavController() // Use a new instance of NavController
                LikedClubsScreen(navController = navController) // Pass NavController to the screen
            }


//            composable("search") { SearchClubScreen(navController) }
            composable("ereceipt") { EReceiptScreen(navController) }


            composable(Screen.Tournaments.route) {
                TournamentsScreen(tournaments = sampleTournaments)
            }
            composable(Screen.Profile.route) { ProfileScreen(navController) }

            // Additional pages from profile
            composable(Screen.Settings.route) { SettingsScreen(navController) }
            composable(Screen.Notifications.route) { NotificationScreen(navController) }
            composable(Screen.PasswordManager.route) { PasswordManagerScreen(navController) }
            composable(Screen.YourProfile.route) { YourProfileScreen(navController) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val ordersViewModel: OrdersViewModel = viewModel()
    val cardViewModel: CardViewModel = viewModel()

    val clubs = sampleClubs

    fun toggleLike(club: GameClub) {
        ordersViewModel.toggleLike(club)  // Toggle the like state and add/remove from liked clubs
    }

    fun bookClub(club: GameClub, cardViewModel: CardViewModel) {
        if (cardViewModel.cardDetails == null) {
            navController.navigate(Screen.AddCard.route) // Navigate to Add Card if no card is added
        } else {
            ordersViewModel.placeOrder(club, cardViewModel) // Proceed with booking
            navController.navigate(Screen.MyOrders.route) // Navigate to orders screen after booking
        }
    }


    Scaffold(
        containerColor = Color(0xFF0A192F),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "UNIGAME",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = PoppinsFontFamily,
                            color = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    }
                    IconButton(onClick = { navController.navigate("ereceipt") }) {
                        Icon(Icons.Default.Star, contentDescription = "Subscriptions", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0A192F))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF0A192F))
                .verticalScroll(rememberScrollState())
        ) {
            // News Banner Section
            Spacer(modifier = Modifier.height(16.dp))

            val newsItems = listOf(
                "🔥 Big update coming soon!",
                "📢 Community meeting this Sunday",
                "💡 Tips for better naming"
            )

            NewsBannerPager(newsList = newsItems) {
                println("Clicked on: $it")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Club Listing Section
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                clubs.forEach { club ->
                    ClubItem(
                        club = club,
                        onClick = { navController.navigate(Screen.ClubDetails.passId(club.id)) },
                        onLikeClick = { ordersViewModel.toggleLike(club) },
                        onBookClick = { bookClub(club, cardViewModel) }, // Pass cardViewModel to bookClub
                        ordersViewModel = ordersViewModel,
                        cardViewModel = cardViewModel
                    )

                }
            }
        }
    }
}

@Composable
fun ClubItem(
    club: GameClub,
    onClick: () -> Unit,
    onLikeClick: (GameClub) -> Unit,
    onBookClick: () -> Unit,
    ordersViewModel: OrdersViewModel,
    cardViewModel: CardViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF112240)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    IconButton(onClick = { onLikeClick(club) }) {
                        Icon(
                            imageVector = if (club.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (club.isLiked) Color.Red else Color.Gray
                        )
                    }
                }

                // Book Now Button
                BookNowButton(onClick = onBookClick) // Correctly call onBookClick
            }
        }
    }
}


@Composable
fun BookNowButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
    ) {
        Text("Book Now", color = Color.White)
    }
}




@Composable

fun NewsBanner(newsTitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(horizontal = 20.dp)
            .clickable { onClick() }
            .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B3A55)) // Stylish dark blue
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = newsTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PoppinsFontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}


@Composable
fun NewsBannerPager(newsList: List<String>, onClick: (String) -> Unit) {
    val pagerState = rememberPagerState()
    Column {
        HorizontalPager(
            count = newsList.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) { page ->
            NewsBanner(newsTitle = newsList[page]) {
                onClick(newsList[page])
            }
        }

        // Optional: Pager Indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            activeColor = Color.White,
            inactiveColor = Color.Gray
        )
    }
}


@Composable
fun CustomBottomNavigationBar(
    navController: NavController,
    items: List<BottomNavItem>
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .shadow(10.dp, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        color = Color(0xFF181818), // dark grey-blue (different from home bg)
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.screen.route
                val animatedColor by animateColorAsState(
                    if (selected) Color(0xFF57C5B6) else Color.LightGray,
                    label = ""
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = animatedColor,
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = item.title,
                        fontSize = 12.sp,
                        color = animatedColor,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
fun LikedClubsScreen(navController: NavController) {
    val ordersViewModel: OrdersViewModel = viewModel()  // Get the OrdersViewModel instance
    val cardViewModel: CardViewModel = viewModel()  // Get the CardViewModel instance

    val likedClubs = ordersViewModel.likedClubs // Access the list of liked clubs

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C27))
    ) {
        Text(
            "Liked Clubs",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(20.dp)
        )

        LazyColumn {
            items(likedClubs) { club ->
                ClubItem(
                    club = club,
                    onClick = { navController.navigate(Screen.ClubDetails.passId(club.id)) },  // Navigate to club details
                    onLikeClick = { ordersViewModel.toggleLike(club) },  // Remove from liked clubs
                    onBookClick = { /* Handle booking logic */ },
                    ordersViewModel = ordersViewModel,  // Pass ordersViewModel to handle booking
                    cardViewModel = cardViewModel  // Pass cardViewModel to handle booking logic
                )
            }
        }
    }
}



data class Tournament(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val imageRes: Int
)

val sampleTournaments = listOf(
    Tournament(
        1,
        "Valorant Night",
        "Join the 5v5 tournament!",
        "May 18, 2025",
        R.drawable.valorant
    ),
    Tournament(2, "FIFA Showdown", "Compete in FIFA 24!", "June 3, 2025", R.drawable.fifa),
    Tournament(3, "CS:GO Masters", "Clash with top CS:GO teams", "June 15, 2025", R.drawable.csgo)
)

@Composable
fun TournamentsScreen(tournaments: List<Tournament>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C27))
    ) {
        Text(
            "Upcoming Tournaments",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            modifier = Modifier.padding(20.dp)
        )
        LazyColumn {
            items(tournaments) { tournament ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2B3A55)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Image(
                            painter = painterResource(id = tournament.imageRes),
                            contentDescription = tournament.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            tournament.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = PoppinsFontFamily,
                            color = Color.White
                        )
                        Text(
                            tournament.description,
                            fontSize = 14.sp,
                            color = Color.LightGray,
                            fontFamily = PoppinsFontFamily
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "Date: ${tournament.date}",
                            fontSize = 13.sp,
                            color = Color(0xFF80CBC4),
                            fontFamily = PoppinsFontFamily
                        )
                    }
                }
            }
        }
    }
}



