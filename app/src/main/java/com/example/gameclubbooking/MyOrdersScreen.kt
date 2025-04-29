package com.example.gameclubbooking

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
// --- Data classes ---
data class GameClub(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val isLiked: Boolean = false
)

data class ClubOrder(
    val id: Int,
    val club: GameClub,
    val status: OrderStatus
)

enum class OrderStatus {
    ACTIVE, COMPLETED, CANCELLED
}

// --- Sample data ---
val sampleClubs = listOf(
    GameClub(1, "Alpha Club", R.drawable._shot_interiors_gg_light),
    GameClub(2, "Beta Club", R.drawable._97a15199870299_y3jvccw4otqsnzawldi1miww),
    GameClub(3, "Gamma Club", R.drawable._shot_interiors_red_room)
)

val sampleOrders = listOf(
    ClubOrder(1, sampleClubs[0], OrderStatus.ACTIVE),
    ClubOrder(2, sampleClubs[1], OrderStatus.COMPLETED),
    ClubOrder(3, sampleClubs[2], OrderStatus.CANCELLED)
)

// --- UI for Order List ---
@Composable
fun OrdersTab(orders: List<ClubOrder>) {
    LazyColumn {
        items(orders) { order ->
            Card(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = order.club.imageRes),
                        contentDescription = order.club.name,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(order.club.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Status: ${order.status.name}", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

// --- Tabs filtered ---
@Composable
fun ActiveOrdersTab() {
    OrdersTab(orders = sampleOrders.filter { it.status == OrderStatus.ACTIVE })
}

@Composable
fun CompletedOrdersTab() {
    OrdersTab(orders = sampleOrders.filter { it.status == OrderStatus.COMPLETED })
}

@Composable
fun CancelledOrdersTab() {
    OrdersTab(orders = sampleOrders.filter { it.status == OrderStatus.CANCELLED })
}

// --- Main Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Active", "Completed", "Cancelled")

    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.padding(20.dp),
                title = {
                    Text(
                        "My Orders",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 70.dp, vertical = 20.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(30.dp))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    )
                }
            }

            when (selectedTab) {
                0 -> ActiveOrdersTab()
                1 -> CompletedOrdersTab()
                2 -> CancelledOrdersTab()
            }
        }
    }
}

// --- Preview ---
@Preview(showBackground = true)
@Composable
fun MyOrdersScreenPreview() {
    val navController = rememberNavController()
    MyOrdersScreen(navController = navController)
}
