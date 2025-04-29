package com.example.gameclubbooking.ui.theme

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gameclubbooking.YourProfileScreen

@Preview(showBackground = true)
@Composable
fun Pre(){
//    EReceiptScreen()
    val navController = rememberNavController()
    AddCardScreen(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EReceiptScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Add Card",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 140.dp, vertical = 20.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(30.dp))
                    }
                }
            )
        }
    )  { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Monthly Subscription", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

            ReceiptField("Start Date", "April 1, 2025")
            ReceiptField("End Date", "April 30, 2025")
            ReceiptField("Total Amount", "₸ 9,900")
            ReceiptField("Payment Method", "Visa **** 1234")
            ReceiptField("Status", "Active", statusColor = Color(0xFF4CAF50))

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { /* Handle download */ }) {
                Text("Download Receipt")
            }

            OutlinedButton(onClick = { /* Handle share */ }) {
                Text("Share")
            }
        }
    }
}

@Composable
fun ReceiptField(label: String, value: String, statusColor: Color = Color.DarkGray) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 16.sp, style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp))
        Text(
            text = value,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF6F6F6), RoundedCornerShape(6.dp))
                .padding(12.dp),
            color = statusColor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(navController: NavController) {
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var saveCard by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add Card",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 100.dp, vertical = 20.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(30.dp).padding(horizontal = 12.dp))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card visual placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
                    .padding(16.dp),
            )

            OutlinedTextField(
                value = cardNumber,
                onValueChange = { cardNumber = it },
                label = { Text("Card Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { expiryDate = it },
                    label = { Text("Expiry Date (MM/YY)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f).padding(horizontal = 12.dp)
                )

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = { Text("CVV") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    modifier = Modifier.weight(1f).padding(horizontal = 12.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = saveCard, onCheckedChange = { saveCard = it })
                Text("Save Card")
            }

            Button(
                onClick = {
                    // Just log or show a toast for now
                    println("Card Added: $cardNumber, $expiryDate, $cvv, Save: $saveCard")
                    navController.popBackStack() // Optionally go back
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Card")
            }
        }
    }
}
