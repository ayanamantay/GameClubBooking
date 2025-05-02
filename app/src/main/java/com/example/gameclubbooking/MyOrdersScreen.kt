package com.example.gameclubbooking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gameclubbooking.ui.theme.PoppinsFontFamily

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

class OrdersViewModel : ViewModel() {
    private val _orders = mutableStateListOf<ClubOrder>()
    val orders: List<ClubOrder> get() = _orders

    fun placeOrder(club: GameClub) {
        val newOrder = ClubOrder(
            id = _orders.size + 1,
            club = club,
            status = OrderStatus.ACTIVE
        )
        _orders.add(newOrder)
    }

    // Optional: For future updates (e.g., cancel or complete orders)
    fun updateStatus(orderId: Int, newStatus: OrderStatus) {
        val index = _orders.indexOfFirst { it.id == orderId }
        if (index != -1) {
            _orders[index] = _orders[index].copy(status = newStatus)
        }
    }
}


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
@Composable
fun OrdersTab(orders: List<ClubOrder>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(orders) { order ->
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2B3A55)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = order.club.imageRes),
                        contentDescription = order.club.name,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            order.club.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = Color.White,
                            fontFamily = PoppinsFontFamily
                        )
                        Text(
                            "Status: ${order.status.name}",
                            fontSize = 14.sp,
                            color = Color.LightGray,
                            fontFamily = PoppinsFontFamily
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersScreen(navController: NavController, ordersViewModel: OrdersViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Active", "Completed", "Cancelled")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Orders",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, tint = Color.White, contentDescription = "Back", modifier = Modifier.size(28.dp))
                    }
                },
                        colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF101828),
            )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).background(color = Color(0xFF101828))) {
            TabRow(selectedTabIndex = selectedTab) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = PoppinsFontFamily,
                                color = Color.White
                            )
                        },
                        modifier = Modifier.background(Color(0xFF101828))
                    )
                }
            }

            when (selectedTab) {
                0 -> OrdersTab(ordersViewModel.orders.filter { it.status == OrderStatus.ACTIVE })
                1 -> OrdersTab(ordersViewModel.orders.filter { it.status == OrderStatus.COMPLETED })
                2 -> OrdersTab(ordersViewModel.orders.filter { it.status == OrderStatus.CANCELLED })
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
