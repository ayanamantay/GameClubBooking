package com.example.gameclubbooking

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object LikedClubs : Screen("liked")
    object Tournaments : Screen("events")
    object Profile : Screen("profile")

    object Search : Screen("search")
    object YourProfile : Screen("your_profile")
    object Settings : Screen("settings")
    object HelpCenter : Screen("help_center")
    object PrivacyPolicy : Screen("privacy_policy")
    object BookedClubsScreen : Screen("booked_clubs")
    object Notifications : Screen("notifications")
    object PasswordManager : Screen("password_manager")

    object ClubDetails : Screen("clubDetails/{clubId}") {
        fun passId(clubId: String): String = "clubDetails/$clubId"
    }

}

sealed class BottomNavIcon {
    data class Vector(val icon: ImageVector) : BottomNavIcon()
    data class Resource(@DrawableRes val resId: Int) : BottomNavIcon()
}

data class BottomNavItem(
    val title: String,
    val icon: BottomNavIcon,
    val screen: Screen
)


@Composable
fun MainScreen() {
    val clubViewModel: ClubViewModel = viewModel()
    val ordersViewModel: OrdersViewModel = viewModel()
    val tournamentViewModel: TournamentViewModel = viewModel()
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem("Home", BottomNavIcon.Vector(Icons.Default.Home), Screen.Home),
        BottomNavItem("My Clubs", BottomNavIcon.Resource(R.drawable._542411), Screen.BookedClubsScreen),
        BottomNavItem("Liked", BottomNavIcon.Vector(Icons.Default.Favorite), Screen.LikedClubs),
        BottomNavItem("Events", BottomNavIcon.Resource(R.drawable._44750), Screen.Tournaments), // ✅ Custom icon
        BottomNavItem("Profile", BottomNavIcon.Vector(Icons.Default.Person), Screen.Profile)
    )
    Scaffold(
        containerColor = Color(0xFF101828),
        bottomBar = {
            CustomBottomNavigationBar(navController = navController, items = items)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Tournaments.route) {
                TournamentsScreen(
                    tournaments = sampleTournaments,
                    navController = navController,
                    viewModel = tournamentViewModel
                )
            }

            composable(Screen.Search.route) {
                SearchScreen(navController = navController)
            }


            composable("tournamentDetail/{tournamentJson}") { backStackEntry ->
                val json = backStackEntry.arguments?.getString("tournamentJson")
                val tournament = Gson().fromJson(json, Tournament::class.java)

                TournamentDetailScreen(
                    tournament = tournament,
                    navController = navController,
                    viewModel = tournamentViewModel
                )
            }

            composable("home") {
                HomeScreen(navController, ordersViewModel)
            }

            composable("liked") {
                LikedClubsScreen(navController, ordersViewModel, )
            }

            composable(
                route = Screen.ClubDetails.route,
                arguments = listOf(navArgument("clubId") { type = NavType.StringType })
            ) { backStackEntry ->
                val clubId = backStackEntry.arguments?.getString("clubId") ?: ""
                val viewModel = viewModel<ClubViewModel>() // or hiltViewModel<ClubViewModel>()

                ClubDetailsScreen(
                    clubId = clubId,
                    navController = navController,
                    onLikeClick = { clickedClub ->
                        val index = sampleClubs.indexOfFirst { it.id == clickedClub.id }
                        if (index != -1) {
                            val updated = sampleClubs[index].copy(
                                isLiked = !sampleClubs[index].isLiked
                            )
                            sampleClubs[index] = updated
                            viewModel.bookClub(updated, "dummyDate", "dummyPlace")
                        }
                    },
                    viewModel = clubViewModel
                )
            }

            composable(Screen.Profile.route) { ProfileScreen(navController) }

            composable(Screen.Settings.route) { SettingsScreen(navController) }
            composable(Screen.Notifications.route) { NotificationScreen(navController) }
            composable(Screen.PasswordManager.route) { PasswordManagerScreen(navController) }
            composable(Screen.YourProfile.route) { YourProfileScreen(navController) }
            composable(Screen.HelpCenter.route) { HelpCenterScreen(navController) }
            composable(Screen.PrivacyPolicy.route) { PrivacyPolicyScreen(navController) }

            composable(Screen.BookedClubsScreen.route) {
                BookedClubsScreen(viewModel = clubViewModel)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScreen(  navController: NavController,
                     ordersViewModel: OrdersViewModel,
    ) {
        val likedIds = remember { derivedStateOf { ordersViewModel.likedClubIds } }
        val clubs = sampleClubs.map {
            it.copy(isLiked = likedIds.value.contains(it.id))
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
                        Spacer(modifier = Modifier.width(8.dp))

                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(36.dp)
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
            Spacer(modifier = Modifier.height(16.dp))

            val newsItems = listOf(
                NewsItem("", R.drawable.gameclub1),
                NewsItem("", R.drawable.gameclub2),
                NewsItem("", R.drawable.gameclub3)
            )

            NewsBannerPager(newsList = newsItems) {
                println("Clicked on: $it")
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                clubs.forEach { club ->
                        ClubItem(
                            club = club,
                            onClick = { navController.navigate(Screen.ClubDetails.passId(club.id)) },
                        onLikeClick = { ordersViewModel.toggleLike(club) }
                    )
                }
            }
        }
    }
}

data class NewsItem(
    val title: String,
    val imageRes: Int
)


@Composable
fun ClubItem(
    club: GameClub,
    onClick: () -> Unit,
    onLikeClick: (GameClub) -> Unit,

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
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
                    IconButton(onClick = {
                        onLikeClick(club)
                    }) {
                        Icon(
                            imageVector = if (club.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (club.isLiked) Color.Red else Color.Gray
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun NewsBanner(newsItem: NewsItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 20.dp)
            .clickable { onClick() }
            .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B3A55))
    ) {
        Box {
            Image(
                painter = painterResource(id = newsItem.imageRes),
                contentDescription = "News image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = newsItem.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PoppinsFontFamily,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NewsBannerPager(newsList: List<NewsItem>, onClick: (NewsItem) -> Unit) {
    val pagerState = rememberPagerState()

    Column {
        HorizontalPager(
            count = newsList.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            NewsBanner(newsItem = newsList[page]) {
                onClick(newsList[page])
            }
        }

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
        color = Color(0xFF181818),
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
                    when (val icon = item.icon) {
                        is BottomNavIcon.Vector -> Icon(
                            imageVector = icon.icon,
                            contentDescription = item.title,
                            tint = animatedColor,
                            modifier = Modifier.size(26.dp)
                        )
                        is BottomNavIcon.Resource -> Icon(
                            painter = painterResource(id = icon.resId),
                            contentDescription = item.title,
                            tint = animatedColor,
                            modifier = Modifier.size(26.dp)
                        )
                    }

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
fun LikedClubsScreen(
    navController: NavHostController,
    ordersViewModel: OrdersViewModel,
) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser

    val likedClubs1 = sampleClubs.filter { it.isLiked }
    val likedClubs by remember { derivedStateOf { ordersViewModel.likedClubIds } }

    val clubs = likedClubs.mapNotNull { clubId ->
        sampleClubs.find { it.id == clubId }?.copy(isLiked = true)
    }


    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            ordersViewModel.loadLikedClubs()
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
        Text( "Liked Clubs",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,)
        Spacer(Modifier.height(16.dp))

        if (currentUser == null) {
            Text("Please log in to view your liked clubs.")
        } else if (clubs.isEmpty()) {
            Text("You haven't liked any clubs yet.",
                fontSize = 22.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.White,
                modifier = Modifier.padding(40.dp))
        } else {
            clubs.forEach { club ->
                ClubItem(
                    club = club,
                    onClick = { navController.navigate("clubDetails/${club.id}") },
                    onLikeClick = { },
                )
            }
        }
    }
}