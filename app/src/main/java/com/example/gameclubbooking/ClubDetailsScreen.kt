@file:OptIn(ExperimentalLayoutApi::class)

package com.example.gameclubbooking


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gameclubbooking.ui.theme.PoppinsFontFamily
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ClubDetailsScreen(
    clubId: String,
    navController: NavController,
    onLikeClick: (GameClub) -> Unit,
    viewModel: ClubViewModel // correct usage
) {

    val club = sampleClubs.find { it.id == clubId } ?: return

    val backgroundColor = Color(0xFF101828)

    var selectedDate by remember { mutableStateOf<String?>(null) }
    var selectedPlace by remember { mutableStateOf<String?>(null) }

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM")
    val nextSevenDays = List(7) { LocalDate.now().plusDays(it.toLong()) }
    val places = listOf("Room 1", "Room 2", "VIP Lounge", "eSports Zone")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { onLikeClick(club) }) {
                        Icon(
                            imageVector = if (club.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (club.isLiked) Color.Red else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Image(
                painter = painterResource(id = club.imageRes),
                contentDescription = club.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("ALMATY COMPUTER CLUB", fontSize = 14.sp, fontFamily = PoppinsFontFamily, color = Color.White)
                    Text(club.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, fontFamily = PoppinsFontFamily, color = Color.White)
                }
                Text("Total Price\ntg1000", textAlign = TextAlign.End, fontFamily = PoppinsFontFamily, color = Color.White)
            }

            Divider(color = Color.Gray)

            // Select Date
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Select DATE", fontSize = 14.sp, fontFamily = PoppinsFontFamily, color = Color.White, modifier = Modifier.padding(bottom = 8.dp))
                Row {
                    nextSevenDays.forEach { date ->
                        val dateText = date.format(dateFormatter)
                        val isSelected = selectedDate == dateText
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) Color.Green else Color.LightGray)
                                .clickable { selectedDate = dateText },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(dateText, color = Color.White, fontFamily = PoppinsFontFamily)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Select Place
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Select PLACE", fontSize = 14.sp, fontFamily = PoppinsFontFamily, color = Color.White, modifier = Modifier.padding(bottom = 8.dp))
                Row {
                    places.forEach { place ->
                        val isSelected = selectedPlace == place
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color.Green else Color.DarkGray)
                                .clickable { selectedPlace = place }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(place, color = Color.White, fontFamily = PoppinsFontFamily)
                        }
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.Gray)

            Row(Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                Text("4.5", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), fontFamily = PoppinsFontFamily, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    selectedDate?.let { date ->
                        selectedPlace?.let { place ->
                            viewModel.bookClub(club, date, place)
                            navController.navigate("booked_clubs") {
                                popUpTo(Screen.Home.route) { // Clear the backstack to Home
                                    inclusive = true // Pop the home screen too (if you want to remove Home)
                                }
                                launchSingleTop = true // Prevent multiple instances
                            }
                        }
                    }
                },
                enabled = selectedDate != null && selectedPlace != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (selectedDate != null && selectedPlace != null) Color(0xFF4CAF50) else Color.Gray)
            ) {
                Text("BOOK NOW", fontFamily = PoppinsFontFamily, color = Color.White)
            }


            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

data class BookedClub(
    val club: GameClub,
    val date: String,
    val place: String
)

class ClubViewModel : ViewModel() {
    var bookedClubs = mutableStateListOf<BookedClub>()
        private set

    fun bookClub(club: GameClub, date: String, place: String) {
        bookedClubs.add(BookedClub(club, date, place))
    }
}
@Composable
fun BookedClubsScreen(viewModel: ClubViewModel) {
    Text(
        "Booked Clubs",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = PoppinsFontFamily,
        color = Color.White,
        modifier = Modifier.padding(20.dp)
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101828))
            .padding(8.dp)
    ) {
        items(viewModel.bookedClubs) { booked ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1F2A))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(booked.club.imageRes),
                        contentDescription = booked.club.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("🎮 ${booked.club.name}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("📅 Date: ${booked.date}", color = Color.White, fontSize = 14.sp)
                    Text("📍 Place: ${booked.place}", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}
