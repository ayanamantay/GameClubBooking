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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth


data class GameClub(
    val id: String,
    val name: String,
    val address: String,
    val imageRes: Int,
    val isLiked: Boolean = false,
    val rating: Double,
    val description: String,
    val price: String
)

val sampleClubs = mutableStateListOf(
    GameClub("1", "Alpha Club", "123 Alpha Street", R.drawable._shot_interiors_gg_light,false, 4.5, "The Alpha Club is the best for eSports and casual gamers.", "tg1000"),
    GameClub("2", "Beta Club", "456 Beta Ave", R.drawable._97a15199870299_y3jvccw4otqsnzawldi1miww,false, 4.2, "Beta Club is great for tournaments and casual events.", "tg800"),
    GameClub("3", "Gamma Club", "789 Gamma Blvd", R.drawable._shot_interiors_red_room, false, 4.7, "Gamma Club offers the best VR experiences.", "tg1200"),
    GameClub("4", "Zeta Club", "256 Zeta Ave", R.drawable.mustang_gaming_club_cover, false, 4.9, "Zeta Club is perfect for professional gaming events.", "tg1500"),
    GameClub("5", "Sigma Club", "111 Sigma Blvd", R.drawable.istock_1354760356, false, 4.0, "Sigma Club specializes in retro gaming and casual hangouts.", "tg600")
)


class OrdersViewModel : ViewModel() {
    private val _likedClubIds = mutableStateListOf<String>()
    val likedClubIds: List<String> get() = _likedClubIds

    init {
        loadLikedClubs()
    }

    fun loadLikedClubs() {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val userDoc = FirebaseFirestore.getInstance().collection("users").document(user.uid)
        userDoc.get().addOnSuccessListener { doc ->
            val favorites = doc.get("favorites") as? List<Map<String, String>> ?: emptyList()
            _likedClubIds.clear()
            _likedClubIds.addAll(favorites.mapNotNull { it["id"] })
        }
    }

    fun toggleLike(club: GameClub) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val userDoc = FirebaseFirestore.getInstance().collection("users").document(user.uid)

        val currentlyLiked = _likedClubIds.contains(club.id)
        if (currentlyLiked) {
            _likedClubIds.remove(club.id)
        } else {
            _likedClubIds.add(club.id)
        }

        userDoc.get().addOnSuccessListener { doc ->
            val currentList = doc.get("favorites") as? List<Map<String, String>> ?: emptyList()
            val updatedList = if (currentlyLiked) {
                currentList.filter { it["id"] != club.id }
            } else {
                currentList + mapOf(
                    "id" to club.id,
                    "name" to club.name,
                    "address" to club.address,
                    "image" to club.imageRes.toString()
                )
            }

            userDoc.update("favorites", updatedList)
                .addOnSuccessListener {
                    loadLikedClubs()
                }
                .addOnFailureListener {
                    userDoc.set(mapOf("favorites" to updatedList))
                        .addOnSuccessListener {
                            loadLikedClubs()
                        }
                }
        }
    }
}

