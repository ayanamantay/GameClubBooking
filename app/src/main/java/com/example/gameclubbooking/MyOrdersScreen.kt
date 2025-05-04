package com.example.gameclubbooking

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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

    private val _likedClubs = mutableStateListOf<GameClub>()
    val likedClubs: List<GameClub> get() = _likedClubs

    fun toggleLike(club: GameClub) {
        val updatedClub = club.copy(isLiked = !club.isLiked)
        val index = _likedClubs.indexOfFirst { it.id == club.id }

        if (updatedClub.isLiked) {
            if (index == -1) {
                _likedClubs.add(updatedClub)
            }
        } else {
            if (index != -1) {
                _likedClubs.removeAt(index)
            }
        }
    }

    fun placeOrder(club: GameClub, cardViewModel: CardViewModel) {
        if (cardViewModel.cardDetails != null) {
            val newOrder = ClubOrder(
                id = _orders.size + 1,
                club = club,
                status = OrderStatus.ACTIVE
            )
            _orders.add(newOrder)
        } else {
            Log.d("Booking", "Please add a card first!")
        }
    }
}





class CardViewModel : ViewModel() {
    private val _cardDetails = mutableStateOf<CardDetails?>(null)
    val cardDetails: CardDetails? get() = _cardDetails.value

    fun addCard(cardNumber: String, expiryDate: String, cvv: String, saveCard: Boolean) {
        val newCard = CardDetails(cardNumber, expiryDate, cvv, saveCard)
        _cardDetails.value = newCard
    }
}


data class CardDetails(
    val cardNumber: String,
    val expiryDate: String,
    val cvv: String,
    val saveCard: Boolean
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


@Composable
fun OrdersTab(orders: List<ClubOrder>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(orders) { order ->
            val statusColor = when (order.status) {
                OrderStatus.ACTIVE -> Color.Green
                OrderStatus.COMPLETED -> Color.Gray
                OrderStatus.CANCELLED -> Color.Red
            }

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
                            color = statusColor,
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

            val orders = ordersViewModel.orders

            when (selectedTab) {
                0 -> OrdersTab(orders.filter { it.status == OrderStatus.ACTIVE })
                1 -> OrdersTab(orders.filter { it.status == OrderStatus.COMPLETED })
                2 -> OrdersTab(orders.filter { it.status == OrderStatus.CANCELLED })
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
