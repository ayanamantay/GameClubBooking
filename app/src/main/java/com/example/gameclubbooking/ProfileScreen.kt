package com.example.gameclubbooking

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController


@Preview(showBackground = true)
@Composable
fun MyPreview() {


}




data class UserProfile(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val gender: String,
    val age: Int,
    val cityAndAddress: String,
    val email: String
)

class ProfileViewModel : ViewModel() {
    private val _user = mutableStateOf(
        UserProfile(
            name = "John",
            surname = "Doe",
            phoneNumber = "+123 456 789",
            gender = "Male",
            age = 30,
            cityAndAddress = "Almaty, Kazakhstan",
            email = "john@example.com"
        )
    )
    val user: State<UserProfile> = _user
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 120.dp, vertical = 20.dp)
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            ) {}

            Spacer(modifier = Modifier.height(16.dp))

            Text("AMAN MUSTAFIYEV", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            ProfileItem("Your Profile") { navController.navigate(Screen.YourProfile.route) }
            ProfileItem("Payment Methods") { navController.navigate(Screen.PaymentMethods.route) }
            ProfileItem("My CLUBS") { navController.navigate(Screen.MyClubs.route) }
            ProfileItem("Settings") { navController.navigate(Screen.Settings.route) } // ✅ opens SettingsScreen
            ProfileItem("Help Center") { navController.navigate(Screen.HelpCenter.route) }
            ProfileItem("Privacy Policy") { navController.navigate(Screen.PrivacyPolicy.route) }
            ProfileItem("Invite Friends") { navController.navigate(Screen.InviteFriends.route) }
            ProfileItem("Log out") {
                // TODO: Add logout logic here
            }
        }
    }
}

@Composable
fun ProfileItem(title: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp)
    ) {
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(vertical = 14.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourProfileScreen(navController: NavController, viewModel: ProfileViewModel = viewModel()) {
    val user = viewModel.user.value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Your Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 60.dp, vertical = 20.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Avatar placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileField("Name", user.name)
            ProfileField("Surname", user.surname)
            ProfileField("Phone", user.phoneNumber)
            ProfileField("Gender", user.gender)
            ProfileField("Age", user.age.toString())
            ProfileField("City and Address", user.cityAndAddress)
            ProfileField("Email", user.email)
        }
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.padding(horizontal = 20.dp,vertical = 8.dp)) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.labelSmall)
        Text(
            text = value,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0F0), RoundedCornerShape(6.dp))
                .padding(10.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpCenterScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("FAQ", "Contact Us")

    Column {
        TopAppBar(modifier = Modifier.padding(top = 20.dp),
            title = {
                Text(
                    "Help Center",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 50.dp, vertical = 0.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        TabRow(selectedTabIndex = selectedTab, modifier = Modifier.padding(horizontal = 24.dp)) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> FAQScreen()
            1 -> ContactUsScreen()
        }
    }
}
@Composable
fun ContactUsScreen() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(20.dp)
    ) {
        Text("Need Help?", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("You can reach us through the following options:", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(24.dp))

        ContactCard(
            title = "Email Support",
            subtitle = "support@gameclubbooking.com",
            icon = Icons.Default.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        ContactCard(
            title = "Phone",
            subtitle = "+7 707 123 4567",
            icon = Icons.Default.Phone
        )

        Spacer(modifier = Modifier.height(16.dp))

        ContactCard(
            title = "Visit Us",
            subtitle = "Al-Farabi Ave 71, Almaty, Kazakhstan",
            icon = Icons.Default.Place
        )
    }
}

@Composable
fun ContactCard(title: String, subtitle: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = title, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(subtitle, fontSize = 14.sp)
            }
        }
    }
}


@Composable
fun FAQScreen() {
    val faqList = listOf(
        "How do I book a game club?" to "Go to the club details page and click the 'Book Now' button. You’ll need to select a time and confirm your booking.",
        "Can I cancel my booking?" to "Yes, you can cancel your active booking from the 'My Orders' screen by selecting the order and tapping 'Cancel'.",
        "Do I need an account to book?" to "Yes, you must be logged in to book a game club.",
        "What payment methods are supported?" to "Currently, we support in-person payment at the club. Online payment will be available soon.",
        "How do I know if a club is available?" to "Available time slots are shown on each club’s detail page."
    )

    LazyColumn(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        items(faqList) { (question, answer) ->
            FAQItem(question = question, answer = answer)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(28.dp)) {
            Text(text = question, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = answer, fontSize = 14.sp)
            }
        }
    }
}

const val SETTINGS_ROUTE = "settings"
const val NOTIFICATIONS_ROUTE = "notifications"
const val PASSWORD_MANAGER_ROUTE = "password_manager"



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 100.dp, vertical = 30.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(20.dp)) {
            SettingItem("Notification Settings", Icons.Default.Notifications) {
                navController.navigate(Screen.Notifications.route)
            }
            SettingItem("Password Manager", Icons.Default.Lock) {
                navController.navigate(Screen.PasswordManager.route)
            }
            SettingItem("Delete Account", Icons.Default.Delete) {
                // Optional: show confirmation dialog
            }
        }
    }
}

@Composable
fun SettingItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontWeight = FontWeight.Normal, fontSize = 18.sp, style = MaterialTheme.typography.bodyLarge)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    "Notifications",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 85.dp, vertical = 30.dp)
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Spacer(modifier = Modifier.height(34.dp))
        LazyColumn(modifier = Modifier.padding(innerPadding).padding(horizontal = 20.dp)) {
            item { Text("TODAY", fontWeight = FontWeight.Bold, fontSize = 18.sp, style = MaterialTheme.typography.labelMedium) }
            items(2) {
                NotificationItem("UQue seat is booked", "Your booking is confirmed.")
            }

            item { Text("YESTERDAY",fontWeight = FontWeight.Bold, fontSize = 18.sp, style = MaterialTheme.typography.labelMedium) }
            items(2) {
                NotificationItem("Reminder", "Don't miss your booking.")
            }

            item { Text("5 DECEMBER",fontWeight = FontWeight.Bold, fontSize = 18.sp, style = MaterialTheme.typography.labelMedium) }
            items(2) {
                NotificationItem("Welcome!", "Thanks for joining GameClub.")
            }
        }
    }
}

@Composable
fun NotificationItem(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(Icons.Default.Notifications, contentDescription = null)
        Spacer(Modifier.width(20.dp))
        Column {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(description, fontSize = 16.sp, fontWeight = FontWeight.Normal,style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordManagerScreen(navController: NavController) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    "Password Manager",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding( horizontal = 60.dp, vertical = 30.dp)
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(26.dp))
            PasswordField("Current Password", currentPassword) { currentPassword = it }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text("Forgot Password?",
                    modifier = Modifier.padding(end = 20.dp, top = 12.dp),
                    fontSize = 14.sp, style = MaterialTheme.typography.labelMedium)
            }

            Spacer(Modifier.height(16.dp))

            PasswordField("New Password", newPassword) { newPassword = it }
            Spacer(Modifier.height(18.dp))

            PasswordField("Confirm New Password", confirmPassword) { confirmPassword = it }

            Spacer(Modifier.height(24.dp))

            Button(onClick = {""},
                // Add logic to update password
                modifier = Modifier.padding(20.dp)
            ) {
                Text("Update Password")
            }
        }
    }
}

@Composable
fun PasswordField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth().padding(horizontal =  20.dp)
    )
}





