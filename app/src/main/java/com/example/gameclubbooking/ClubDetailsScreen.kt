@file:OptIn(ExperimentalLayoutApi::class)

package com.example.gameclubbooking


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gameclubbooking.ui.theme.PoppinsFontFamily

@Preview(showBackground = true)
@Composable
fun Pre2(){
    ClubDetailsScreen(1, navController = rememberNavController())
}@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubDetailsScreen(clubId: Int, navController: NavController) {
    // Get the club by ID
    val club = sampleClubs.find { it.id == clubId }
    var isLiked by remember { mutableStateOf(club?.isLiked ?: false) }

    // Background Color for the whole screen
    val backgroundColor = Color(0xFF101828)

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
                    IconButton(onClick = {
                        // Toggle like state
                        isLiked = !isLiked
                    }) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { /* Cart */ }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        club?.let {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(backgroundColor) // Apply background color here
            ) {
                // Image
                Image(
                    painter = painterResource(id = club.imageRes),
                    contentDescription = club.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )

                // Name and Price Row
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "ALMATY COMPUEE CLUB",
                            fontSize = 14.sp,
                            fontFamily = PoppinsFontFamily,
                            color = Color.White
                        )
                        Text(
                            text = it.name,  // Use the club's name here
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontFamily = PoppinsFontFamily,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "Total Price\n$1000",
                        textAlign = TextAlign.End,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White
                    )
                }

                Divider(color = Color.Gray)

                // Select Date
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        "Select DATE",
                        fontSize = 14.sp,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row {
                        (1..6).forEach {
                            Box(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color.LightGray)
                                    .clickable { /* select date */ },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("$it", color = Color.White, fontFamily = PoppinsFontFamily)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Select Place
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        "Select PLACE",
                        fontSize = 14.sp,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // Example for placeholders - uncomment FlowRow for a more dynamic layout
//                    FlowRow(
//                        mainAxisSpacing = 8.dp,
//                        crossAxisSpacing = 8.dp
//                    ) {
//                        repeat(18) {
//                            Box(
//                                modifier = Modifier
//                                    .size(20.dp)
//                                    .clip(CircleShape)
//                                    .background(Color.Black)
//                            )
//                        }
//                    }
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.Gray)

                // Reviews
                Row(Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                    Text(
                        "4.5",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 4.dp),
                        fontFamily = PoppinsFontFamily,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bottom Buttons
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { /* Remind later */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "REMIND LATER",
                            color = Color.White,
                            fontFamily = PoppinsFontFamily
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { /* Buy now */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Buy Now",
                            fontFamily = PoppinsFontFamily,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        } ?: run {
            Text(
                "Club not found",
                modifier = Modifier.padding(16.dp),
                fontFamily = PoppinsFontFamily,
                color = Color.White
            )
        }
    }
}
