package com.example.gameclubbooking


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gameclubbooking.ui.theme.PoppinsFontFamily
import com.google.gson.Gson
import kotlinx.coroutines.launch

class TournamentViewModel : ViewModel() {
    private val _enrolledMap = mutableStateMapOf<String, Boolean>()
    val enrolledMap: Map<String, Boolean> get() = _enrolledMap

    fun isEnrolled(tournamentId: String): Boolean {
        return _enrolledMap[tournamentId] == true
    }

    fun enroll(tournamentId: String) {
        _enrolledMap[tournamentId] = true
    }
}



data class Tournament(
    val id: String,
    val title: String,
    val date: String,
    val address: String,
    val prize: String,
    val tuition: String,
    val description: String,
    val imageRes: Int
)


val sampleTournaments = listOf(
    Tournament(
        id = "1",
        title = "Valorant Night",
        description = "Join the 5v5 tournament!",
        date = "May 18, 2025",
        imageRes = R.drawable.valorant,
        address = "Game Club Arena, Almaty",
        prize = "$500 + Trophy",
        tuition = "$10 per player"
    ),
    Tournament(
        id = "2",
        title = "FIFA Showdown",
        description = "Compete in FIFA 24!",
        date = "June 3, 2025",
        imageRes = R.drawable.fifa,
        address = "Mega Center Esentai",
        prize = "$300 Gift Card",
        tuition = "Free"
    ),
    Tournament(
        id = "3",
        title = "CS:GO Masters",
        description = "Clash with top CS:GO teams",
        date = "June 15, 2025",
        imageRes = R.drawable.csgo,
        address = "CyberZone, Astana",
        prize = "$1000 + Medals",
        tuition = "$20 per team"
    )
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentDetailScreen(
    tournament: Tournament,
    navController: NavController,
    viewModel: TournamentViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val isEnrolled = viewModel.isEnrolled(tournament.id)

    Scaffold(
        containerColor = Color(0xFF101828),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = tournament.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        androidx.compose.material3.Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF101828))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = tournament.imageRes),
                contentDescription = tournament.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C27)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow("📅 Date", tournament.date, fontSize = 16.sp)
                    InfoRow("📍 Location", tournament.address, fontSize = 16.sp)
                    InfoRow("🎁 Prize", tournament.prize, fontSize = 16.sp)
                    InfoRow("💵 Tuition", tournament.tuition, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "About the Tournament",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PoppinsFontFamily,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tournament.description,
                fontSize = 15.sp,
                color = Color.LightGray,
                fontFamily = PoppinsFontFamily
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (!isEnrolled) {
                        viewModel.enroll(tournament.id)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("You have enrolled successfully!")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isEnrolled) Color.Gray else Color(0xFF00C896)
                ),
                shape = RoundedCornerShape(14.dp),
                enabled = !isEnrolled
            ) {
                Text(
                    text = if (isEnrolled) "Enrolled" else "Enroll Now",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFontFamily,
                    color = Color.White
                )
            }
        }
    }
}




@Composable
fun InfoRow(label: String, value: String, fontSize: TextUnit = 14.sp) {
    Row(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(label, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = fontSize)
        Spacer(Modifier.width(8.dp))
        Text(value, color = Color(0xFF80CBC4), fontSize = fontSize)
    }
}
@Composable
fun TournamentsScreen(
    tournaments: List<Tournament>,
    navController: NavController,
    viewModel: TournamentViewModel
) {

    if (tournaments.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No tournaments available",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(tournaments) { tournament ->
                TournamentCard(tournament = tournament, navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun TournamentCard(
    tournament: Tournament,
    navController: NavController,
    viewModel: TournamentViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C27)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = tournament.imageRes),
                contentDescription = tournament.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = tournament.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFontFamily,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = tournament.date,
                fontSize = 14.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    val jsonTournament = Gson().toJson(tournament)
                    navController.navigate("tournamentDetail/$jsonTournament")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C896)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "View Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFontFamily,
                    color = Color.White
                )
            }
        }
    }
}







