package com.example.gameclubbooking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun Prcae(){
    val navController = rememberNavController()
    EReceiptScreen(navController)
//    AddCardScreen(navController)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EReceiptScreen(navController: NavHostController) {
    Scaffold(
        containerColor = Color(0xFF101828),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "E-Receipt",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.poppins_bold))
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF101828)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Monthly Subscription",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins_medium))
            )

            ReceiptField("Start Date", "April 1, 2025")
            ReceiptField("End Date", "April 30, 2025")
            ReceiptField("Total Amount", "₸ 9,900")
            ReceiptField("Payment Method", "Visa **** 1234")
            ReceiptField("Status", "Active", statusColor = Color(0xFF4CAF50))

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Handle download */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Download Receipt", fontFamily = FontFamily(Font(R.font.poppins_medium)))
            }

            OutlinedButton(
                onClick = { /* Handle share */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text("Share", fontFamily = FontFamily(Font(R.font.poppins_medium)))
            }
        }
    }
}

@Composable
fun ReceiptField(label: String, value: String, statusColor: Color = Color(0xFFCBD5E1)) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            fontFamily = FontFamily(Font(R.font.poppins_light)),
            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1F2A37), RoundedCornerShape(10.dp))
                .padding(14.dp)
        ) {
            Text(
                text = value,
                color = statusColor,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium))
            )
        }
    }
}

