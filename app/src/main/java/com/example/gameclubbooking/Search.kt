package com.example.gameclubbooking

import CustomInputField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gameclubbooking.ui.theme.PoppinsFontFamily


@Preview(showBackground = true)
@Composable
fun Pre(){
    val navController = rememberNavController()
    SearchClubScreen(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchClubScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredClubs = sampleClubs.filter { club ->
        club.name.contains(searchQuery, ignoreCase = true)
    }

    val backgroundColor = Color(0xFF101828)

    Scaffold(
        containerColor = backgroundColor, // sets background for scaffold base layer
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Search Clubs",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 60.dp, vertical = 20.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor) // make sure entire content area is dark
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = {
                    Text(
                        "Search by club name",
                        color = Color.White,
                        fontFamily = PoppinsFontFamily
                    )
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White
                ),
                textStyle = TextStyle(
                    color = Color.White,
                    fontFamily = PoppinsFontFamily
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize() // ensures the background fills the space
            ) {
                items(filteredClubs) { club ->
                    ClubItem(
                        club = club,
                        onClick = {
                            // Handle navigation here
                            navController.navigate(Screen.ClubDetails.passId(club.id))
                        },
                        onLikeClick = {
                            // Handle the like action here (e.g., toggle liked state)
                            println("Liked club: ${club.name}")
                        },
                        onBookClick = {
                            // Handle booking action here
                            println("Booked club: ${club.name}")
                        }
                    )
                }
            }
        }
    }
}



